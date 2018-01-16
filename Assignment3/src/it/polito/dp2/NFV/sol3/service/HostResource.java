package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.service.model.Connections;
import it.polito.dp2.NFV.sol3.service.model.Host;

@Api(hidden = true, tags = {NfvDeployer.hostsPath})
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class HostResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	
	public HostResource(String name) {
		this.name = name;
	}
	
	@GET
	@Path(NfvDeployer.connectionsPath)
	@ApiOperation(value = "Get connections list")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Connections.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Connections getConnections() {
		return deployer.getConnections(name);
	}
	
	@GET
	@ApiOperation(value = "Get host info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Host.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Host getHost() {
		return this.deployer.getHostByName(name);
	}

}
