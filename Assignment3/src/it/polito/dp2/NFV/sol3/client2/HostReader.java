package it.polito.dp2.NFV.sol3.client2;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.sol3.client2.data.Host;
import it.polito.dp2.NFV.sol3.client2.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer;

public class HostReader implements it.polito.dp2.NFV.HostReader {

	private Host host;
	private Set<URI> nodes;

	public HostReader(URI uri) throws ServiceException {
		try {
			this.host = NfvDeployer.createClient().resource(uri).get(Host.class);
			nodes = new HashSet<>();
			for (NamedEntity n : host.getDeployedNodes().getNode()) {
				nodes.add(URI.create(n.getHref()));
			}
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String getName() {
		return host.getName();
	}

	@Override
	public int getAvailableMemory() {
		return (host.getAvailableMemory() != null) ? host.getAvailableMemory().intValue() : 0;
	}

	@Override
	public int getAvailableStorage() {
		return (host.getAvailableStorage() != null) ? host.getAvailableStorage().intValue() : 0;
	}

	@Override
	public int getMaxVNFs() {
		return (host.getMaxVNFs() != null) ? host.getMaxVNFs().intValue() : 0;
	}

	@Override
	public Set<NodeReader> getNodes() {
		Set<NodeReader> deployedNodes = new HashSet<>();
		for (URI n : nodes) {
			try {
				deployedNodes.add(new it.polito.dp2.NFV.sol3.client2.NodeReader(n));
			} catch (ServiceException e) {
				return null;
			}
		}
		return deployedNodes;
	}

}
