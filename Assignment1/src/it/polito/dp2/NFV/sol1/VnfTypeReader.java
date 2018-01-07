package it.polito.dp2.NFV.sol1;

import it.polito.dp2.NFV.FunctionalType;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol1.jaxb.VnfType;

public class VnfTypeReader implements VNFTypeReader {

	private VnfType vnf;

	public VnfTypeReader(VnfType t) {
		this.vnf = t;
	}

	@Override
	public FunctionalType getFunctionalType() {
		return FunctionalType.fromValue(this.vnf.getFunctionalType().name());
	}

	@Override
	public String getName() {
		return this.vnf.getName();
	}

	@Override
	public int getRequiredMemory() {
		return this.vnf.getRequiredMemory().intValue();
	}

	@Override
	public int getRequiredStorage() {
		return this.vnf.getRequiredStorage().intValue();
	}

}
