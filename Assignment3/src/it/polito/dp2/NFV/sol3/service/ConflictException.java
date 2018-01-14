package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ConflictException extends ClientErrorException{

    public ConflictException(int status, Throwable cause) {
		super(status, cause);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(int status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(Response response, Throwable cause) {
		super(response, cause);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(Response response) {
		super(response);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(Status status, Throwable cause) {
		super(status, cause);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(Status status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(String message, int status, Throwable cause) {
		super(message, status, cause);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(String message, int status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(String message, Response response, Throwable cause) {
		super(message, response, cause);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(String message, Response response) {
		super(message, response);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(String message, Status status, Throwable cause) {
		super(message, status, cause);
		// TODO Auto-generated constructor stub
	}

	public ConflictException(String message, Status status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4515860597298940341L;

	public ConflictException() {
        super(Response.Status.CONFLICT); // 409
    }
}
