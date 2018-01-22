package it.polito.dp2.NFV.sol3.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Pattern;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.service.model.NewNffg;
import it.polito.dp2.NFV.sol3.service.model.Nffg;
import it.polito.dp2.NFV.sol3.service.model.Nffgs;

@Api(hidden = true)
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
public class NffgsCollection {

	private NfvDeployer deployer = null;
	private Map<String, NffgResource> nffgResources = new HashMap<>();

	public NffgsCollection() {
		this.deployer = NfvDeployer.getInstance();
	}

	@POST
	@ApiOperation(value = "Deploy a new nffg")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK", response = Nffg.class),
			@ApiResponse(code = 409, message = "Conflict", response = String.class),
			@ApiResponse(code = 422, message = "Unprocessable Entity", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Nffg deployNffg(NewNffg nffg) {
		if (nffg == null) throw new BadRequestException("No NF-FG description defined");
		if (nffg.getName() == null) throw new UnprocessableEntityException("No NF-FG name provided");
		
		try {
			return deployer.deployNffg(nffg);
		} catch (AlreadyLoadedException e) {
			throw new ConflictException(e);
		} catch (UnknownEntityException e) {
			throw new UnprocessableEntityException(e);
		} catch (AllocationException e) {
			throw new ConflictException(e);
		}catch (DatatypeConfigurationException e) {
			throw new InternalServerErrorException(e);
		} catch (ServiceException e) {
			throw new InternalServerErrorException(e);
		}

	}

	@Path("{nffgName}")
	public NffgResource getNffg(@PathParam("nffgName")
								@Pattern(regexp = "\\d{8}") String name) {
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
