package it.polito.dp2.NFV.sol2;

public class ReachabilityTesterException extends it.polito.dp2.NFV.lab2.ReachabilityTesterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4875193686376398950L;

	public ReachabilityTesterException() {
	}

	public ReachabilityTesterException(String msg) {
		super(msg);
	}

	public ReachabilityTesterException(Exception e) {
		super(e);
	}

	public ReachabilityTesterException(Exception e, String msg) {
		super(e, msg);
	}

}
