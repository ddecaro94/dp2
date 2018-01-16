package it.polito.dp2.NFV.sol3.service;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.lab3.AllocationException;
import it.polito.dp2.NFV.sol3.service.model.Hosts;
import it.polito.dp2.NFV.sol3.service.model.Node;
import it.polito.dp2.NFV.sol3.service.model.Nodes;

@Api(hidden = true, tags = {NfvDeployer.nodesPath})
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NodeResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	private String graphName;
	private LinksCollection links;
	
	public NodeResource(String graphName, String nodeName) {
		this.name = nodeName;
		this.graphName = graphName;
		this.links = new LinksCollection(graphName, name);
	}
	
	@DELETE
	@ApiOperation(value = "Delete node from deployed NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 422, message = "Unprocessable Entity"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public void deleteNode() {
		boolean res;
		try {
			res = this.deployer.deleteNode(graphName, name);
		} catch (NotDefinedException e) {
			e.printStackTrace();
			throw new InternalServerErrorException(e);
		}
		if (!res) throw new NotFoundException(); 
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
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node getNode() {
		return this.deployer.getNode(graphName, name);
	}
	
	@GET
	@Path(NfvDeployer.reachableHostsPath)
    @ApiOperation(value = "Get the host list")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Hosts.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Hosts getReachableHosts() {
		return deployer.getReachableHosts(graphName, name);
	}
	
	@PUT
	@ApiOperation(value = "Create node inside deployed NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Node.class),
    		@ApiResponse(code = 409, message = "Conflict"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 422, message = "Unprocessable Entity"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node putNode(Node node) {
		if (deployer.getNffgByName(graphName).getDeployTime() != null) {
			
		} else throw new NotDeployedException("NF-FG "+graphName+" not deployed", 422);
		if (node == null) throw new BadRequestException();
		try {
			String host;
			deployer.createNode(graphName, node.getName(), node.getVnf().getName(), true);
			if (node.getHost() == null) host = null;
			else host = node.getHost().getName();
			try {
				deployer.allocateNode(node.getName(), host);
			} catch (Exception e) {
				deployer.deleteNode(graphName, node.getName());
				throw new ClientErrorException(e.getMessage(), 422);
			}
			
			return deployer.getNode(graphName, node.getName());
		} catch (AlreadyLoadedException e) {
			throw new InternalServerErrorException(e);
		} catch (NotDefinedException e) {
			throw new ClientErrorException(e.getMessage(), 422);
		}
	}

}
