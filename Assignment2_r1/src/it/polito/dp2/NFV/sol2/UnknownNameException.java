package it.polito.dp2.NFV.sol2;

public class UnknownNameException extends it.polito.dp2.NFV.lab2.UnknownNameException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5214127097688082008L;

	public UnknownNameException() {
	}

	public UnknownNameException(String message) {
		super(message);
	}

	public UnknownNameException(Throwable cause) {
		super(cause);
	}

	public UnknownNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownNameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
