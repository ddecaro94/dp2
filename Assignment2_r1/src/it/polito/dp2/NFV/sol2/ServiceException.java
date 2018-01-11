package it.polito.dp2.NFV.sol2;

public class ServiceException extends it.polito.dp2.NFV.lab2.ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1145741989073515872L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
