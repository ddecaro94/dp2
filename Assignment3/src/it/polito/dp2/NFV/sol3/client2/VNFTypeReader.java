package it.polito.dp2.NFV.sol3.client2;

import java.net.URI;

import it.polito.dp2.NFV.FunctionalType;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client2.data.Vnf;

public class VNFTypeReader implements it.polito.dp2.NFV.VNFTypeReader {

	private Vnf vnf;
	
	public VNFTypeReader(URI vnfUri) {
		vnf = NfvDeployer.createClient().resource(vnfUri).get(Vnf.class);
	}

	@Override
	public String getName() {
		return vnf.getName();
	}

	@Override
	public FunctionalType getFunctionalType() {
		return FunctionalType.fromValue(vnf.getFunctionalType().toString());
	}

	@Override
	public int getRequiredMemory() {
		return vnf.getRequiredMemory().intValue();
	}

	@Override
	public int getRequiredStorage() {
		return vnf.getRequiredStorage().intValue();
	}

}
