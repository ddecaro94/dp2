package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NodeResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	
	public NodeResource() {
		// TODO Auto-generated constructor stub
	}

}
