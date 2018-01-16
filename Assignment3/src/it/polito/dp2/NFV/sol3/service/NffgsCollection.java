package it.polito.dp2.NFV.sol3.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.service.model.NewNffg;
import it.polito.dp2.NFV.sol3.service.model.Nffg;
import it.polito.dp2.NFV.sol3.service.model.Nffgs;

@Api(hidden = true, tags = { NfvDeployer.nffgsPath })
@ApiModel(description = "A resource representing a Network Function Virtualization")
@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
public class NffgsCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private Map<String, NffgResource> nffgResources = new HashMap<>();

	public NffgsCollection() {

	}

	@POST
	@ApiOperation(value = "Deploy a new nffg")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK", response = Nffg.class),
			@ApiResponse(code = 409, message = "Conflict"),
			@ApiResponse(code = 422, message = "Unprocessable Entity"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Nffg deployNffg(NewNffg nffg) {
		try {
			if (nffg == null) throw new BadRequestException();
			return deployer.deployNffg(nffg);
		} catch (AlreadyLoadedException e) {
			e.printStackTrace();
			throw new ConflictException(409, e);
		} catch (NotDefinedException e) {
			e.printStackTrace();
			throw new ClientErrorException(422, e);
		}

	}

	@Path("{nffgName}")
	public NffgResource getNffg(@PathParam("nffgName") String name) {
		return nffgResources.getOrDefault(name, new NffgResource(name));
	}
	
	@GET
	@ApiOperation(value = "Get the list of deployed nffgs")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Nffgs.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Nffgs getNffgs(@QueryParam("deployTime") String date) {
		if (date == null || date.equals(""))
			return deployer.getNffgs(null);
		try {
			Date from = new SimpleDateFormat("yyyyMMdd").parse(date);
			return deployer.getNffgs(from);
		} catch (ParseException e) {
			throw new BadRequestException("Provided date is not in a valid format, the correct format is 'YYYYMMDD'");
		}
	}
	

}
