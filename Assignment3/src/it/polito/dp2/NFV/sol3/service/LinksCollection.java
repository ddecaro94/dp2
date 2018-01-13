package it.polito.dp2.NFV.sol3.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Connections;
import it.polito.dp2.NFV.sol3.model.Link;
import it.polito.dp2.NFV.sol3.model.Links;

@Api(hidden = true)
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
    @ApiOperation(value = "Get the forward relationships")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Links.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Links getLinks() {
		return deployer.getLinks(graphName, nodeName);
	}
	
	@POST
    @ApiOperation(value = "Get the forward relationships")
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
			throw new ClientErrorException(422, e);
		}
	}
	
	@PUT
	@Path("{linkName}")
    @ApiOperation(value = "Create or replace link")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Links.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Link putLink(Link link) {
		try {
			return deployer.createLink(graphName, link.getName(), link.getSrc(), link.getDst(), link.getRequiredLatency().intValue(), link.getRequiredThroughput(), true);
		} catch (NotFoundException e) {
			throw e;
		} catch (AlreadyLoadedException e) {
			e.printStackTrace();
			throw new InternalServerErrorException(e);
		}
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
	
}
