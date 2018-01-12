package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Host;
import it.polito.dp2.NFV.sol3.model.Node;

@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NodeResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	private String graphName;
	
	public NodeResource(String graphName, String nodeName) {
		this.name = nodeName;
		this.graphName = graphName;
	}
	
	@GET
	@ApiOperation(value = "Get node info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node getNode() {
		return this.deployer.getNode(name);
	}
	
	@Path(NfvDeployer.linksPath)
	public LinksCollection getLinks() {
		return new LinksCollection(graphName, name);
	}

}
