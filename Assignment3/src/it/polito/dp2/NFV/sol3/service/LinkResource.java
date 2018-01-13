package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Link;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class LinkResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String graphName;
	private String linkName;
	
	public LinkResource(String graphName, String linkName) {
		this.graphName = graphName;
		this.linkName = linkName;
	}
	
	@GET
    @ApiOperation(value = "Get the forward relationships")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link getLink() {
		return deployer.getLink(graphName, linkName);
	}

}
