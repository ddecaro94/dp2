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
import it.polito.dp2.NFV.sol3.model.Catalog;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class CatalogCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	
	@GET
    @ApiOperation(value = "Get the vnf catalog")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Catalog getCatalog() {
		return deployer.getCatalog();
	}
	
	@Path("{name}")
	public VnfResource getVnfByName(@PathParam("name") String name) {
		return new VnfResource(name);
	}

}
