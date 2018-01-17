package it.polito.dp2.NFV.sol3.client2;

import java.net.URI;

import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.lab3.UnknownEntityException;

public class NfvReaderFactory extends it.polito.dp2.NFV.NfvReaderFactory {

	@Override
	public NfvReader newNfvReader() throws NfvReaderException {
		// TODO Auto-generated method stub
		try {
			return new it.polito.dp2.NFV.sol3.client2.NfvReader(URI.create(System.getProperty("it.polito.dp2.NFV.lab3.URL", "http://localhost:8080/NfvDeployer/rest/")));
		} catch (UnknownEntityException e) {
			e.printStackTrace();
			throw new NfvReaderException(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new NfvReaderException(e);
		}
	}

}
