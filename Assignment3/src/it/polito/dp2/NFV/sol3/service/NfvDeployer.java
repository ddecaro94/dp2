package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.sol3.model.*;

public class NfvDeployer {
	private static final NfvDeployer INSTANCE = new NfvDeployer();
	private String nfvPath = "";
	private String hostsPath = "hosts";
	private String nffgsPath = "nffgs";
	private String catalogPath = "catalog";
	private String connectionsPath = "connections";

	private Nfv nfv;
	private Hosts hosts;
	private Nffgs nffgs;
	private Catalog catalog;

	private Map<String, Host> hostMap;
	private Map<String, String> hostIds;
	
	private Map<String, Connections> connections;
	
	private Map<String, Vnf> vnfs;
    
    private NfvDeployer() {
    	this.nfv = new Nfv();
    	/*
    	try {
			NfvReader referenceNfvReader = NfvReaderFactory.newInstance().newNfvReader();
		} catch (NfvReaderException e) {
			System
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    	
    	Hyperlink hostsRef = createLink("/hosts");
    	Hyperlink nffgsRef = createLink("/nffgs");
    	Hyperlink catalogRef = createLink("/catalog");
    	
    	this.nfv.setHosts(hostsRef);
    	this.nfv.setNffgs(nffgsRef);
    	this.nfv.setVnfCatalog(catalogRef);
    	
    	this.hosts = new Hosts();
    	this.hosts.getHost().add(createNamedRef("host1", "/hosts/host1"));
    	
    	this.nffgs = new Nffgs();
    	this.nffgs.getNffg().add(createNamedRef("Nffg0", "/nffgs/Nffg0"));
    	
    	this.catalog = new Catalog();
    	this.catalog.getVnf().add(createVnf(FunctionalType.FW, "fw1", "/catalog/fw1", BigInteger.valueOf(120), BigInteger.valueOf(240)));
    	
    	this.hostMap = new HashMap<>();
    	this.hostIds = new HashMap<>();
    	
    	this.hostIds.put("host1", "1");
    	this.hostMap.put("1", createHost("host1", 0, 0, 0));
    	
    	this.connections = new HashMap<>();
    };



	public static NfvDeployer getInstance() {
		return INSTANCE;
	}

	public Nfv getNfv() {
		return this.nfv;
	}

	private Hyperlink createLink(String ref) {
		Hyperlink l = new Hyperlink();
		l.setHref(ref);
		return l;
	}

	public Hosts getHosts() {
		return this.hosts;
	}

	public Nffgs getNffgs() {
		return this.nffgs;
	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	private NamedEntity createNamedRef(String name, String ref) {
		NamedEntity e = new NamedEntity();
		e.setHref(ref);
		e.setName(name);
		return e;
	}

	private Vnf createVnf(FunctionalType type, String name, String href, BigInteger reqMemory, BigInteger reqStorage) {
		Vnf vnf = new Vnf();
		vnf.setFunctionalType(type);
		vnf.setName(name);
		vnf.setHref(href);
		vnf.setRequiredMemory(reqMemory);
		vnf.setRequiredStorage(reqStorage);
		return vnf;
	}
	
	private Host createHost(String name, Integer availableMem, Integer availableStorage, Integer maxVnfs) {
		Host h = new Host();
		h.setHref(this.nfvPath+"/"+this.hostsPath+"/"+name);
		h.setName(name);
		h.setAvailableMemory(availableMem.toString());
		h.setAvailableStorage(availableStorage.toString());
		h.setMaxVNFs(maxVnfs.toString());
		h.setConnections(createLink(nfvPath+"/"+hostsPath+"/"+connectionsPath));
		
		return h;
	}

	public Host getHostByName(String name) {
		String id = this.hostIds.get(name);
		return this.hostMap.get(id);
	}

	public Vnf getVnfByName(String name) {
		return vnfs.get(name);
	}

	public Connections getConnections(String hostName) {
		// TODO Auto-generated method stub
		String id = hostIds.get(hostName);
		return this.connections.get(id);
	}

}
