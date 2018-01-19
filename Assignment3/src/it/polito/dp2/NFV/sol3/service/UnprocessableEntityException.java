package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class UnprocessableEntityException extends ClientErrorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnprocessableEntityException() {
		super(422);
	}

	public UnprocessableEntityException(Throwable cause) {
		super(Response.status(422).entity(cause.getMessage()).build(), cause);
	}

	public UnprocessableEntityException(String message) {
		super(Response.status(422).entity(message).build());
	}


}
