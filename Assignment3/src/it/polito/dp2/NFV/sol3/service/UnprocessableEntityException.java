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
		// TODO Auto-generated constructor stub
	}

	public UnprocessableEntityException(Response response) {
		super(response);
		// TODO Auto-generated constructor stub
	}

	public UnprocessableEntityException(String message, Response response) {
		super(message, response);
		// TODO Auto-generated constructor stub
	}

	public UnprocessableEntityException(Throwable cause) {
		super(422, cause);
		// TODO Auto-generated constructor stub
	}

	public UnprocessableEntityException(String message) {
		super(message, 422);
	}


}
