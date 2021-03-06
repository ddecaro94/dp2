package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.service.model.Nffg;
import it.polito.dp2.NFV.sol3.service.model.Nffgs;

@Api(hidden = true)
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NffgResource {

	private NfvDeployer deployer = null;
	private String name;
	private NodesCollection nodes;
	private LinksCollection links;
	
	public NffgResource(String name) {
		this.name = name;
		this.nodes = new NodesCollection(name);
		this.links = new LinksCollection(name, null);
		this.deployer = NfvDeployer.getInstance();
	}
	
	@DELETE
	@ApiOperation(value = "Undeploy a nffg")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK", response = Nffgs.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public void deleteNffg() {
		throw new ServerErrorException(501);
	}
	
	@Path(NfvDeployer.linksPath)
	public LinksCollection getLinks() {
		return links;
	}
	
	@GET
	@ApiOperation(value = "Get nffg info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Nffg.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Nffg getNffg() {
		try {
			return this.deployer.getNffgByName(name);
		} catch (UnknownEntityException e) {
			throw new NotFoundException(Response.status(404).entity(e.getMessage()).build());
		}
	}

	
	@Path(NfvDeployer.nodesPath)
	public NodesCollection getNodes() {
		return nodes;
	}	

}
