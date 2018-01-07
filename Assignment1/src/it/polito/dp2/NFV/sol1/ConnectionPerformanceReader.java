package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.sol1.jaxb.ConnectionType;

public class ConnectionPerformanceReader implements it.polito.dp2.NFV.ConnectionPerformanceReader {

	private ConnectionType conn;

	public ConnectionPerformanceReader(ConnectionType conn) {
		this.conn = conn;
	}

	@Override
	public int getLatency() {
		// in absence of the value, a latency of the maximum integer value represents
		// two unreachable hosts
		if (conn.getPerformance() != null) {
			if (conn.getPerformance().getLatency() != null) {
				return conn.getPerformance().getLatency().intValue();
			} else {
				return Integer.MAX_VALUE;
			}
		} else {
			return Integer.MAX_VALUE;
		}
	}

	@Override
	public float getThroughput() {
		// in absence of the value, a throughput of 0 represents two unreachable hosts
		if (conn.getPerformance() != null) {
			if (conn.getPerformance().getThroughput() != null) {
				return conn.getPerformance().getThroughput().floatValue();
			} else {
				return 0;
			}

		} else {
			return 0;
		}
	}

}
