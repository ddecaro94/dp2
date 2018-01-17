package it.polito.dp2.NFV.sol3.client1;

import java.net.URI;
import org.apache.commons.lang.math.RandomUtils;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import it.polito.dp2.NFV.sol3.client1.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client1.data.Node;

public class TestClient {


	public static void main(String[] args) {
		NfvClient client;
		try {
			
			Node node = new Node();
			NamedEntity host = new NamedEntity();
			host.setName("H3");
			node.setHost(host);
			
			NamedEntity vnf = new NamedEntity();
			vnf.setName("CACHEb");
			node.setVnf(vnf);
			node.setName("Node"+RandomUtils.nextInt());
			

	        WebResource nodesService = NfvDeployer.createClient().resource(URI.create("http://localhost:8080/NfvDeployer/rest/nffgs/Nffg0/nodes"));
		
	        ClientResponse n = nodesService.post(ClientResponse.class, node);
	        
	        System.out.println(n.getEntity(String.class));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		


	}

}
