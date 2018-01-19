package it.polito.dp2.NFV.sol3.service;

public class AlreadyLoadedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyLoadedException() {
	}

	public AlreadyLoadedException(String message) {
		super(message);
	}

	public AlreadyLoadedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyLoadedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AlreadyLoadedException(Throwable cause) {
		super(cause);
	}

}
