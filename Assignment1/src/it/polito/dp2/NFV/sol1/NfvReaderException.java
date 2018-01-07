package it.polito.dp2.NFV.sol1;

public class NfvReaderException extends it.polito.dp2.NFV.NfvReaderException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2333621196151175989L;

	public NfvReaderException() {
		super();
	}

	public NfvReaderException(Exception e) {
		super(e);
	}

	public NfvReaderException(Exception e, String msg) {
		super(e, msg);
	}

	public NfvReaderException(String msg) {
		super(msg);
	}

}
