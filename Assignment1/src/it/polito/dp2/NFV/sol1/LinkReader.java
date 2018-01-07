package it.polito.dp2.NFV.sol1;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol1.jaxb.ConnectionType;
import it.polito.dp2.NFV.sol1.jaxb.NodeType;

public class LinkReader implements it.polito.dp2.NFV.LinkReader {

	private ConnectionType link;
	private NodeReader source;
	private NfvReader nfv;

	public LinkReader(ConnectionType l, NodeReader source, NfvReader nfv) {
		this.link = l;
		this.source = source;
		this.nfv = nfv;
	}

	@Override
	public NodeReader getDestinationNode() {
		Map<String, NodeReader> allNodes = new HashMap<String, NodeReader>();
		for (NffgReader nffg : this.nfv.getNffgs(null)) {
			allNodes.putAll(nffg.getNodes().stream().collect(Collectors.toMap(NodeReader::getName, p -> p)));
		}
		return allNodes.get(((NodeType) link.getDestination()).getName());
	}

	@Override
	public int getLatency() {
		// in absence of the value, a latency of the maximum integer value represents
		// the minimum required latency
		if (this.link.getPerformance() != null) {
			if (this.link.getPerformance().getLatency() != null) {
				return this.link.getPerformance().getLatency().intValue();
			} else {
				return Integer.MAX_VALUE;
			}
		} else {
			return Integer.MAX_VALUE;
		}
	}

	@Override
	public String getName() {
		return this.link.getName();
	}

	@Override
	public NodeReader getSourceNode() {
		return source;
	}

	@Override
	public float getThroughput() {
		// in absence of the value, a throughput of 0 represents the minimum required
		// throughput
		if (this.link.getPerformance() != null) {
			if (this.link.getPerformance().getThroughput() != null) {
				return this.link.getPerformance().getThroughput().floatValue();
			} else {
				return 0;
			}

		} else {
			return 0;
		}
	}
}
