package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class ConflictException extends ClientErrorException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConflictException() {
        super(Response.Status.CONFLICT); // 409
    }

	public ConflictException(Throwable cause) {
		super(Response.status(409).entity(cause.getMessage()).build(), cause);
	}

	public ConflictException(String message) {
		super(Response.status(409).entity(message).build());
	}
}
