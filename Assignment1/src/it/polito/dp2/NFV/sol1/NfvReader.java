package it.polito.dp2.NFV.sol1;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.sol1.jaxb.Nfv;
import it.polito.dp2.NFV.HostReader;

import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.lab1.NfvInfo;

public class NfvReader implements it.polito.dp2.NFV.NfvReader {
	private String filePath;
	private Nfv nfv;
	
	public NfvReader() {
		this.filePath = System.getProperty("it.polito.dp2.NFV.sol1.NfvInfo.file");
		
		try {
			JAXBContext jc= JAXBContext.newInstance("it.polito.dp2.NFV.sol1.jaxb");
			Unmarshaller u = jc.createUnmarshaller();
			
			this.nfv = (Nfv) u.unmarshal(new File(this.filePath));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ConnectionPerformanceReader getConnectionPerformance(HostReader arg0, HostReader arg1) {
		
		return null;
	}

	@Override
	public HostReader getHost(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<HostReader> getHosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NffgReader getNffg(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NffgReader> getNffgs(Calendar arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<VNFTypeReader> getVNFCatalog() {
		// TODO Auto-generated method stub
		return null;
	}

}
