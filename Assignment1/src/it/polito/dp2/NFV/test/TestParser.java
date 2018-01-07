package it.polito.dp2.NFV.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;

public class TestParser {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestParser wf;
		try {
			System.setProperty("it.polito.dp2.NFV.sol1.NfvInfo.file", "file.xml");
			System.setProperty("it.polito.dp2.NFV.NfvReaderFactory", "it.polito.dp2.NFV.sol1.NfvReaderFactory");
			wf = new TestParser();
			wf.printAll();
		} catch (NfvReaderException e) {
			System.err.println("Could not instantiate data generator.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private NfvReader monitor;

	private DateFormat dateFormat;

	/**
	 * Default constructror
	 * 
	 * @throws NfvReaderException
	 */
	public TestParser() throws NfvReaderException {
		it.polito.dp2.NFV.NfvReaderFactory factory = NfvReaderFactory.newInstance();
		monitor = factory.newNfvReader();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	}

	public TestParser(NfvReader monitor) {
		super();
		this.monitor = monitor;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	}

	private StringBuffer makeLine(char c) {
		StringBuffer line = new StringBuffer(132);

		for (int i = 0; i < 132; ++i) {
			line.append(c);
		}
		return line;
	}

	public void printAll() {
		printLine(' ');
		printHosts();
		printCatalog();
		printNffgs();
		printPerformance();
	}

	private void printBlankLine() {
		System.out.println(" ");
	}

	private void printCatalog() {
		Set<VNFTypeReader> set = monitor.getVNFCatalog();

		/* Print the header of the table */
		printHeader('#', "#Information about the CATALOG of VNFs");
		printHeader("#Number of VNF types: " + set.size());
		printHeader("#List of VNF types:");

		// For each VNF type print name and class
		for (VNFTypeReader vnfType_r : set) {
			System.out.println("Type name " + vnfType_r.getName() + "\tFunc type: "
					+ vnfType_r.getFunctionalType().value() + "\tRequired Mem:" + vnfType_r.getRequiredMemory()
					+ "\tRequired Sto:" + vnfType_r.getRequiredStorage());
		}
		printBlankLine();
	}

	private void printHeader(char c, String header) {
		printLine(c);
		System.out.println(header);
	}

	private void printHeader(String header) {
		System.out.println(header);
	}

	private void printHeader(String header, char c) {
		System.out.println(header);
		printLine(c);
	}

	private void printHosts() {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();

		/* Print the header of the table */
		printHeader('#', "#Information about HOSTS");
		printHeader("#Number of Hosts: " + set.size());
		printHeader("#List of Hosts:");

		// For each Host print related data
		for (HostReader host_r : set) {
			printHeader('%', "###Data for Host " + host_r.getName());

			// Print maximum number of nodes
			printHeader("#Maximum number of nodes: " + host_r.getMaxVNFs());

			// Print available memory
			printHeader("#Available memory: " + host_r.getAvailableMemory());

			// Print available storage
			printHeader("#Available storage: " + host_r.getAvailableStorage());

			// Print allocated nodes
			Set<NodeReader> nodeSet = host_r.getNodes();
			printHeader("#Number of Allocated Nodes: " + nodeSet.size());
			printHeader("#List of Allocated Nodes:");
			for (NodeReader nr : nodeSet)
				System.out.println("Node " + nr.getName() + "\tType: " + nr.getFuncType().getName());
			System.out.println("###End of Allocated nodes");
		}
		printBlankLine();
	}

	private void printLine(char c) {
		System.out.println(makeLine(c));
	}

	private void printNffgs() {
		// Get the list of NF-FGs
		Set<NffgReader> set = monitor.getNffgs(null);

		/* Print the header of the table */
		printHeader('#', "#Information about NF-FGs");
		printHeader("#Number of NF-FGs: " + set.size());
		printHeader("#List of NF-FGs:");

		// For each NFFG print related data
		for (NffgReader nffg_r : set) {
			printHeader('%', "###Data for NF-FG " + nffg_r.getName());

			// Print deploy time
			Calendar deployTime = nffg_r.getDeployTime();
			printHeader("#Deploy time: " + dateFormat.format(deployTime.getTime()));

			// Print nodes
			Set<NodeReader> nodeSet = nffg_r.getNodes();
			printHeader("#Number of Nodes: " + nodeSet.size());
			printHeader("#List of Nodes: ");
			for (NodeReader nr : nodeSet) {
				printHeader('+', "+Node " + nr.getName() + "\tType: " + nr.getFuncType().getName() + "\t Allocated on: "
						+ nr.getHost().getName() + "\tNumber of links: " + nr.getLinks().size());
				Set<LinkReader> linkSet = nr.getLinks();
				System.out.println("+List of Links for node " + nr.getName());
				printHeader("Link name \tsource \tdestination", '-');
				for (LinkReader lr : linkSet)
					System.out.println(lr.getName() + "\t" + lr.getSourceNode().getName() + "\t"
							+ lr.getDestinationNode().getName());
				printLine('-');
			}
			printLine('+');
			System.out.println("###End of Nodes");
		}
		printBlankLine();
	}

	private void printPerformance() {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();

		/* Print the header of the table */
		printHeader('#', "#Information about the Performance of Host to Host connections");
		printHeader("#Throughput Matrix:");
		for (HostReader sri : set) {
			System.out.print("\t" + sri.getName());
		}
		System.out.println(" ");
		for (HostReader sri : set) {
			System.out.print(sri.getName());
			for (HostReader srj : set) {
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				System.out.print("\t" + cpr.getThroughput());
			}
			System.out.println(" ");
		}
		printBlankLine();
		printHeader("#Latency Matrix:");
		for (HostReader sri : set) {
			System.out.print("\t" + sri.getName());
		}
		System.out.println(" ");
		for (HostReader sri : set) {
			System.out.print(sri.getName());
			for (HostReader srj : set) {
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				System.out.print("\t" + cpr.getLatency());
			}
			System.out.println(" ");
		}

	}

}
