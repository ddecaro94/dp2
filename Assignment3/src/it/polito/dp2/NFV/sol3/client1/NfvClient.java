package it.polito.dp2.NFV.sol3.client1;

import java.math.BigInteger;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.math.RandomUtils;

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
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer.Index.Nffgs;
import it.polito.dp2.NFV.lab3.NffgDescriptor;
import it.polito.dp2.NFV.lab3.NodeDescriptor;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.lab3.UnknownEntityException;

public class NfvClient implements it.polito.dp2.NFV.lab3.NfvClient {
	private NfvDeployer.Index.Nffgs serviceApi;
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
			String nodeName = n.getFuncType().getName()+RandomUtils.nextInt();
			newNode.setName(nodeName);
			newNode.setVnf(n.getFuncType().getName());
			newNode.setPreferredHost(n.getHostName());
			nodes.getNode().add(newNode);
			nodeNames.put(n, nodeName);
		}
		
		
		
		for (NodeDescriptor n : nffg.getNodes()) {
			
			for (LinkDescriptor l : n.getLinks()) {
				Link newLink = new Link();
				String linkName = "Link"+RandomUtils.nextInt();
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
			Nffg loadedGraph = serviceApi.postXmlAsNffg(newGraph);
			
			return new DeployedNffg(loadedGraph);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}

	@Override
	public DeployedNffg getDeployedNffg(String name) throws UnknownEntityException, ServiceException {
		// TODO Auto-generated method stub
		Nffg nffg = serviceApi.nffgName(name).getAsNffgXml();
		
		return new DeployedNffg(nffg); 
	}

}
