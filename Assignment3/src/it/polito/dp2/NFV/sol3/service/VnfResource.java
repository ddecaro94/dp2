package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.polito.dp2.NFV.sol3.model.Vnf;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class VnfResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	
	public VnfResource(String name) {
		this.name = name;
	}

	@GET
	public Vnf getVnf() {
		return this.deployer.getVnfByName(name);
	}
}