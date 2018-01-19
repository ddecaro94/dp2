package it.polito.dp2.NFV.sol3.service;

public class InvalidEntityException extends Exception {

	public InvalidEntityException() {
	}

	public InvalidEntityException(String message) {
		super(message);
	}

	public InvalidEntityException(Throwable cause) {
		super(cause);
	}

	public InvalidEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEntityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
