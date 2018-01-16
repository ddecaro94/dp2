package it.polito.dp2.NFV.sol3.client1;

import java.net.URI;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol3.client1.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client1.data.Nffg;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer.Index.Nffgs.NffgName.Nodes;

public class NffgReader implements it.polito.dp2.NFV.NffgReader {

	private String nffgName;
	private URI graphUri;
	private NfvDeployer.Index.Nffgs.NffgName.Nodes nodesService;
	
	public NffgReader(URI graphUri) {
		this.graphUri = graphUri;
		this.nodesService = new Nodes(NfvDeployer.createClient(), graphUri);
	}

	@Override
	public String getName() {
		return NfvDeployer.createClient().resource(graphUri).get(Nffg.class).getName();
	}

	@Override
	public Calendar getDeployTime() {
	
		Nffg graph = NfvDeployer.createClient().resource(graphUri).get(Nffg.class);
		
		if (graph != null) {
			if (graph.getDeployTime() != null) {
				return graph.getDeployTime().toGregorianCalendar();
			}
		}
	
		return null;

	}

	@Override
	public NodeReader getNode(String nodeName) {
		for (NamedEntity node : this.nodesService.getAsNodesXml().getNode()) {
			if (node.getName().equals(nodeName)) return new it.polito.dp2.NFV.sol3.client1.NodeReader(URI.create(node.getHref()));
		}
		return null;
	}

	@Override
	public Set<NodeReader> getNodes() {
		Set<NodeReader> nodes = new HashSet<>();
		for (NamedEntity node : this.nodesService.getAsNodesXml().getNode()) {
			nodes.add(new it.polito.dp2.NFV.sol3.client1.NodeReader(URI.create(node.getHref())));
		}
		return nodes;
	}

}
