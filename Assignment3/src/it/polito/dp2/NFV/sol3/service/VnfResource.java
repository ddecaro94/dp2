package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Connections;
import it.polito.dp2.NFV.sol3.model.Vnf;

@Api(hidden = true, value = NfvDeployer.catalogPath)
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class VnfResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	
	public VnfResource(String name) {
		this.name = name;
	}

	@GET
	@ApiOperation(value = "Get vnf info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Vnf.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Vnf getVnf() {
		return this.deployer.getVnfByName(name);
	}
}
