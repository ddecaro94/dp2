package it.polito.dp2.NFV.sol3.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Deployment;
import it.polito.dp2.NFV.sol3.model.Deployments;
import it.polito.dp2.NFV.sol3.model.Nffgs;
import it.polito.dp2.NFV.sol3.model.Undeployment;
import it.polito.dp2.NFV.sol3.model.Undeployments;

@Api(value = NfvDeployer.nffgsPath, hidden=true)
@ApiModel(description = "A resource representing a Network Function Virtualization")
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NffgsCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private Map<String, NffgResource> nffgResources = new HashMap<>();
	private Deployments deployments = new Deployments();
	private Undeployments undeployments = new Undeployments();
	
	public NffgsCollection() {
		
	}

	@GET
	@ApiOperation(value = "Get the list of deployed nffgs")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Nffgs.class),
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
	
    @Path("{nffgName}")
	public NffgResource getNffg(@PathParam("nffgName") String name) {
		return nffgResources.getOrDefault(name, new NffgResource(name));
	}
    
    @GET
	@Path(NfvDeployer.deploymentsPath)
	public Deployments getDeployments() {
		return deployments;
	}
    
	@POST
	@Path(NfvDeployer.deploymentsPath)
	@ApiOperation(value = "Start new deployment")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Deployment.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Deployment startDeployment(Deployment d) {
		d.setId(UUID.randomUUID().toString());
		deployments.getDeployment().add(d);
		return d;
	}
    
    @GET
	@ApiOperation(value = "Get deployment info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Deployment.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	@Path(NfvDeployer.deploymentsPath+"/{deploymentID}")
	public Deployment getDeployment(@PathParam("deploymentID") String id) {
    	for (Deployment d : deployments.getDeployment()) {
    		if (d.getId().equals(id)) return d;
    	}
		throw new NotFoundException();
	}
    
    @GET
	@Path(NfvDeployer.undeploymentsPath)
	public Undeployments getUndeployments() {
		return undeployments;
	}
    
	@POST
	@Path(NfvDeployer.undeploymentsPath)
	@ApiOperation(value = "Start new deployment")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Deployment.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Undeployment startUndeployment(Undeployment d) {
		d.setId(UUID.randomUUID().toString());
		undeployments.getUndeployment().add(d);
		return d;
	}
    
    @GET
	@ApiOperation(value = "Get deployment info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Deployment.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	@Path(NfvDeployer.undeploymentsPath+"/{undeploymentID}")
	public Undeployment getUndeployment(@PathParam("undeploymentID") String id) {
    	for (Undeployment d : undeployments.getUndeployment()) {
    		if (d.getId().equals(id)) return d;
    	}
		throw new NotFoundException();
	}

}
