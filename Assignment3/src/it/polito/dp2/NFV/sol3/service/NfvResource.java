package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.*;

@Path("/")
@Api(value = "/")
@ApiModel(description = "A resource representing a Network Function Virtualization")
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NfvResource {
	private NfvDeployer deployer = NfvDeployer.getInstance();
	private HostsCollection hosts = new HostsCollection();
	private NffgsCollection nffgs = new NffgsCollection();
	private CatalogCollection catalog = new CatalogCollection();
	
	public NfvResource() {
		
	}
	@GET
    @ApiOperation(value = "Get the properties for the NFV system")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Nfv getNfv() {
		return this.deployer.getNfv();
	}
	
	@Path("hosts")
    @ApiOperation(value = "Get the hosts collection")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public HostsCollection getHostsCollection() {
		return this.hosts;
	}

	@Path("nffgs")
    @ApiOperation(value = "Get the nffgs collection")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public NffgsCollection getNffgs() {
		return this.nffgs;
	}
	
	@Path("catalog")

	public CatalogCollection getCatalog() {
		return this.catalog;
	}
	

}
