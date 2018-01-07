package it.polito.dp2.NFV.sol1;

public class FactoryConfigurationError extends it.polito.dp2.NFV.FactoryConfigurationError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6484676206321677921L;

	public FactoryConfigurationError() {
		super();
	}

	public FactoryConfigurationError(Exception e) {
		super(e);
	}

	public FactoryConfigurationError(Exception e, String msg) {
		super(e, msg);
	}

	public FactoryConfigurationError(String msg) {
		super(msg);
	}

}
