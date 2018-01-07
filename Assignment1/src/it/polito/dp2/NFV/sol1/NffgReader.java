package it.polito.dp2.NFV.sol1;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol1.jaxb.NffgType;
import it.polito.dp2.NFV.sol1.jaxb.NodeType;

public class NffgReader implements it.polito.dp2.NFV.NffgReader {
	private String name;
	private Calendar deployTime;
	private Map<String, NodeReader> nodes;

	public NffgReader(NffgType nffg, NfvReader nfv) {
		this.name = nffg.getName();
		if (nffg.getDeployTime() != null)
			this.deployTime = nffg.getDeployTime().toGregorianCalendar();
		else {
			this.deployTime = Calendar.getInstance();
			this.deployTime.setTime(new Date(Long.MIN_VALUE));
		}
		this.nodes = new HashMap<String, NodeReader>();
		for (NodeType node : nffg.getNodes().getNode()) {
			nodes.put(node.getName(), new it.polito.dp2.NFV.sol1.NodeReader(node, this, nfv));
		}
	}

	@Override
	public Calendar getDeployTime() {
		return this.deployTime;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public NodeReader getNode(String nodeName) {
		return this.nodes.getOrDefault(nodeName, null);
	}

	@Override
	public Set<NodeReader> getNodes() {
		return this.nodes.values().stream().collect(Collectors.toSet());
	}

}
