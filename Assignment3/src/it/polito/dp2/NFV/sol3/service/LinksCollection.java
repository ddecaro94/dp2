package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Link;
import it.polito.dp2.NFV.sol3.model.Links;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class LinksCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String nodeName;
	private String graphName;
	
	public LinksCollection(String graphName, String nodeName) {
		this.nodeName = nodeName;
		this.graphName = graphName;
		System.out.println("GRAPH, NODE: "+graphName+" "+nodeName);
	}
	
	@GET
    @ApiOperation(value = "Get the forward relationships")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Links getLinks() {
		return deployer.getLinks(graphName, nodeName);
	}
	
    @Path("{linkName}")
	public LinkResource getLink(@PathParam("linkName") String name) {
		return new LinkResource(graphName, name);
	}
	
}
