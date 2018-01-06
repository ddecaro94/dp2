package it.polito.dp2.NFV.sol1;

import java.util.Set;
import java.util.stream.Collectors;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol1.jaxb.HostType;
import it.polito.dp2.NFV.sol1.jaxb.NodeType;

public class HostReader implements it.polito.dp2.NFV.HostReader {
	private HostType host;

	public HostReader(HostType host) {
		this.host = host;
	}

	@Override
	public String getName() {
		return this.host.getName();
	}

	@Override
	public int getAvailableMemory() {
		return this.host.getAvailableMemory().intValue();
	}

	@Override
	public int getAvailableStorage() {
		return this.host.getAvailableStorage().intValue();
	}

	@Override
	public int getMaxVNFs() {
		return this.host.getMaxVNFs().intValue();
	}

	@Override
	public Set<NodeReader> getNodes() {
		return this.host.getDeployedNodes().getDeployedNode().parallelStream().map(t->new it.polito.dp2.NFV.sol1.NodeReader((NodeType) t.getName())).collect(Collectors.toSet());
	}

}
