package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.ApiOperation;
import it.polito.dp2.NFV.sol3.model.Host;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class HostResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	
	public HostResource(String name) {
		this.name = name;
	}
	
	@GET
	@ApiOperation(value = "Get host info")
	public Host getHost() {
		return this.deployer.getHostByName(name);
	}
	
	@Path("connections")
	public ConnectionsCollection getConnections(String hostName) {
		return new ConnectionsCollection(hostName);
	}

}