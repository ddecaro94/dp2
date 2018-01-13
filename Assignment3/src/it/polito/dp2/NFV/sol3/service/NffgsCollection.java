package it.polito.dp2.NFV.sol3.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Nffgs;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NffgsCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private Map<String, NffgResource> nffgResources = new HashMap<>();
	private DeploymentsCollection deployments = new DeploymentsCollection();
	
	@GET
	@ApiOperation(value = "Get the list of deployed nffgs")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Nffgs getNffgs(@QueryParam("deployTime") String date) {
		if (date == null) return deployer.getNffgs(null);
		try {
			Date from = new SimpleDateFormat("yyyyMMdd").parse(date) ;
			return deployer.getNffgs(from);
		} catch (ParseException e) {
			throw new BadRequestException("Provided date is not in a valid format, the correct format is 'YYYYMMDD'");
		}	
	}
	
    @Path("{name}")
	public NffgResource getNffg(@PathParam("name") String name) {
		return nffgResources.getOrDefault(name, new NffgResource(name));
	}
    
	@Path(NfvDeployer.deploymentsPath)
	public DeploymentsCollection deployments() {
		return deployments;
	}

}
