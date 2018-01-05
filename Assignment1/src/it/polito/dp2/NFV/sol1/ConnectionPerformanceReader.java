package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.sol1.jaxb.ConnectionPerformanceType;

public class ConnectionPerformanceReader implements it.polito.dp2.NFV.ConnectionPerformanceReader {

	private ConnectionPerformanceType p;
	
	public ConnectionPerformanceReader(ConnectionPerformanceType p) {
		this.p = p;
	}

	@Override
	public int getLatency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getThroughput() {
		// TODO Auto-generated method stub
		return 0;
	}

}
