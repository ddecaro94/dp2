package it.polito.dp2.NFV.sol3.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Node;
import it.polito.dp2.NFV.sol3.model.Nodes;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NodesCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String graph;
	private Map<String, NodeResource> nodeResources = new HashMap<>();
	
	public NodesCollection(String name) {
		this.graph = name;
	}
	
	@GET
	@ApiOperation(value = "Get nodes of the NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Nodes getNodes() {
		return deployer.getNodes(graph);
	}
	
	@Path("{nodeName}")
	public NodeResource getNode(@PathParam("nodeName") String nodeName) {
		return nodeResources.getOrDefault(nodeName, new NodeResource(graph, nodeName));
	}

}
