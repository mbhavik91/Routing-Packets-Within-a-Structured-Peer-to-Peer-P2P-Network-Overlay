/**
 * 
 */
package cs455.overlay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.HashMap;

import cs455.overlay.node.MessagingNode;
import cs455.overlay.node.Registry;
import cs455.overlay.routing.RoutingTable;


/**
 * @author mbhavik
 * @throws IOException 
 */
public class InteractiveCommandPareser extends Thread{
	
	
	Registry r;
	MessagingNode mn;
	
	String input;
	String check = "setup-overlay";
	int overlaysize;
	RoutingTable rt = null;
	int startSize;
	public InteractiveCommandPareser(Registry r) {
		// TODO Auto-generated constructor stub
		this.r = r;
	}
	public InteractiveCommandPareser(MessagingNode mn) {
		// TODO Auto-generated constructor stub
		this.mn = mn;
	}
	@Override
	public void run() {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("You Menu is......");
		while(true){
			try {
				input =  br.readLine();
				//System.out.println(input);
			} catch (IOException e) {
				e.printStackTrace();
		}
		if(input.split("\\s+")[0].equalsIgnoreCase("setup-overlay")){
			overlaysize = Integer.parseInt(input.split("\\s+")[1]);
			input = "setup-overlay";
		}
		if(input.split("\\s+")[0].equalsIgnoreCase("start")){
			startSize = Integer.parseInt(input.split("\\s+")[1]);
			input = "start";
		}
		
		switch (input) {
		
			case "list-messaging-nodes": r.displayListMessagingNodes();break;
			case "print-counters-and-diagnostics": mn.display();break;
			case "setup-overlay": 	rt = new RoutingTable();
				try {
					if(overlaysize == 3){
						rt.createRoutingInfo(r.original, r.connectionStored, overlaysize);
					}
					else if (overlaysize == 4) {
						rt.createRoutingInfoFor4(r.original, r.connectionStored, overlaysize);
					}
				
				} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}break;
			case "exit-overlay": try {
				mn.exitOverlay();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
								break;
			case "start":try {
				r.taskInitiate(startSize);
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
				break;
			case "list-routing-tables": rt.displayRoutingInfo();break;

		default: System.out.println("Entered wrong value... Please enter the command properly");
			break;
		}
		}
	}
}
