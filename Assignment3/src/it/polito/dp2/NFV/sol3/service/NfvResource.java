package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;
import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;

import org.glassfish.hk2.api.Immediate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.lab3.AllocationException;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.lab3.UnknownEntityException;
import it.polito.dp2.NFV.sol3.service.data.Neo4JSimpleXML;
import it.polito.dp2.NFV.sol3.service.data.Neo4JSimpleXML.Data;
import it.polito.dp2.NFV.sol3.service.model.*;

@Singleton
@Path("/")
@Api(value = "/")
@ApiModel(description = "A resource representing a Network Function Virtualization")
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NfvResource {
	
	public static final String catalogPath = "catalog";
	public static final String connectionsPath = "connections";
	public static final String hostsPath = "hosts";
	public static final String linksPath = "links";
	public static final String nffgsPath = "nffgs";
	public static final String nodesPath = "nodes";
	public static final String reachableHostsPath = "reachableHosts";
	
	private NfvDeployer deployer = NfvDeployer.getInstance();
	private Nfv nfv = new Nfv();
	private CatalogCollection catalog = new CatalogCollection();
	private NffgsCollection nffgs = new NffgsCollection();
	private HostsCollection hosts = new HostsCollection();
	
	private Data dataApi;
	private NfvReader reader;
	private String baseUri;
	private String dataUri;
	
	public NfvResource() throws NfvReaderException, FactoryConfigurationError, AlreadyLoadedException, UnknownEntityException, AllocationException, InvalidEntityException, DatatypeConfigurationException, ServiceException {
		baseUri = System.getProperty("it.polito.dp2.NFV.lab3.URL", "http://localhost:8080/NfvDeployer/rest/");
		dataUri = System.getProperty("it.polito.dp2.NFV.lab3.Neo4JSimpleXMLURL",
				"http://localhost:8080/Neo4JSimpleXML/rest");

		reader = NfvReaderFactory.newInstance().newNfvReader();
		dataApi = Neo4JSimpleXML.data(Neo4JSimpleXML.createClient(), URI.create(dataUri));

		this.nfv.setHosts(createHyperlink(baseUri + hostsPath));
		this.nfv.setNffgs(createHyperlink(baseUri + nffgsPath));
		this.nfv.setVnfCatalog(createHyperlink(baseUri + catalogPath));

		for (VNFTypeReader t : reader.getVNFCatalog()) {
			deployer.createVnf(FunctionalType.fromValue(t.getFunctionalType().toString()), t.getName(),
					BigInteger.valueOf(t.getRequiredMemory()), BigInteger.valueOf(t.getRequiredStorage()));

		}

		// load all hosts
		for (HostReader host : reader.getHosts()) {
			try {
				deployer.createHost(host.getName(), host.getAvailableMemory(), host.getAvailableStorage(),
						host.getMaxVNFs());
			} catch (AlreadyLoadedException e) {
				throw new ConflictException(e);
			} catch (ServiceException e) {
				e.printStackTrace();
				throw new InternalServerErrorException(e);
			}
		}

		// load connections
		for (HostReader hosti : reader.getHosts()) {
			for (HostReader hostj : reader.getHosts()) {
				
				deployer.createConnection(hosti.getName(), hostj.getName(), reader.getConnectionPerformance(hosti, hostj).getLatency(), reader.getConnectionPerformance(hosti, hostj).getThroughput());
			}
		}

		// create NF-FG0
		NffgReader graph = reader.getNffg("Nffg0");

		NewNffg nffg0 = new NewNffg();
		nffg0.setName(graph.getName());
		NewNffg.Nodes nodes = new NewNffg.Nodes();
		NewNffg.Links links = new NewNffg.Links();
		for (NodeReader n : graph.getNodes()) {
			NewNffg.Nodes.Node node = new NewNffg.Nodes.Node();
			node.setName(n.getName());
			node.setVnf(n.getFuncType().getName());
			node.setPreferredHost(n.getHost().getName());
			for (LinkReader lr : n.getLinks()) {
				NewNffg.Links.Link link = new NewNffg.Links.Link();
				link.setName(lr.getName());
				link.setDestinationNode(lr.getDestinationNode().getName());
				link.setSourceNode(lr.getSourceNode().getName());
				link.setRequiredLatency(BigInteger.valueOf(lr.getLatency()));
				link.setRequiredThroughput(lr.getThroughput());
				links.getLink().add(link);
			}
			nodes.getNode().add(node);
		}
		nffg0.setNodes(nodes);
		nffg0.setLinks(links);

		deployer.deployNffg(nffg0);
	}
	
	@GET
    @ApiOperation(value = "Get the properties for the NFV system")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = Nfv.class),
    		@ApiResponse(code = 500, message = "Internal Server Error")})
	public Nfv getNfv() {
		return this.nfv;
	}
	
	@Path("catalog")
	public CatalogCollection getCatalog() {
		return this.catalog;
	}
	
	@Path("nffgs")
	public NffgsCollection getNffgs() {
		return this.nffgs;
	}
	
	@Path("hosts")
	public HostsCollection getHosts() {
		return this.hosts;
	}
	
	private Hyperlink createHyperlink(String ref) {
		Hyperlink l = new Hyperlink();
		l.setHref(ref);
		return l;
	}
	

}
