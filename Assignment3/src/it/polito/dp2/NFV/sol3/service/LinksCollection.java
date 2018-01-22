package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.BadRequestException;
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
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.service.model.Link;
import it.polito.dp2.NFV.sol3.service.model.Links;

@Api(hidden = true)
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class LinksCollection {

	private NfvDeployer deployer = null;
	private String nodeName;
	private String graphName;
	
	public LinksCollection(String graphName, String nodeName) {
		this.nodeName = nodeName;
		this.graphName = graphName;
		this.deployer = NfvDeployer.getInstance();
	}
	
	@DELETE
	@Path("{linkName}")
    @ApiOperation(value = "Delete link")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 409, message = "Conflict", response = String.class),
    		@ApiResponse(code = 422, message = "Unprocessable Entity", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link deleteLink(@PathParam("linkName") String id) {
		throw new ServerErrorException(501);
	}
	
	@GET
    @Path("{linkName}")
    @ApiOperation(value = "Get link information")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Link.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link getLink(@PathParam("linkName") String name) {
		try {
			return deployer.getLink(graphName, name);
		} catch (UnknownEntityException e) {
			throw new NotFoundException(Response.status(404).entity(e.getMessage()).build());
		}
	}
	
	@GET
    @ApiOperation(value = "Get the forward relationships")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Links.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Links getLinks() {
		try {
			return deployer.getLinks(graphName, nodeName);
		} catch (UnknownEntityException e) {
			throw new NotFoundException(Response.status(404).entity(e.getMessage()).build());
		}
	}
	
	@POST
    @ApiOperation(value = "Create new link between nodes")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Link.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link postLink(Link link) {		
		if (!deployer.isDeployed(graphName)) throw new ConflictException("NF-FG ["+graphName+"] does not exist");
		
		int reqLatency = (link.getRequiredLatency() == null) ? 0 : link.getRequiredLatency().intValue();
		float reqThr = (link.getRequiredThroughput() == null) ? 0 : link.getRequiredThroughput();
		
		try {
			return deployer.createLink(graphName, link.getName(), link.getSrc(), link.getDst(), reqLatency, reqThr, false);
		} catch (AlreadyLoadedException e) {
			throw new ConflictException(e);
		} catch (UnknownEntityException e) {
			throw new ConflictException(e);
		} catch (ServiceException e) {
			throw new InternalServerErrorException(e);
		}
	}
	
	@PUT
	@Path("{linkName}")
    @ApiOperation(value = "Create or replace link")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Link.class),
    		@ApiResponse(code = 404, message = "Not Found", response = String.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link putLink(Link link, @PathParam("linkName") String id) {
		if (link == null) throw new BadRequestException("No link defined");
		if (link.getName() == null) throw new UnprocessableEntityException("Link name cannot be empty");
		if (!link.getName().equals(id)) throw new UnprocessableEntityException("Link name and link resource identifier cannot be different, identifier: "+id+", link name: "+link.getName());
		if (link.getSrc() == null) throw new UnprocessableEntityException("Source node not defined");
		if (link.getDst() == null) throw new UnprocessableEntityException("Destination node not defined");
		if (!deployer.isDeployed(graphName)) throw new ConflictException("NF-FG ["+graphName+"] does not exist");
		
		int reqLatency = (link.getRequiredLatency() == null) ? 0 : link.getRequiredLatency().intValue();
		float reqThr = (link.getRequiredThroughput() == null) ? 0 : link.getRequiredThroughput();
		
		try {
			
			return deployer.createLink(graphName, link.getName(), link.getSrc(), link.getDst(), reqLatency, reqThr, true);
		} catch (AlreadyLoadedException e) {
			throw new InternalServerErrorException(e);
		} catch (UnknownEntityException e) {
			throw new UnprocessableEntityException(e);
		} catch (ServiceException e) {
			throw new InternalServerErrorException(e);
		}
	}
	
}
