package client;

import org.apache.tuscany.sca.data.collection.Collection;
import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import service.TBService;


@Service(TBClient.class)
public class TBClientImpl implements TBClient {
	

	private TBService tbService;
	
	
	
	public TBService getTbService() {
		return tbService;
	}
	
	@Reference
	public void setTbService(TBService tbService) {
		this.tbService = tbService;
	}

	public void execute(String name){
		System.out.println("ClientSide:"+tbService);
		tbService.display(name+": invoked by client");
	}
	
	 public static void main(String[] args) throws Exception {

	        SCANodeFactory factory = SCANodeFactory.newInstance();
	        SCANode node = factory.createSCANodeFromClassLoader("client.composite", TBClient.class.getClassLoader());
	        node.start();
	        //((SCAClient)node).getServiceReference(arg0, arg1)
	       // TBService tbService = ((SCAClient)node).getService(TBService.class, "TBClientComponent");

	     //   System.out.println("Account summary: " + tbService.display("I am client") );

	        node.stop();
	    }  
}
