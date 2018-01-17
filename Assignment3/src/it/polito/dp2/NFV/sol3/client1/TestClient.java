package it.polito.dp2.NFV.sol3.client1;

import java.util.HashMap;
import java.util.Set;

import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.lab3.LinkDescriptor;
import it.polito.dp2.NFV.lab3.NffgDescriptor;
import it.polito.dp2.NFV.lab3.NfvClientException;
import it.polito.dp2.NFV.lab3.NodeDescriptor;

public class TestClient {


	public static void main(String[] args) {
		NfvClient client;
		try {
	        System.setProperty("it.polito.dp2.NFV.NfvReaderFactory", "it.polito.dp2.NFV.sol3.client2.NfvReaderFactory");
	        NfvReader initialTestNfvReader = NfvReaderFactory.newInstance().newNfvReader();
		
		
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		


	}

}
