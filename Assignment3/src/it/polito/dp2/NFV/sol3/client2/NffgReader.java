package it.polito.dp2.NFV.sol3.client2;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.WebApplicationException;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.sol3.client1.data.Nffg;
import it.polito.dp2.NFV.sol3.client2.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer.Index.Nffgs.NffgName.Nodes;

public class NffgReader implements it.polito.dp2.NFV.NffgReader {

	private Nffg nffg;
	private Map<String, NodeReader> nodes;
	
	public NffgReader(URI uri) throws ServiceException {
		try {
			nffg = NfvDeployer.createClient().resource(uri).get(Nffg.class);
			this.nodes = new HashMap<>();
			it.polito.dp2.NFV.sol3.client2.data.Nodes nodes = new NfvDeployer.Index.Nffgs.NffgName.Nodes(NfvDeployer.createClient(), uri).getAsNodesXml();
			for (NamedEntity n : nodes.getNode()) {
				this.nodes.put(n.getName(), new it.polito.dp2.NFV.sol3.client2.NodeReader(URI.create(n.getHref())));
			}
		} catch (WebApplicationException e) {
			throw new ServiceException();
		}
	}

	@Override
	public String getName() {
		return nffg.getName();
	}

	@Override
	public Calendar getDeployTime() {
		if (nffg.getDeployTime() != null) {
			return nffg.getDeployTime().toGregorianCalendar();
		}
		return null;
	}

	@Override
	public NodeReader getNode(String nodeName) {
		if (nodes.containsKey(nodeName)) return nodes.get(nodeName);
		return null;
	}

	@Override
	public Set<NodeReader> getNodes() {
		return nodes.values().stream().collect(Collectors.toSet());
	}

}
