package it.polito.dp2.NFV.sol3.client1;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import com.sun.jersey.api.client.WebResource;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol3.client1.data.Host;
import it.polito.dp2.NFV.sol3.client1.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer.Index.Hosts.HostName;
import it.polito.dp2.NFV.sol3.client1.data.Node;

public class HostReader implements it.polito.dp2.NFV.HostReader {
	
	private WebResource hostService;

	public HostReader(URI hostUri) {
		hostService = NfvDeployer.createClient().resource(hostUri);
	}

	@Override
	public String getName() {
		return hostService.get(Host.class).getName();
	}

	@Override
	public int getAvailableMemory() {
		// TODO Auto-generated method stub
		return hostService.get(Host.class).getAvailableMemory().intValue();
	}

	@Override
	public int getAvailableStorage() {
		// TODO Auto-generated method stub
		return hostService.get(Host.class).getAvailableStorage().intValue();
	}

	@Override
	public int getMaxVNFs() {
		// TODO Auto-generated method stub
		return hostService.get(Host.class).getMaxVNFs().intValue();
	}

	@Override
	public Set<NodeReader> getNodes() {
		Set<NodeReader> nodes = new HashSet<>();
		Host h = hostService.get(Host.class);
		for (NamedEntity n : hostService.get(Host.class).getDeployedNodes().getNode()) {
			Node node = NfvDeployer.createClient().resource(n.getHref()).get(Node.class);
			nodes.add(new it.polito.dp2.NFV.sol3.client1.NodeReader(URI.create(node.getHref())));
		}
		return nodes;
	}

}
