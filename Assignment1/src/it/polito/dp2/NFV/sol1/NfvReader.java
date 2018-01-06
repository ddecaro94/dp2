package it.polito.dp2.NFV.sol1;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.helpers.ValidationEventImpl;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.sol1.jaxb.ConnectionType;
import it.polito.dp2.NFV.sol1.jaxb.HostType;
import it.polito.dp2.NFV.sol1.jaxb.NffgType;
import it.polito.dp2.NFV.sol1.jaxb.Nfv;
import it.polito.dp2.NFV.HostReader;

import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;

public class NfvReader implements it.polito.dp2.NFV.NfvReader {
	private String filePath;
	private Nfv nfv;
	
	public NfvReader() {
		this.filePath = System.getProperty("it.polito.dp2.NFV.sol1.NfvInfo.file");
		
		try {
	        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
	        Schema schema = sf.newSchema(new File("xsd/nfvInfo.xsd")); 
	        ValidationEventCollector validationCollector= new ValidationEventCollector();
			JAXBContext jc= JAXBContext.newInstance("it.polito.dp2.NFV.sol1.jaxb");
			Unmarshaller u = jc.createUnmarshaller();
			
			u.setSchema(schema);
			
			u.setEventHandler(validationCollector);
			
			this.nfv = (Nfv) u.unmarshal(new File(this.filePath));
			
			if (validationCollector.hasEvents()) {
				for (ValidationEvent event : validationCollector.getEvents()) {
			        System.out.println("\nEVENT");
			        System.out.println("SEVERITY:  " + event.getSeverity());
			        System.out.println("MESSAGE:  " + event.getMessage());
			        System.out.println("LINKED EXCEPTION:  " + event.getLinkedException());
			        System.out.println("LOCATOR");
			        System.out.println("    LINE NUMBER:  " + event.getLocator().getLineNumber());
			        System.out.println("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
			        System.out.println("    OFFSET:  " + event.getLocator().getOffset());
			        System.out.println("    OBJECT:  " + event.getLocator().getObject());
			        System.out.println("    NODE:  " + event.getLocator().getNode());
			        System.out.println("    URL:  " + event.getLocator().getURL());
				}
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ConnectionPerformanceReader getConnectionPerformance(HostReader from, HostReader to) {
		for(HostType src : nfv.getHosts().getHost()) {
			if(src.getName().equals(from.getName())) {
				for(ConnectionType dst : src.getConnections().getConnection()) {
					if ( ((HostType) dst.getDestination()).getName().equals(to.getName())) {
						return new it.polito.dp2.NFV.sol1.ConnectionPerformanceReader(dst);
					}
				}
			}
		}
		return null;
	}

	@Override
	public HostReader getHost(String hostName) {
		for(HostType host : nfv.getHosts().getHost()) {
			if(host.getName().equals(hostName)) {
				return new it.polito.dp2.NFV.sol1.HostReader(host);
			}
		}
		return null;
	}

	@Override
	public Set<HostReader> getHosts() {
		Set<HostReader> hostSet = new HashSet<HostReader>();
		hostSet.addAll(nfv.getHosts().getHost().parallelStream().map(t-> new it.polito.dp2.NFV.sol1.HostReader(t)).collect(Collectors.toSet()));
		return hostSet;
	}

	@Override
	public NffgReader getNffg(String graphName) {
		for(NffgType graph : nfv.getNffgs().getNffg()) {
			if(graph.getName().equals(graphName)) {
				return new it.polito.dp2.NFV.sol1.NffgReader(graph);
			}
		}
		return null;
	}

	@Override
	public Set<NffgReader> getNffgs(Calendar deployTime) {
		Set<NffgReader> nffgSet = new HashSet<NffgReader>();
		Calendar since;
		if (deployTime == null) {
			Calendar minimumDate = Calendar.getInstance();
			minimumDate.setTime(new Date(Long.MIN_VALUE));
			since = minimumDate;
		} else {
			since = deployTime;
		}
		nffgSet.addAll(nfv.getNffgs().getNffg().parallelStream().map(t-> new it.polito.dp2.NFV.sol1.NffgReader(t)).filter(t->t.getDeployTime().compareTo(since) >= 0).collect(Collectors.toSet()));
		return nffgSet;
	}

	@Override
	public Set<VNFTypeReader> getVNFCatalog() {
		Set<VNFTypeReader> catalog = new HashSet<VNFTypeReader>();
		catalog.addAll(nfv.getVnfCatalog().getVnf().parallelStream().map(t-> new it.polito.dp2.NFV.sol1.VnfTypeReader(t)).collect(Collectors.toSet()));
		return catalog;
	}

}
