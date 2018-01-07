package it.polito.dp2.NFV.sol1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol1.jaxb.DeployedNode;
import it.polito.dp2.NFV.sol1.jaxb.HostType;
import it.polito.dp2.NFV.sol1.jaxb.NodeType;

public class HostReader implements it.polito.dp2.NFV.HostReader {
	private HostType host;
	private NfvReader nfv;

	public HostReader(HostType host, NfvReader nfv) {
		this.host = host;
		this.nfv = nfv;
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
	public String getName() {
		return this.host.getName();
	}

	@Override
	public Set<NodeReader> getNodes() {
		Map<String, NodeReader> allNodes = new HashMap<String, NodeReader>();
		Set<NodeReader> deployedNodes = new HashSet<NodeReader>();

		for (NffgReader nffg : nfv.getNffgs(null)) {
			allNodes.putAll(nffg.getNodes().stream().collect(Collectors.toMap(NodeReader::getName, p -> p)));
		}

		for (DeployedNode d : this.host.getDeployedNodes().getDeployedNode()) {
			deployedNodes.add(allNodes.get(((NodeType) d.getName()).getName()));
		}

		return deployedNodes;

	}

}
