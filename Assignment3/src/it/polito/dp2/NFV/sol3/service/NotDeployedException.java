package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class NotDeployedException extends ClientErrorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6102207529213125591L;

	public NotDeployedException(Status status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(int status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(Response response) {
		super(response);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(String message, Status status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(String message, int status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(String message, Response response) {
		super(message, response);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(Status status, Throwable cause) {
		super(status, cause);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(int status, Throwable cause) {
		super(status, cause);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(Response response, Throwable cause) {
		super(response, cause);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(String message, Status status, Throwable cause) {
		super(message, status, cause);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(String message, int status, Throwable cause) {
		super(message, status, cause);
		// TODO Auto-generated constructor stub
	}

	public NotDeployedException(String message, Response response, Throwable cause) {
		super(message, response, cause);
		// TODO Auto-generated constructor stub
	}

}
