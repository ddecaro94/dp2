package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ConflictException extends ClientErrorException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4515860597298940341L;

	public ConflictException() {
        super(Response.Status.CONFLICT); // 409
    }

	public ConflictException(Throwable cause) {
		super(Response.Status.CONFLICT, cause);
	}

	public ConflictException(Response response) {
		super(response);
	}

	public ConflictException(Response response, Throwable cause) {
		super(response, cause);
	}

	public ConflictException(String message) {
		super(message, Response.Status.CONFLICT);
	}
}
