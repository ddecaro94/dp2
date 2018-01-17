package it.polito.dp2.NFV.sol3.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.lab3.AllocationException;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.lab3.UnknownEntityException;
import it.polito.dp2.NFV.sol3.service.model.Node;
import it.polito.dp2.NFV.sol3.service.model.Nodes;

@Api(hidden = true, tags = {NfvDeployer.nodesPath})
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NodesCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String graph;
	private Map<String, NodeResource> nodeResources = new HashMap<>();
	
	public NodesCollection(String name) {
		this.graph = name;
	}
	
	@Path("{nodeName}")
	public NodeResource getNode(@PathParam("nodeName") String nodeName) {
		return nodeResources.getOrDefault(nodeName, new NodeResource(graph, nodeName));
	}
	
	@GET
	@ApiOperation(value = "Get nodes of the NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Nodes.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Nodes getNodes() {
		try {
			return deployer.getNodes(graph);
		} catch (UnknownEntityException e) {
			throw new NotFoundException(e);
		}
	}
	
	@POST
	@ApiOperation(value = "Create node inside deployed NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Node.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 409, message = "Conflict"),
    		@ApiResponse(code = 422, message = "Unprocessable Entity"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node postNode(Node node) {
		if (node == null) throw new BadRequestException("Node descriptor not provided");
		if (!deployer.isDeployed(graph)) throw new ConflictException("NF-FG "+graph+" not deployed");
		if (node.getVnf() == null) throw new UnprocessableEntityException("VNF type not defined");
		if (node.getName() == null) throw new UnprocessableEntityException("Node name not defined");
		String host = (node.getHost() != null) ? node.getHost().getName() : null;
		
		try {
			Node n = deployer.createNode(graph, node.getName(), node.getVnf().getName(), false);

		} catch (AlreadyLoadedException e) {
			throw new ConflictException(e);
		} catch (UnknownEntityException e) {
			throw new UnprocessableEntityException(e);
		} catch (ServiceException e) {
			try {
				deployer.deleteNode(graph, node.getName());
			} catch (UnknownEntityException e1) {
				throw new InternalServerErrorException(e1);
			} catch (ServiceException e1) {
				throw new InternalServerErrorException(e1);
			}
			throw new InternalServerErrorException(e);
		} catch (InvalidEntityException e) {
			throw new UnprocessableEntityException(e);
		}
		
		try {
			deployer.allocateNode(node.getName(), host);

		} catch (AllocationException e) {
			try {
				deployer.deleteNode(graph, node.getName());
			} catch (UnknownEntityException e1) {
				throw new InternalServerErrorException(e1);
			} catch (ServiceException e1) {
				throw new InternalServerErrorException(e1);
			}
			throw new ConflictException(e);
		} catch (UnknownEntityException e) {
			try {
				deployer.deleteNode(graph, node.getName());
			} catch (UnknownEntityException e1) {
				throw new InternalServerErrorException(e1);
			} catch (ServiceException e1) {
				throw new InternalServerErrorException(e1);
			}
			throw new UnprocessableEntityException(e);
		}
		
		
		try {
			return deployer.getNode(graph, node.getName());
		} catch (UnknownEntityException e) {
			throw new InternalServerErrorException(e);
		}

	}

}
