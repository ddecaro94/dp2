package it.polito.dp2.NFV.sol1;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol1.jaxb.ConnectionType;
import it.polito.dp2.NFV.sol1.jaxb.HostType;
import it.polito.dp2.NFV.sol1.jaxb.Nfv;

public class NfvReader implements it.polito.dp2.NFV.NfvReader {
	private String filePath;
	private Nfv nfv;
	private Map<String, HostReader> hosts;
	private Map<String, NffgReader> nffgs;
	private Map<String, Map<String, ConnectionPerformanceReader>> connections;
	private Map<String, VNFTypeReader> catalog;

	public NfvReader(String file) throws NfvReaderException {
		this.filePath = file;

		try {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new File("xsd/nfvInfo.xsd"));
			NfvValidationEventCollector validationCollector = new NfvValidationEventCollector();
			JAXBContext jc = JAXBContext.newInstance("it.polito.dp2.NFV.sol1.jaxb");
			Unmarshaller u = jc.createUnmarshaller();

			u.setSchema(schema);

			u.setEventHandler(validationCollector);

			this.nfv = (Nfv) u.unmarshal(new File(this.filePath));

			if (validationCollector.hasEvents()) {
				String details = "";
				for (ValidationEvent event : validationCollector.getEvents()) {

					details = details + event.getSeverity() + ": " + event.getMessage() + " - detected at line: "
							+ event.getLocator().getLineNumber() + "/column: " + event.getLocator().getColumnNumber()
							+ "\n";
				}
				throw new NfvReaderException(details);

			} else {
				this.hosts = new HashMap<String, HostReader>();
				this.connections = new HashMap<String, Map<String, ConnectionPerformanceReader>>();
				this.nffgs = new HashMap<String, NffgReader>();
				this.catalog = new HashMap<String, VNFTypeReader>();

				createNfv(nfv);
			}
		} catch (JAXBException e) {
			throw new NfvReaderException(e);
		} catch (SAXException e) {
			throw new NfvReaderException(e);
		}
	}

	private void createNfv(Nfv nfv) {
		// populate catalog data
		this.catalog.putAll(nfv.getVnfCatalog().getVnf().stream().map(t -> new it.polito.dp2.NFV.sol1.VnfTypeReader(t))
				.collect(Collectors.toMap(VnfTypeReader::getName, p -> p)));

		// create hosts
		this.hosts.putAll(nfv.getHosts().getHost().stream().map(t -> new it.polito.dp2.NFV.sol1.HostReader(t, this))
				.collect(Collectors.toMap(HostReader::getName, p -> p)));

		// create NF-FGs
		this.nffgs.putAll(nfv.getNffgs().getNffg().stream().map(t -> new it.polito.dp2.NFV.sol1.NffgReader(t, this))
				.collect(Collectors.toMap(NffgReader::getName, p -> p)));

		// create connection information
		for (HostType src : nfv.getHosts().getHost()) {
			this.connections.put(src.getName(), new HashMap<String, ConnectionPerformanceReader>());
			for (ConnectionType dst : src.getConnections().getConnection()) {
				this.connections.get(src.getName()).put(((HostType) dst.getDestination()).getName(),
						new it.polito.dp2.NFV.sol1.ConnectionPerformanceReader(dst));
			}
		}
	}

	@Override
	public ConnectionPerformanceReader getConnectionPerformance(HostReader from, HostReader to) {
		return this.connections.getOrDefault(from.getName(), new HashMap<String, ConnectionPerformanceReader>())
				.getOrDefault(to.getName(), null);
	}

	@Override
	public HostReader getHost(String hostName) {
		return this.hosts.getOrDefault(hostName, null);
	}

	@Override
	public Set<HostReader> getHosts() {
		return this.hosts.values().stream().collect(Collectors.toSet());
	}

	@Override
	public NffgReader getNffg(String graphName) {
		return this.nffgs.get(graphName);
	}

	@Override
	public Set<NffgReader> getNffgs(Calendar deployTime) {

		Set<NffgReader> nffgSet = new HashSet<NffgReader>();
		for (NffgReader nffg : this.nffgs.values()) {
			if (deployTime == null) {
				nffgSet.add(nffg);
			} else {
				if (nffg.getDeployTime().compareTo(deployTime) >= 0) {
					nffgSet.add(nffg);
				}
			}
		}

		return nffgSet;
	}

	@Override
	public Set<VNFTypeReader> getVNFCatalog() {
		Set<VNFTypeReader> catalog = new HashSet<VNFTypeReader>();
		catalog.addAll(nfv.getVnfCatalog().getVnf().parallelStream()
				.map(t -> new it.polito.dp2.NFV.sol1.VnfTypeReader(t)).collect(Collectors.toSet()));
		return catalog;
	}

}
