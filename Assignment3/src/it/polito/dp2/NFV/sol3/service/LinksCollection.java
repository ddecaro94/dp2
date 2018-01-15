package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Link;
import it.polito.dp2.NFV.sol3.model.Links;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Api(hidden = true, tags = {NfvDeployer.nffgsPath, NfvDeployer.nodesPath})
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class LinksCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String nodeName;
	private String graphName;
	
	public LinksCollection(String graphName, String nodeName) {
		this.nodeName = nodeName;
		this.graphName = graphName;
	}
	
	@GET
    @Path("{linkName}")
    @ApiOperation(value = "Get link information")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Link.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link getLink(@PathParam("linkName") String name) {
		return deployer.getLink(graphName, name);
	}
	
	@GET
    @ApiOperation(value = "Get the forward relationships")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Links.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Links getLinks() {
		return deployer.getLinks(graphName, nodeName);
	}
	
	@POST
    @ApiOperation(value = "Create new link between nodes")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Links.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link postLink(Link link) {
		try {
			return deployer.createLink(graphName, link.getName(), link.getSrc(), link.getDst(), link.getRequiredLatency().intValue(), link.getRequiredThroughput(), false);
		} catch (NotFoundException e) {
			throw e;
		} catch (AlreadyLoadedException e) {
			throw new ConflictException();
		}
	}
	
	@PUT
	@Path("{linkName}")
    @ApiOperation(value = "Create or replace link")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Links.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link putLink(Link link, @PathParam("linkName") String id) {
		try {
			if (link.getName() != id) throw new ClientErrorException("Link name and link resource identifier cannot be different", 422);
			return deployer.createLink(graphName, link.getName(), link.getSrc(), link.getDst(), link.getRequiredLatency().intValue(), link.getRequiredThroughput(), true);
		} catch (NotFoundException e) {
			throw e;
		} catch (AlreadyLoadedException e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@DELETE
	@Path("{linkName}")
    @ApiOperation(value = "Delete link")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 409, message = "Conflict"),
    		@ApiResponse(code = 422, message = "Unprocessable Entity"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link deleteLink(@PathParam("linkName") String id) {
		throw new ServerErrorException(501);
	}
	
}
