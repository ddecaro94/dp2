package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.sol3.model.Connections;
import it.polito.dp2.NFV.sol3.model.Host;
import it.polito.dp2.NFV.sol3.model.Hosts;
import it.polito.dp2.NFV.sol3.model.Node;
import it.polito.dp2.NFV.sol3.model.Nodes;

@Api(hidden = true)
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NodeResource {

	private NfvDeployer deployer = NfvDeployer.getInstance();
	private String name;
	private String graphName;
	private LinksCollection links;
	
	public NodeResource(String graphName, String nodeName) {
		this.name = nodeName;
		this.graphName = graphName;
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
	
	@DELETE
	@ApiOperation(value = "Delete node from deployed NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Node.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 422, message = "Unprocessable Entity"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node deleteNode() {
		return this.deployer.deleteNode(name);
	}
	
	@PUT
	@ApiOperation(value = "Create node inside deployed NF-FG")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Nodes.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 422, message = "Unprocessable Entity"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Node putNode(Node node) {
		if (deployer.getNffgByName(graphName).getDeployTime() != null) {
			
		} else throw new NotDeployedException("NF-FG "+graphName+" not deployed", 422);
		try {
			return deployer.createNode(graphName, node.getName(), node.getVnf().getName(), true);
		} catch (AlreadyLoadedException e) {
			throw new ClientErrorException(e.getMessage(), 422);
		} catch (NotDefinedException e) {
			throw new ClientErrorException(e.getMessage(), 422);
		}
	}
	
	@Path(NfvDeployer.linksPath)
	public LinksCollection getLinks() {
		return links;
	}
	
	@GET
	@Path(NfvDeployer.reachableHostsPath)
    @ApiOperation(value = "Get the host list")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Hosts.class),
    		@ApiResponse(code = 404, message = "Not Found"),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Hosts getReachableHosts() {
		return deployer.getReachableHosts(name);
	}

}
