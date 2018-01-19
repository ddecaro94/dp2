package it.polito.dp2.NFV.sol3.client2;

import java.net.URI;

import javax.ws.rs.WebApplicationException;

import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.lab3.UnknownEntityException;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client2.data.Connection;
import it.polito.dp2.NFV.sol3.client2.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer.Index.Hosts;
import it.polito.dp2.NFV.sol3.client2.data.Connections;

public class ConnectionPerformanceReader implements it.polito.dp2.NFV.ConnectionPerformanceReader {

	private Hosts hostService;
	private Connection conn;

	public ConnectionPerformanceReader(String src, String dst, URI baseUri)
			throws UnknownEntityException, ServiceException {
		boolean foundSrc = false;
		boolean foundDst = false;
		hostService = new Hosts(NfvDeployer.createClient(), baseUri);
		try {
			for (NamedEntity n : hostService.getAsHostsXml().getHost()) {
				if (n.getName().equals(src)) {
					foundSrc = true;
					Connections conns = hostService.hostName(src).connections().getAsConnectionsXml();

					for (Connection c : conns.getConnection()) {
						if (c.getDst().equals(dst)) {
							foundDst = true;
							conn = c;
						}
					}
				}
			}
			if (!foundSrc && !foundDst) {
				if (!foundDst) {
					throw new UnknownEntityException("Hosts " + src + ", " + dst + " not found");
				} else {
					throw new UnknownEntityException("Host " + src + " not found");
				}
			}
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public int getLatency() {
		return (conn.getLatency() != null) ? conn.getLatency().intValue() : 0;
	}

	@Override
	public float getThroughput() {
		return conn.getThroughput();
	}

}
