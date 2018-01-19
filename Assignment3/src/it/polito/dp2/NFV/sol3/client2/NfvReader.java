package it.polito.dp2.NFV.sol3.client2;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.lab3.UnknownEntityException;
import it.polito.dp2.NFV.sol3.client2.data.Catalog;
import it.polito.dp2.NFV.sol3.client2.data.Hosts;
import it.polito.dp2.NFV.sol3.client2.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client2.data.Nffgs;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer.Index;
import it.polito.dp2.NFV.sol3.client2.data.Vnf;

public class NfvReader implements it.polito.dp2.NFV.NfvReader {

	private Index nfvService;

	private Set<HostReader> hosts;
	private Set<NffgReader> nffgs;
	private Set<VNFTypeReader> catalog;
	private Map<String, Map<String, ConnectionPerformanceReader>> connections;
	
	public NfvReader(URI baseUri) throws UnknownEntityException, ServiceException {
		this.nfvService = new Index(NfvDeployer.createClient(), baseUri);
		Catalog c = nfvService.catalog().getAsCatalogXml();
		Hosts h = nfvService.hosts().getAsHostsXml();
		Nffgs n = nfvService.nffgs().getAsNffgsXml();
		connections = new HashMap<>();
		catalog = new HashSet<>();
		hosts = new HashSet<>();
		nffgs = new HashSet<>();
		
		
		for (Vnf v : c.getVnf()) {
			catalog.add(new it.polito.dp2.NFV.sol3.client2.VNFTypeReader(URI.create(v.getHref())));
		}
		for (NamedEntity host : h.getHost()) {
			hosts.add(new it.polito.dp2.NFV.sol3.client2.HostReader(URI.create(host.getHref())));
		}
		for (NamedEntity graph : n.getNffg()) {
			nffgs.add(new it.polito.dp2.NFV.sol3.client2.NffgReader(URI.create(graph.getHref())));
		}
		
		for (NamedEntity hosti : h.getHost()) {
			connections.put(hosti.getName(), new HashMap<>());
			for (NamedEntity hostj : h.getHost()) {
				connections.get(hosti.getName()).put(hostj.getName(), new it.polito.dp2.NFV.sol3.client2.ConnectionPerformanceReader(hosti.getName(), hostj.getName(), baseUri));
			}
		}
		
		
	}

	@Override
	public ConnectionPerformanceReader getConnectionPerformance(HostReader src, HostReader dst) {
		if (src == null || dst == null) return null;
		if (connections.containsKey(src.getName())) {
			if (connections.get(src.getName()).containsKey(dst.getName())) {
				return connections.get(src.getName()).get(dst.getName());
			}
		}
		return null;
	}

	@Override
	public HostReader getHost(String hostName) {
		for (HostReader h : this.hosts) {
			if (h.getName().equals(hostName)) return h;
		}
		return null;
	}

	@Override
	public Set<HostReader> getHosts() {
		return hosts;
	}

	@Override
	public NffgReader getNffg(String nffgName) {
		for (NffgReader n : this.nffgs) {
			if (n.getName().equals(nffgName)) return n;
		}
		return null;
	}

	@Override
	public Set<NffgReader> getNffgs(Calendar deployTime) {
		Set<NffgReader> res = new HashSet<>();
		for (NffgReader n : this.nffgs) {
			if (deployTime == null) {
				res.add(n);
			} else {
				if (n.getDeployTime() != null) {
					if (n.getDeployTime().compareTo(deployTime) >= 0) res.add(n);
				}
			}
		}
		return res;
	}

	@Override
	public Set<VNFTypeReader> getVNFCatalog() {
		return catalog;
	}

}
