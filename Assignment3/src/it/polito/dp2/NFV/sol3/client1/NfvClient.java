package it.polito.dp2.NFV.sol3.client1;

import java.math.BigInteger;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang.math.RandomUtils;

import com.sun.jersey.api.client.ClientResponse;

import it.polito.dp2.NFV.lab3.AllocationException;
import it.polito.dp2.NFV.lab3.LinkDescriptor;
import it.polito.dp2.NFV.sol3.client1.DeployedNffg;
import it.polito.dp2.NFV.sol3.client1.data.NewNffg;
import it.polito.dp2.NFV.sol3.client1.data.NewNffg.Links;
import it.polito.dp2.NFV.sol3.client1.data.NewNffg.Links.Link;
import it.polito.dp2.NFV.sol3.client1.data.NewNffg.Nodes;
import it.polito.dp2.NFV.sol3.client1.data.NewNffg.Nodes.Node;
import it.polito.dp2.NFV.sol3.client1.data.Nffg;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer.Root.Nffgs;
import it.polito.dp2.NFV.lab3.NffgDescriptor;
import it.polito.dp2.NFV.lab3.NodeDescriptor;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.lab3.UnknownEntityException;

public class NfvClient implements it.polito.dp2.NFV.lab3.NfvClient {
	private NfvDeployer.Root.Nffgs serviceApi;
	private Map<NodeDescriptor, String> nodeNames;
	public NfvClient() {
		String baseUri = System.getProperty("it.polito.dp2.NFV.lab3.URL", "http://localhost:8080/NfvDeployer/rest/");
		serviceApi = new Nffgs(NfvDeployer.createClient(), URI.create(baseUri));
	}

	@Override
	public DeployedNffg deployNffg(NffgDescriptor nffg) throws AllocationException, ServiceException {
		
		nodeNames = new HashMap<>();
		
		NewNffg newGraph = new NewNffg();
		newGraph.setName("Nffg"+RandomUtils.nextInt());
		
		Nodes nodes = new Nodes();
		Links links = new Links();
		
		for (NodeDescriptor n : nffg.getNodes()) {
			Node newNode = new Node();
			String nodeName = n.getFuncType().getName()+newGraph.getName()+RandomUtils.nextInt();
			newNode.setName(nodeName);
			newNode.setVnf(n.getFuncType().getName());
			newNode.setPreferredHost(n.getHostName());
			nodes.getNode().add(newNode);
			nodeNames.put(n, nodeName);
		}
		
		for (NodeDescriptor n : nffg.getNodes()) {
			
			for (LinkDescriptor l : n.getLinks()) {
				Link newLink = new Link();
				String linkName = "Link"+nodeNames.get(n)+nodeNames.get(l.getDestinationNode());
				newLink.setName(linkName);
				newLink.setSourceNode(nodeNames.get(n));
				newLink.setDestinationNode(nodeNames.get(l.getDestinationNode()));
				newLink.setRequiredLatency(BigInteger.valueOf(l.getLatency()));
				newLink.setRequiredThroughput(l.getThroughput());
				
				links.getLink().add(newLink);
			}
			
		}
		
		newGraph.setNodes(nodes);
		newGraph.setLinks(links);
				
		
		try {
			ClientResponse resp = serviceApi.postXml(newGraph, ClientResponse.class);
			
			if (resp.getStatus() >= 500) throw new ServiceException();
			if (resp.getStatus() == 409) throw new AllocationException(resp.getEntity(String.class));
			if (resp.getStatus() >= 400) throw new ServiceException(resp.getEntity(String.class));
			if (resp.getStatus() == 200) {
				Nffg loadedGraph = resp.getEntity(Nffg.class);
				return new DeployedNffg(loadedGraph);
			}
			throw new ServiceException();
			
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}
		
		
	}

	@Override
	public DeployedNffg getDeployedNffg(String name) throws UnknownEntityException, ServiceException {
		// TODO Auto-generated method stub
		ClientResponse resp = serviceApi.nffgName(name).getAsXml(ClientResponse.class);
		
		if (resp.getStatus() >= 500) throw new ServiceException();
		if (resp.getStatus() == 404) throw new UnknownEntityException(resp.getEntity(String.class));
		if (resp.getStatus() >= 400) throw new ServiceException(resp.getEntity(String.class));
		if (resp.getStatus() == 200) {
			Nffg nffg = resp.getEntity(Nffg.class);
			return new DeployedNffg(nffg);
		}
		throw new ServiceException();
		 
	}

}
