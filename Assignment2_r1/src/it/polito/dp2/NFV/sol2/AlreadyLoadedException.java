package it.polito.dp2.NFV.sol2;

public class AlreadyLoadedException extends it.polito.dp2.NFV.lab2.AlreadyLoadedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1637377977270622135L;

	public AlreadyLoadedException() {
	}

	public AlreadyLoadedException(String message) {
		super(message);
	}

	public AlreadyLoadedException(Throwable cause) {
		super(cause);
	}

	public AlreadyLoadedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyLoadedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
