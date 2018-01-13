package it.polito.dp2.NFV.sol3.service;

import javax.ws.rs.core.Application;

public class NfvDeployerApp extends Application {
	private NfvDeployer deployer = NfvDeployer.getInstance();
}
