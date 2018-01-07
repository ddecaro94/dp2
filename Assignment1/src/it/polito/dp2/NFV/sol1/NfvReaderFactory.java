package it.polito.dp2.NFV.sol1;

public class NfvReaderFactory extends it.polito.dp2.NFV.NfvReaderFactory {

	public NfvReaderFactory() {

	}

	@Override
	public NfvReader newNfvReader() throws NfvReaderException {
		return new NfvReader(System.getProperty("it.polito.dp2.NFV.sol1.NfvInfo.file"));
	}

}
