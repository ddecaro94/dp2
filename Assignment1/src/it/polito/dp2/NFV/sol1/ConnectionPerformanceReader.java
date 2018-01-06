package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.sol1.jaxb.ConnectionType;

public class ConnectionPerformanceReader implements it.polito.dp2.NFV.ConnectionPerformanceReader {

	private ConnectionType conn;
	
	public ConnectionPerformanceReader(ConnectionType conn) {
		this.conn = conn;
	}

	@Override
	public int getLatency() {
		if(conn.getPerformance() != null) {
			return conn.getPerformance().getLatency().intValue();
		} else {
			return Integer.MAX_VALUE;
		}
	}

	@Override
	public float getThroughput() {
		if(conn.getPerformance() != null) {
			return conn.getPerformance().getThroughput().floatValue();
		} else {
			return 0;
		}
	}

}
