package it.polito.dp2.NFV.sol3.service;

public class InvalidEntityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEntityException() {
	}

	public InvalidEntityException(String message) {
		super(message);
	}

	public InvalidEntityException(Throwable cause) {
		super(cause);
	}


}
