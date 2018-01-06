package it.polito.dp2.NFV.sol1;

import javax.xml.bind.Unmarshaller;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol1.jaxb.ConnectionType;
import it.polito.dp2.NFV.sol1.jaxb.NffgType;
import it.polito.dp2.NFV.sol1.jaxb.NodeType;

public class LinkReader implements it.polito.dp2.NFV.LinkReader {

	private ConnectionType link;
	private NodeReader source;
	
	public LinkReader(ConnectionType l) {
		this.link = l;
	}

	public void afterUnmarshal(Unmarshaller u, Object parent) { 
	    this.source = (NodeReader) parent;
	 }
	
	@Override
	public String getName() {
		return this.link.getName();
	}

	@Override
	public NodeReader getDestinationNode() {
		return new it.polito.dp2.NFV.sol1.NodeReader((NodeType) link.getDestination());
	}

	@Override
	public int getLatency() {
		//In a link, presence of performance object is made sure from validation
		return this.link.getPerformance().getLatency().intValue();
	}

	@Override
	public NodeReader getSourceNode() {
		return source;
	}

	@Override
	public float getThroughput() {
		//In a link, presence of performance object is made sure from validation
		return this.link.getPerformance().getThroughput().intValue();
	}

}
