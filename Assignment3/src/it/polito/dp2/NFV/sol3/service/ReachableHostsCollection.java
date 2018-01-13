package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.GET;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Hosts;

public class ReachableHostsCollection {
	
	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String nodeName;
	
	public ReachableHostsCollection(String nodeName) {
		this.nodeName = nodeName;
	}

	@GET
    @ApiOperation(value = "Get the host list")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Hosts getReachableHosts() {
		return deployer.getReachableHosts(nodeName);
	}
}
