package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import it.polito.dp2.NFV.sol3.model.*;

public class HostsCollection {
	private NfvDeployer deployer = NfvDeployer.getInstance();
	
	public HostsCollection() {
		// TODO Auto-generated constructor stub
	}

	public Hosts getHosts() {
		// TODO Auto-generated method stub
		return deployer.getHosts();
	}

    @GET
    @Path("{name}")
	public Host getHost(@PathParam("name") String name) {
		
		return null;
	}

}
