package it.polito.dp2.NFV.sol3.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Deployment;

public class DeploymentsCollection {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private Map<UUID, Deployment> deployments = new HashMap<>();
	public DeploymentsCollection() {
		
	}
	
	@POST
	@ApiOperation(value = "Get the list of deployed nffgs")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Deployment startDeployment(Deployment d) {
		deployments.put(UUID.randomUUID(), d);
		return d;
	}
}
