package it.polito.dp2.NFV.sol3.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.service.model.*;


@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Api(hidden = true)
public class HostsCollection {
	private NfvDeployer deployer = null;
	private Map<String, HostResource> hostResources = new HashMap<>();
	
	
	public HostsCollection() {
		this.deployer = NfvDeployer.getInstance();
	}

	@Path("{hostName}")
	public HostResource getHost(@PathParam("hostName") String name) {
		return hostResources.getOrDefault(name, new HostResource(name));
	}
	
	@GET
    @ApiOperation(value = "Get the host list")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Hosts.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Hosts getHosts() {
		return deployer.getHosts();
	}

}
