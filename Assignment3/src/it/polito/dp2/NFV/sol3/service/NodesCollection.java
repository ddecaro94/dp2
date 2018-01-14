package it.polito.dp2.NFV.sol3.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Node;
import it.polito.dp2.NFV.sol3.model.Nodes;

@Api(hidden = true, tags = {NfvDeployer.nffgsPath})
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
		return deployer.getNodes(graph);
	}
	
	@POST
	@ApiOperation(value = "Create node inside deployed NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Nodes.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 422, message = "Unprocessable Entity"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node postNode(Node node) {
		if (deployer.getNffgByName(graph).getDeployTime() != null) {
			
		} else throw new NotDeployedException("NF-FG "+graph+" not deployed", 422);
		
		try {
			return deployer.createNode(graph, node.getName(), node.getVnf().getName(), false);
		} catch (AlreadyLoadedException e) {
			throw new ConflictException();
		} catch (NotDefinedException e) {
			throw new ClientErrorException(e.getMessage(), 422);
		}
	}

}
