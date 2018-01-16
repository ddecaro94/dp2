package it.polito.dp2.NFV.sol3.client1;

import java.net.URI;

import com.sun.jersey.api.client.WebResource;

import it.polito.dp2.NFV.FunctionalType;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client1.data.Vnf;

public class VNFTypeReader implements it.polito.dp2.NFV.VNFTypeReader {
	private String vnfName;
	private WebResource vnfService;

	public VNFTypeReader(URI vnfUri) {
		this.vnfService = NfvDeployer.createClient().resource(vnfUri);
	}

	@Override
	public String getName() {
		return vnfName;
	}

	@Override
	public FunctionalType getFunctionalType() {
		return FunctionalType.valueOf(vnfService.get(Vnf.class).getFunctionalType().toString());
	}

	@Override
	public int getRequiredMemory() {
		return vnfService.get(Vnf.class).getRequiredMemory().intValue();
	}

	@Override
	public int getRequiredStorage() {
		return vnfService.get(Vnf.class).getRequiredStorage().intValue();
	}

}
