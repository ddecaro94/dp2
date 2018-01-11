package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.*;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@ApiModel(description = "A resource representing a set of hosts")
public class HostsCollection {
	private NfvDeployer deployer = NfvDeployer.getInstance();
	
	public HostsCollection() {
		
	}

	@GET
    @ApiOperation(value = "Get the host list")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Hosts getHosts() {
		return deployer.getHosts();
	}

    @Path("{name}")
	public HostResource getHost(@PathParam("name") String name) {
		return new HostResource(name);
	}

}
