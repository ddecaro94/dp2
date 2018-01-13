package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Connections;
import it.polito.dp2.NFV.sol3.model.Host;
import it.polito.dp2.NFV.sol3.model.Node;

@Api(hidden = true, value = NfvDeployer.nodesPath)
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NodeResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	private String graphName;
	private ReachableHostsCollection reachableHosts;
	private LinksCollection links;
	
	public NodeResource(String graphName, String nodeName) {
		this.name = nodeName;
		this.graphName = graphName;
		this.reachableHosts = new ReachableHostsCollection(name);
		this.links = new LinksCollection(graphName, name);
	}
	
	@GET
	@ApiOperation(value = "Get node info")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Node.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node getNode() {
		return this.deployer.getNode(name);
	}
	
	@Path(NfvDeployer.linksPath)
	public LinksCollection getLinks() {
		return links;
	}
	
	@Path(NfvDeployer.reachableHostsPath)
	public ReachableHostsCollection getReachableHosts() {
		return this.reachableHosts;
	}

}
