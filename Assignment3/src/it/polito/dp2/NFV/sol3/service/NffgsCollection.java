package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.polito.dp2.NFV.sol3.model.Nffgs;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NffgsCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	
	public Nffgs getNffgs() {
		// TODO Auto-generated method stub
		return null;
	}

}
