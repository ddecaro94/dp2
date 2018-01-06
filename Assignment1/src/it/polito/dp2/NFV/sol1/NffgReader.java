package it.polito.dp2.NFV.sol1;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.stream.Collectors;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol1.jaxb.NffgType;

public class NffgReader implements it.polito.dp2.NFV.NffgReader {
	private NffgType nffg;
	public NffgReader(NffgType nffg) {
		this.nffg = nffg;
	}
	
	@Override
	public String getName() {
		return this.nffg.getName();
	}

	@Override
	public Calendar getDeployTime() {
		if (this.nffg.getDeployTime() != null) {
			Calendar minimumDate = Calendar.getInstance();
			minimumDate.setTime(new Date(Long.MIN_VALUE));
			return minimumDate;
		}
		return this.nffg.getDeployTime().toGregorianCalendar();
	}

	@Override
	public NodeReader getNode(String nodeName) {
		return this.nffg.getNodes().getNode().parallelStream().filter(t->t.getName().equals(nodeName)).map(t->new it.polito.dp2.NFV.sol1.NodeReader(t)).findFirst().orElse(null);
	}

	@Override
	public Set<NodeReader> getNodes() {
		return this.nffg.getNodes().getNode().parallelStream().map(t->new it.polito.dp2.NFV.sol1.NodeReader(t)).collect(Collectors.toSet());
	}

}
