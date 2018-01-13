package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class NotDefinedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6102207529213125591L;

	public NotDefinedException() {
		// TODO Auto-generated constructor stub
	}

	public NotDefinedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotDefinedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotDefinedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotDefinedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
