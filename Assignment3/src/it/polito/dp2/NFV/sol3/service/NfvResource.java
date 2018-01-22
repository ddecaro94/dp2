package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;
import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeConfigurationException;

import io.swagger.annotations.Api;
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
import it.polito.dp2.NFV.sol3.service.model.*;

@Singleton
@Path("")
@Api(value = "")
@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
public class NfvResource {
	
	private Nfv nfv;
	private NfvDeployer deployer = null;
	private CatalogCollection catalog;
	private NffgsCollection nffgs;
	private HostsCollection hosts;

	private NfvReader reader;
	


	public NfvResource(@Context UriInfo uriinfo) throws NfvReaderException, FactoryConfigurationError, AlreadyLoadedException,
			UnknownEntityException, AllocationException, DatatypeConfigurationException, ServiceException {
		reader = NfvReaderFactory.newInstance().newNfvReader();

		try {
			this.deployer = NfvDeployer.getInstance(uriinfo.getBaseUri());
			 
			this.nfv = deployer.createNfv();
			this.catalog = new CatalogCollection();
			this.nffgs = new NffgsCollection();
			this.hosts = new HostsCollection();			
			
			
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

					deployer.createConnection(hosti.getName(), hostj.getName(),
							reader.getConnectionPerformance(hosti, hostj).getLatency(),
							reader.getConnectionPerformance(hosti, hostj).getThroughput());
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
			
		} catch (InvalidEntityException e) {
			throw new NfvReaderException(e);
		}
	}

	@GET
	@ApiOperation(value = "Get the properties for the NFV system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Nfv.class),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Nfv getNfv() {
		return this.nfv;
	}

	@Path(NfvDeployer.catalogPath)
	public CatalogCollection getCatalog() {
		return this.catalog;
	}

	@Path(NfvDeployer.nffgsPath)
	public NffgsCollection getNffgs() {
		return this.nffgs;
	}

	@Path(NfvDeployer.hostsPath)
	public HostsCollection getHosts() {
		return this.hosts;
	}

}
