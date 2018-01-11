package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.polito.dp2.NFV.sol3.model.Connections;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class ConnectionsCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String hostName;

	public ConnectionsCollection(String hostName) {
		this.hostName = hostName;
	}
	
	@GET
	public Connections getHostsConnections() {
		return this.deployer.getConnections(this.hostName);
	}
	
}
