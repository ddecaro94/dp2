package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.service.model.Catalog;
import it.polito.dp2.NFV.sol3.service.model.Vnf;

@Api(hidden = true)
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class CatalogCollection {

	private NfvDeployer deployer = null;
	
	public CatalogCollection () {
		this.deployer = NfvDeployer.getInstance();
	}
	@GET
    @ApiOperation(value = "Get the vnf catalog")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Catalog.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Catalog getCatalog() {
		return deployer.getCatalog();
	}
	
	@GET
	@Path("{vnfName}")
	@ApiOperation(value = "Get vnf info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Vnf.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Vnf getVnf(@PathParam("vnfName") String vnfName) {
		try {
			return this.deployer.getVnfByName(vnfName);
		} catch (UnknownEntityException e) {
			throw new NotFoundException(Response.status(404).entity(e.getMessage()).build());
		}
	}

}
