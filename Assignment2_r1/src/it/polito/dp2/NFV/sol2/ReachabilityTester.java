package it.polito.dp2.NFV.sol2;

import java.util.Set;

import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.lab2.AlreadyLoadedException;
import it.polito.dp2.NFV.lab2.ExtendedNodeReader;
import it.polito.dp2.NFV.lab2.NoGraphException;
import it.polito.dp2.NFV.lab2.ServiceException;
import it.polito.dp2.NFV.lab2.UnknownNameException;

public class ReachabilityTester implements it.polito.dp2.NFV.lab2.ReachabilityTester {

	private NfvReader nfv;
	
	public ReachabilityTester() throws ReachabilityTesterException {
		try {
			this.nfv = NfvReaderFactory.newInstance().newNfvReader();
		} catch (NfvReaderException e) {
			throw new ReachabilityTesterException(e);
		} catch (FactoryConfigurationError e) {
			throw new ReachabilityTesterException(e.getMessage());
		}
	}

	@Override
	public void loadGraph(String nffgName) throws UnknownNameException, AlreadyLoadedException, ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<ExtendedNodeReader> getExtendedNodes(String nffgName)
			throws UnknownNameException, NoGraphException, ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLoaded(String nffgName) throws UnknownNameException {
		// TODO Auto-generated method stub
		
		return false;
	}

}
