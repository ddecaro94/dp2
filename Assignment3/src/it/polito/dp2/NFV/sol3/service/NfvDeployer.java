package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;

import it.polito.dp2.NFV.sol3.model.*;

public class NfvDeployer {
    private static final NfvDeployer INSTANCE = new NfvDeployer();
    private String nfvPath = "";
    private String hostsPath = "hosts";
    private String nffgsPath = "hosts";
    private String catalogPath = "hosts";
    
    private Nfv nfv;
    private Hosts hosts;
    private Nffgs nffgs;
	private Catalog catalog;
    
    private NfvDeployer() {
    	this.nfv = new Nfv();
    	
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



}
