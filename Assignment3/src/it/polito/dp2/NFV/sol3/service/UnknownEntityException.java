package it.polito.dp2.NFV.sol3.service;

public class UnknownEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownEntityException() {
	}

	public UnknownEntityException(String message) {
		super(message);
	}

	public UnknownEntityException(Throwable cause) {
		super(cause);
	}

	public UnknownEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownEntityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
