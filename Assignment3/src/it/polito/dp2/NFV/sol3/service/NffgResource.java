package it.polito.dp2.NFV.sol3.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Deployment;
import it.polito.dp2.NFV.sol3.model.Nffg;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NffgResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	private NodesCollection nodes;
	private LinksCollection links;
	
	public NffgResource(String name) {
		this.name = name;
		this.nodes = new NodesCollection(name);
		this.links = new LinksCollection(name, null);
	}
	
	@GET
	@ApiOperation(value = "Get nffg info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Nffg getNffg() {
		return this.deployer.getNffgByName(name);
	}
	
	@Path(NfvDeployer.nodesPath)
	public NodesCollection getNodes() {
		return nodes;
	}
	
	@Path(NfvDeployer.linksPath)
	public LinksCollection getLinks() {
		return links;
	}	

}
