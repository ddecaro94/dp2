package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.service.model.Hosts;
import it.polito.dp2.NFV.sol3.service.model.Node;

@Api(hidden = true)
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NodeResource {

	private NfvDeployer deployer = null;
	private String name;
	private String graphName;
	private LinksCollection links;
	
	public NodeResource(String graphName, String nodeName) {
		this.name = nodeName;
		this.graphName = graphName;
		this.links = new LinksCollection(graphName, name);
		this.deployer = NfvDeployer.getInstance();
	}
	
	@DELETE
	@ApiOperation(value = "Delete node from deployed NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = String.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 422, message = "Unprocessable Entity", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public void deleteNode() {
		
		if (!deployer.isDeployed(graphName)) throw new NotFoundException("NF-FG ["+graphName+"] not found");
		try {
			this.deployer.deleteNode(graphName, name);
		} catch (UnknownEntityException e) {
			throw new UnprocessableEntityException(e);
		}
		return;
	}
	
	@Path(NfvDeployer.linksPath)
	public LinksCollection getLinks() {
		return links;
	}
	
	@GET
	@ApiOperation(value = "Get node info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Node.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node getNode() {
		try {
			return this.deployer.getNode(graphName, name);
		} catch (UnknownEntityException e) {
			throw new NotFoundException(Response.status(404).entity(e.getMessage()).build());
		}
	}
	
	@GET
	@Path(NfvDeployer.reachableHostsPath)
    @ApiOperation(value = "Get the host list")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Hosts.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Hosts getReachableHosts() {
		try {
			return deployer.getReachableHosts(graphName, name);
		} catch (UnknownEntityException e) {
			throw new NotFoundException(Response.status(404).entity(e.getMessage()).build());
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new InternalServerErrorException(e);
		}
	}
	
	@PUT
	@ApiOperation(value = "Create node inside deployed NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Node.class),
    		@ApiResponse(code = 409, message = "Conflict", response = String.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 422, message = "Unprocessable Entity", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node putNode(Node node) {
		if (node == null) throw new BadRequestException("Node descriptor not provided");
		if (!deployer.isDeployed(graphName)) throw new ConflictException("NF-FG ["+graphName+"] not deployed");

		String host = (node.getHost() != null) ? node.getHost().getName() : null;
		
		try {
			deployer.createNode(graphName, node.getName(), node.getVnf().getName(), true);
		} catch (AlreadyLoadedException e) {
			throw new ConflictException(e);
		} catch (UnknownEntityException e) {
			throw new UnprocessableEntityException(e);
		} catch (ServiceException e) {
			try {
				deployer.deleteNode(graphName, node.getName());
			} catch (UnknownEntityException e1) {
				throw new InternalServerErrorException(e1);
			}
			throw new InternalServerErrorException(e);
		}
		
		try {
			deployer.allocateNode(node.getName(), host);

		} catch (AllocationException e) {
			try {
				deployer.deleteNode(graphName, node.getName());
			} catch (UnknownEntityException e1) {
				throw new InternalServerErrorException(e1);
			}
			throw new ConflictException(e);
		} catch (UnknownEntityException e) {
			try {
				deployer.deleteNode(graphName, node.getName());
			} catch (UnknownEntityException e1) {
				throw new InternalServerErrorException(e1);
			}
			throw new UnprocessableEntityException(e);
		}
	
		try {
			return deployer.getNode(graphName, node.getName());
		} catch (UnknownEntityException e) {
			throw new InternalServerErrorException(e);
		}
	}

}
