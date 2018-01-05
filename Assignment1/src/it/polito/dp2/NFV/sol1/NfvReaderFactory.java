package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.sol1.NfvReader;
import it.polito.dp2.NFV.sol1.NfvReaderException;

public class NfvReaderFactory extends it.polito.dp2.NFV.NfvReaderFactory {

	public NfvReaderFactory() {
	}
	
	
	@Override
	public NfvReader newNfvReader() throws NfvReaderException {
		return new NfvReader();
	}

}
