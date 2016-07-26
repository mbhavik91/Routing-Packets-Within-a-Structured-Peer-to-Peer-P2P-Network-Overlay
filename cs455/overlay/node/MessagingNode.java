/**
 * 
 */
package cs455.overlay.node;
//
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


import cs455.overlay.connection.ConnectionCache;
import cs455.overlay.transport.TCPReceiver;
import cs455.overlay.transport.TCPSender;
import cs455.overlay.util.InteractiveCommandPareser;
import cs455.overlay.util.ServerThread;
import cs455.overlay.wireformat.Event;
import cs455.overlay.wireformat.MessageType;
import cs455.overlay.wireformat.NodeReportsOverlaySetupStatus;
import cs455.overlay.wireformat.OverlayNodeReportsTaskFinished;
import cs455.overlay.wireformat.OverlayNodeReportsTrafficSummary;
import cs455.overlay.wireformat.OverlayNodeSendsData;
import cs455.overlay.wireformat.OverlayNodeSendsDeregistration;
import cs455.overlay.wireformat.OverlayNodeSendsRegistrationRequest;
import cs455.overlay.wireformat.RegistryReportsDeregistrationStatus;
import cs455.overlay.wireformat.RegistryReportsRegistrationStatus;
import cs455.overlay.wireformat.RegistryRequestTaskInitiate;
import cs455.overlay.wireformat.RegistryRequestsTrafficSummary;
import cs455.overlay.wireformat.RegistrySendsNodeManifest;

/**
 * @author mbhavik
 *
 */
public class MessagingNode implements Node {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	 */
	
	
	private int stat;
	public int currentNodeId;
	Socket socket;
	TCPSender sender;
	public int[] list;
	public HashMap<Integer, ConnectionCache> connection = new HashMap<Integer, ConnectionCache>();
	public String ip1; public String ip2; public String ip3;
	public int port1; public int port2; public int port3;
	public int uniqueID1;public int uniqueID2;public int uniqueID3;public int uniqueID4;
	public int serverPort;
	public int[] receivedList;
	public int[] x; 
	public int randomDestination;
	public List<Integer> routingID = new ArrayList<Integer>();
	long payLoad;
	long payLoadSendCounter = 0;
	long payLoadReceivedCounter = 0;
	long sendPacketCounter = 0;
	long receivedPacketCounter = 0;
	long relayedCounter = 0;
	public String ip;
	public int overlaySize;
	
	public ArrayList< Integer> l =  new ArrayList<Integer>(); 
	
	
	public MessagingNode(String serverIP, int port) throws IOException{
		socket = new Socket(serverIP,port);
		
		sender = new TCPSender(socket);
		
		ServerThread st = new ServerThread(this);
		st.start();
		
		InteractiveCommandPareser mnicp = new InteractiveCommandPareser(this);
		mnicp.start();
		/*
		System.out.println("Server port------------------------"+serverPort);*/
		ip = 	InetAddress.getLocalHost().getHostAddress();
		OverlayNodeSendsRegistrationRequest sendRegistrationRequest = new OverlayNodeSendsRegistrationRequest(InetAddress.getLocalHost().getHostAddress(), serverPort);
		//System.out.println(sendRegistrationRequest.getMyBytes());
		
		sender.sendData(sendRegistrationRequest.getMyBytes());
		TCPReceiver receiver = new TCPReceiver(socket, this);
		Thread t = new Thread(receiver);
		t.start();		
	}
	
	
	//Socket socket;
	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {
		// TODO Auto-generated method stub
			
		new MessagingNode(args[0], Integer.parseInt(args[1]));

	}
	
	
	@Override
	public void onEvent(Event e, Socket socket) throws IOException {
		// TODO Auto-generated method stub
			
		int type = e.getType();
		/*if(type == 9){
		System.out.println("Event Fired :"+type);}*/
		switch (type) {
		case MessageType.REGISTRY_REPORTS_REGISTRATION_STATUS: receivedRegistrationStatus((RegistryReportsRegistrationStatus) e, socket);
																break;
		case MessageType.REGISTRY_SENDS_NODE_MANIFEST_REQUEST: receivedNodeManifest((RegistrySendsNodeManifest) e, socket);	
			break;
		case MessageType.REGISTRY_REQUESTS_TASK_INITIATE: requestTask((RegistryRequestTaskInitiate)e,socket); break;
		
		case MessageType.OVERLAY_NODE_SENDS_DATA: receivedData((OverlayNodeSendsData)e,socket);
													
		break;
		case MessageType.REGISTRY_REQUESTS_TRAFFIC_SUMMARY: sendTrafficSummary((RegistryRequestsTrafficSummary)e,socket);break;
		
		case MessageType.REGISTRY_REPORTS_DEREGISTRATION_STATUS: receivedDeregistrationStatus((RegistryReportsDeregistrationStatus)e,socket);break;
		default:
			break;
		}
		
		
	}

	private void receivedDeregistrationStatus(
			RegistryReportsDeregistrationStatus e, Socket socket2) {
		// TODO Auto-generated method stub
		System.out.println(e.getReceivedDeregistrationMessage());
		
	}


	private void sendTrafficSummary(RegistryRequestsTrafficSummary e,
			Socket socket2) throws IOException {
		// TODO Auto-generated method stub
		OverlayNodeReportsTrafficSummary reportSummary = new OverlayNodeReportsTrafficSummary(currentNodeId,sendPacketCounter,relayedCounter,receivedPacketCounter,payLoadSendCounter,payLoadReceivedCounter);
		
		
		sender.sendData(reportSummary.getMyBytes());
	}


	private synchronized void receivedData(OverlayNodeSendsData e, Socket socket2) throws IOException {
		//List<Integer> temp2 = null;
		// TODO Auto-generated method stub
		//*********************************************************Old Logic***************************************************************************
		/*if(e.getReceivedDestID() == currentNodeId){
			synchronized (this) {
				receivedPacketCounter++;
				payLoadReceivedCounter+= e.getReceivedPayLoad();
			}
			
		}
		else if(connection.containsKey(e.getReceivedDestID())){

			ConnectionCache cm = connection.get(e.getReceivedDestID());
			OverlayNodeSendsData rm = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
			synchronized (this) {
				cm.getrTcpSender().sendData(rm.getMyBytes());
				relayedCounter++;
			}
		
		}
		else if(e.getReceivedDestID()>uniqueID1 && e.getReceivedDestID()<uniqueID2){
			ConnectionCache c1 = connection.get(uniqueID1);
			OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
			synchronized (this) {
				c1.getrTcpSender().sendData(r1.getMyBytes());
				relayedCounter++;
			}
		}
		
		else if(e.getReceivedDestID()>=uniqueID2 && e.getReceivedDestID()<uniqueID3){
			ConnectionCache c2 = connection.get(uniqueID2);
			OverlayNodeSendsData r2 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
			synchronized (this) {
				c2.getrTcpSender().sendData(r2.getMyBytes());
				relayedCounter++;
			}
		}
		else{
			ConnectionCache c3 = connection.get(uniqueID3);
			OverlayNodeSendsData r3 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
			synchronized (this) {
				c3.getrTcpSender().sendData(r3.getMyBytes());
				relayedCounter++;
			}
		}
*/		//*********************************************************Old***************************************************************************	
		/*
		System.out.println("");
		 System.out.println("********************Task Finished Check Counters*********************************");*/
		 //System.out.println("Messages sent were :"+e.getReceivedMess());
		
		//*********************************************************New***************************************************************************
		if(overlaySize == 3){
		if(e.getReceivedDestID() == currentNodeId){
			synchronized (this) {
				receivedPacketCounter++;
				payLoadReceivedCounter+= e.getReceivedPayLoad();
			}
		}
			else if(connection.containsKey(e.getReceivedDestID())){
				//System.out.println("Direcy Send hua--------------------------------------------------------------------------------------------------");
				ConnectionCache c = connection.get(e.getReceivedDestID());
				OverlayNodeSendsData r = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
				synchronized (this) {
					c.getrTcpSender().sendData(r.getMyBytes());
					//sendPacketCounter++;
					//payLoadSendCounter+=payLoad;
					relayedCounter++;
				}
			
			
		}else if(e.getReceivedDestID()>uniqueID1 && e.getReceivedDestID()<uniqueID2){
			
			if(e.getReceivedDestID()>=0&&e.getReceivedDestID()<uniqueID2){
				
				synchronized (this) {
					ConnectionCache c1 = connection.get(uniqueID1);
					OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
					c1.getrTcpSender().sendData(r1.getMyBytes());
					relayedCounter++;
				}
			}else if(e.getReceivedDestID()>uniqueID1&&e.getReceivedDestID()<=128){
				
				synchronized (this) {
					ConnectionCache c1 = connection.get(uniqueID1);
					OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
					c1.getrTcpSender().sendData(r1.getMyBytes());
					relayedCounter++;
				}
			}
			
		}else if(e.getReceivedDestID()>uniqueID2 && e.getReceivedDestID()<uniqueID3){
			
			if(e.getReceivedDestID()>=0&&e.getReceivedDestID()<uniqueID3){
				
				synchronized (this) {
					ConnectionCache c1 = connection.get(uniqueID2);
					OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
					c1.getrTcpSender().sendData(r1.getMyBytes());
					relayedCounter++;
				}
			}else if(e.getReceivedDestID()<=128 && e.getReceivedDestID()>uniqueID2){
				
				synchronized (this) {
					ConnectionCache c1 = connection.get(uniqueID2);
					OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
					c1.getrTcpSender().sendData(r1.getMyBytes());
					relayedCounter++;
				}
			}
		}else {
				
				synchronized (this) {
					ConnectionCache c1 = connection.get(uniqueID3);
					OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
					c1.getrTcpSender().sendData(r1.getMyBytes());
					//sendPacketCounter++;
					relayedCounter++;
					}
		}
		}else{
			

			if(e.getReceivedDestID() == currentNodeId){
				synchronized (this) {
					receivedPacketCounter++;
					payLoadReceivedCounter+= e.getReceivedPayLoad();
				}
			}else if(connection.containsKey(e.getReceivedDestID())){
					
					ConnectionCache c = connection.get(e.getReceivedDestID());
					OverlayNodeSendsData r = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
					synchronized (this) {
						c.getrTcpSender().sendData(r.getMyBytes());
						relayedCounter++;
					}
			}else if(e.getReceivedDestID()>uniqueID1 && e.getReceivedDestID()<uniqueID2){
				
				if(e.getReceivedDestID()>=0&&e.getReceivedDestID()<uniqueID2){
					
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID1);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
						c1.getrTcpSender().sendData(r1.getMyBytes());
						relayedCounter++;
					}
				}else if(e.getReceivedDestID()>uniqueID1&&e.getReceivedDestID()<=128){
					
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID1);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
						c1.getrTcpSender().sendData(r1.getMyBytes());
						relayedCounter++;
					}
				}
				
			}else if(e.getReceivedDestID()>uniqueID2 && e.getReceivedDestID()<uniqueID3){
				
				if(e.getReceivedDestID()>=0&&e.getReceivedDestID()<uniqueID3){
					
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID2);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
						c1.getrTcpSender().sendData(r1.getMyBytes());
						relayedCounter++;
					}
				}else if(e.getReceivedDestID()<=128 && e.getReceivedDestID()>uniqueID2){
					
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID2);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
						c1.getrTcpSender().sendData(r1.getMyBytes());
						relayedCounter++;
					}
				}
			}else if(e.getReceivedDestID()>uniqueID3 || e.getReceivedDestID()<uniqueID4){
				
				if(e.getReceivedDestID()>=0&&e.getReceivedDestID()<uniqueID4){
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID3);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
						c1.getrTcpSender().sendData(r1.getMyBytes());
						relayedCounter++;
					}
				}else if(e.getReceivedDestID()<=128&&e.getReceivedDestID()>uniqueID3){
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID3);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
						c1.getrTcpSender().sendData(r1.getMyBytes());
						relayedCounter++;
					}
				}
				}else {
					
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID4);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),e.getReceivedPayLoad());
						c1.getrTcpSender().sendData(r1.getMyBytes());
						//sendPacketCounter++;
						relayedCounter++;
						}
			}
			
			
			
		}
		}	
		/*
		if(e.getReceivedDestID() == currentNodeId){
			receivedPacketCounter++;
			payLoadReceivedCounter+= e.getReceivedPayLoad();
		}
		else if(connection.containsKey(e.getReceivedDestID())){

			ConnectionCache cm = connection.get(e.getReceivedDestID());
			OverlayNodeSendsData rm = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),payLoad);
			synchronized (this) {
				cm.getrTcpSender().sendData(rm.getMyBytes());
				relayedCounter++;
			}
		
		}
		else if (randomDestination>) {
			
		}*/
		/*if(e.getReceivedDestID() == currentNodeId){
			receivedPacketCounter++;
			payLoadReceivedCounter+= e.getReceivedPayLoad();
		}
		else if(connection.containsKey(e.getReceivedDestID())){

			ConnectionCache cm = connection.get(e.getReceivedDestID());
			OverlayNodeSendsData rm = new OverlayNodeSendsData(e.getReceivedDestID(),e.getReceivedSourceID(),payLoad);
			synchronized (this) {
				cm.getrTcpSender().sendData(rm.getMyBytes());
				relayedCounter++;
			}
		
		}
		
		else if(e.getReceivedDestID()>currentNodeId){
			for (int j = 0; j < routingID.size(); j++) {
				if(e.getReceivedDestID()<routingID.get(j)){
					temp2.add(routingID.get(j));
				}
			}
			ConnectionCache c1;
			if(temp2.size()!=0){
				int toSend = Collections.max(temp2);
				c1 = connection.get(toSend);
			}
			else {
				int send = Collections.max(routingID);
				c1 = connection.get(send);
			}
			
			OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),currentNodeId,payLoad);
			synchronized (this) {
				c1.getrTcpSender().sendData(r1.getMyBytes());
				relayedCounter++;
				}
		}else if (e.getReceivedDestID()<currentNodeId) {
			for (int j = 0; j < routingID.size(); j++) {
				if(e.getReceivedDestID()<routingID.get(j)){
					temp2.add(routingID.get(j));
				}
				ConnectionCache c1;
				if(temp2.size()!=0){
					int toSend = Collections.max(temp2);
					c1 = connection.get(toSend);
				}
				else {
					int send = Collections.max(routingID);
					c1 = connection.get(send);
				}
				
				OverlayNodeSendsData r1 = new OverlayNodeSendsData(e.getReceivedDestID(),currentNodeId,payLoad);
				synchronized (this) {
					c1.getrTcpSender().sendData(r1.getMyBytes());
					relayedCounter++;
					}
			}
			
		}
*/		
		
		//*********************************************************Naya***************************************************************************
//	}

	


	private void requestTask(RegistryRequestTaskInitiate e, Socket socket2) throws IOException {
		List<Integer> temp = null;
		// TODO Auto-generated method stub
		//System.out.println("Firing is proper ----------");
		//System.out.println("Started "+e.getReceivedMess());
		//System.out.println("current node is "+currentNodeId);
		//System.out.println(" Now I will start sending messages to : ");
		//System.out.println("IP1 :"+ip1+" IP2 :"+ip2+" IP3 :"+ip3+" Port1 :"+port1+" Port2 :"+port2+" Port3 :"+port3+ " U1 :"+uniqueID1+ " U2 :"+uniqueID2+ " U3 :"+uniqueID3);
		//System.out.println("received list length" + receivedList.length);
				
		for (int i = 0; i < receivedList.length; i++) {
			
			l.add(receivedList[i]);
		}
		if(l.contains(currentNodeId) == true){
			
			l.remove((Integer)currentNodeId);
		}
		/*System.out.println("Nodes present at this moment");
		for (int i = 0; i < l.size(); i++) {
			System.out.println(l.get(i));
		}*/
		
		/********Select the a node automatically******/
		 
		
		
		 /*for (int i = 0; i < e.getReceivedMess(); i++) {
			 System.out.println("message value " +e.getReceivedMess());
			payLoad = randomGenerator();
			randomDestination =  randomItem();
			System.out.println("Random Destination selected "+randomDestination);
			
			if(connection.containsKey(randomDestination)){
				ConnectionCache c = connection.get(randomDestination);
				OverlayNodeSendsData r = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
				synchronized (this) {
					c.getrTcpSender().sendData(r.getMyBytes());
					sendPacketCounter++;
					payLoadSendCounter+=payLoad;
				}
			}else if(randomDestination>uniqueID1 && randomDestination<uniqueID2){
				ConnectionCache c1 = connection.get(uniqueID1);
				OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
				synchronized (this) {
					c1.getrTcpSender().sendData(r1.getMyBytes());
					sendPacketCounter++;
					payLoadSendCounter+=payLoad;
				}
			}else if(randomDestination>uniqueID2 && randomDestination<uniqueID3){
				ConnectionCache c1 = connection.get(uniqueID2);
				OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
				synchronized (this) {
					c1.getrTcpSender().sendData(r1.getMyBytes());
					sendPacketCounter++;
					payLoadSendCounter+=payLoad;
				}
			}else{
				ConnectionCache c1 = connection.get(uniqueID3);
				OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
				synchronized (this) {
					c1.getrTcpSender().sendData(r1.getMyBytes());
					sendPacketCounter++;
					payLoadSendCounter+=payLoad;
				}
			}
		}	*/
		 
	
	//System.out.println("Ip-----------------------------"+ip);
	
	
//*********************************************************************************************************************************
		 //*********************Start of Overlay Node Reports Task Finished*****************
		 
		/* OverlayNodeReportsTaskFinished taskFinished = new OverlayNodeReportsTaskFinished(1);
		 sender.sendData(taskFinished.getMyBytes());*/
		//*********************End of Overlay Node Reports Task Finished*****************
		//*********************NEW COde********************************************************************************************
		
		if(overlaySize==3){
		
		 for (int i = 0; i < e.getReceivedMess(); i++) {
			//System.out.println("message value " +e.getReceivedMess());
			payLoad = randomGenerator();
			randomDestination =  randomItem();
			//System.out.println("Random Destination selected "+randomDestination);
			
			if(connection.containsKey(randomDestination)){
				
				ConnectionCache c = connection.get(randomDestination);
				OverlayNodeSendsData r = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
				synchronized (this) {
					c.getrTcpSender().sendData(r.getMyBytes());
					sendPacketCounter++;
					payLoadSendCounter+=payLoad;
				}
			}else if(randomDestination>uniqueID1 || randomDestination<uniqueID2){
				
				if(randomDestination>=0&&randomDestination<uniqueID2){
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID1);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
						c1.getrTcpSender().sendData(r1.getMyBytes());
						sendPacketCounter++;
						payLoadSendCounter+=payLoad;
					}
				}else if(randomDestination>uniqueID1&&randomDestination<=128){
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID1);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
						c1.getrTcpSender().sendData(r1.getMyBytes());
						sendPacketCounter++;
						payLoadSendCounter+=payLoad;
					}
				}	
			}//else if (randomDestination<uniqueID1||randomDestination>uniqueID2) }
				
			else if(randomDestination>uniqueID2 || randomDestination<uniqueID3){
				
				if(randomDestination>=0&&randomDestination<uniqueID3){
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID2);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
						c1.getrTcpSender().sendData(r1.getMyBytes());
						sendPacketCounter++;
						payLoadSendCounter+=payLoad;
					}
				}else if(randomDestination<=128&&randomDestination>uniqueID2){
					synchronized (this) {
						ConnectionCache c1 = connection.get(uniqueID2);
						OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
						c1.getrTcpSender().sendData(r1.getMyBytes());
						sendPacketCounter++;
						payLoadSendCounter+=payLoad;
					}
				}
				
				
				
			}else{
				ConnectionCache c1 = connection.get(uniqueID3);
				OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
				synchronized (this) {
					c1.getrTcpSender().sendData(r1.getMyBytes());
					sendPacketCounter++;
					payLoadSendCounter+=payLoad;
				}
				
			}
		 
		 
		}
		}
		else {
			for (int i = 0; i < e.getReceivedMess(); i++) {
				//System.out.println("message value " +e.getReceivedMess());
				payLoad = randomGenerator();
				randomDestination =  randomItem();
				//System.out.println("Random Destination selected "+randomDestination);
				
				if(connection.containsKey(randomDestination)){
					
					ConnectionCache c = connection.get(randomDestination);
					OverlayNodeSendsData r = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
					synchronized (this) {
						c.getrTcpSender().sendData(r.getMyBytes());
						sendPacketCounter++;
						payLoadSendCounter+=payLoad;
					}
				}else if(randomDestination>uniqueID1 || randomDestination<uniqueID2){
					
					if(randomDestination>=0&&randomDestination<uniqueID2){
						synchronized (this) {
							ConnectionCache c1 = connection.get(uniqueID1);
							OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
							c1.getrTcpSender().sendData(r1.getMyBytes());
							sendPacketCounter++;
							payLoadSendCounter+=payLoad;
						}
					}else if(randomDestination>uniqueID1&&randomDestination<=128){
						synchronized (this) {
							ConnectionCache c1 = connection.get(uniqueID1);
							OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
							c1.getrTcpSender().sendData(r1.getMyBytes());
							sendPacketCounter++;
							payLoadSendCounter+=payLoad;
						}
					}	
				}//else if (randomDestination<uniqueID1||randomDestination>uniqueID2) {System.out.println("------------------------------------------------------------------------------------------------------------------------");}
					
				else if(randomDestination>uniqueID2 || randomDestination<uniqueID3){
					
					if(randomDestination>=0&&randomDestination<uniqueID3){
						synchronized (this) {
							ConnectionCache c1 = connection.get(uniqueID2);
							OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
							c1.getrTcpSender().sendData(r1.getMyBytes());
							sendPacketCounter++;
							payLoadSendCounter+=payLoad;
						}
					}else if(randomDestination<=128&&randomDestination>uniqueID2){
						synchronized (this) {
							ConnectionCache c1 = connection.get(uniqueID2);
							OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
							c1.getrTcpSender().sendData(r1.getMyBytes());
							sendPacketCounter++;
							payLoadSendCounter+=payLoad;
						}
					}
					
					
					
				}else if(randomDestination>uniqueID3 || randomDestination<uniqueID4){
					
					if(randomDestination>=0&&randomDestination<uniqueID4){
						synchronized (this) {
							ConnectionCache c1 = connection.get(uniqueID3);
							OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
							c1.getrTcpSender().sendData(r1.getMyBytes());
							sendPacketCounter++;
							payLoadSendCounter+=payLoad;
						}
					}else if(randomDestination<=128&&randomDestination>uniqueID3){
						synchronized (this) {
							ConnectionCache c1 = connection.get(uniqueID3);
							OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
							c1.getrTcpSender().sendData(r1.getMyBytes());
							sendPacketCounter++;
							payLoadSendCounter+=payLoad;
						}
					}
					
					
					
				}
				else{
					ConnectionCache c1 = connection.get(uniqueID4);
					OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
					synchronized (this) {
						c1.getrTcpSender().sendData(r1.getMyBytes());
						sendPacketCounter++;
						payLoadSendCounter+=payLoad;
					}
					
				}
			 
			 
			}
		}
		 OverlayNodeReportsTaskFinished reportTaskFinished = new OverlayNodeReportsTaskFinished(1,ip , serverPort, currentNodeId);
	     sender.sendData(reportTaskFinished.getMyBytes());
	}
		//*********************NEW KACHARA********************************************************************************************
		//hat
		
		/*for (int i = 0; i <e.getReceivedMess(); i++) {
			payLoad = randomGenerator();
			randomDestination =  randomItem();
			System.out.println("Random Destination selected "+randomDestination);
			if(connection.containsKey(randomDestination)){
				ConnectionCache c = connection.get(randomDestination);
				OverlayNodeSendsData r = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
				synchronized (this) {
					c.getrTcpSender().sendData(r.getMyBytes());
					sendPacketCounter++;
					payLoadSendCounter+=payLoad;
				}
			}
			if(randomDestination>currentNodeId){
				for (int j = 0; j < routingID.size(); j++) {
					if(randomDestination<routingID.get(j)){
						temp.add(routingID.get(j));
					}
				}
				ConnectionCache c1;
				if(temp.size()!=0){
					int toSend = Collections.max(temp);
					c1 = connection.get(toSend);
				}
				else {
					int send = Collections.max(routingID);
					c1 = connection.get(send);
				}
				
				OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
				synchronized (this) {
					
					c1.getrTcpSender().sendData(r1.getMyBytes());
					sendPacketCounter++;
					payLoadSendCounter+=payLoad;
					}
			}else if (randomDestination<currentNodeId) {
				for (int j = 0; j < routingID.size(); j++) {
					if(randomDestination<routingID.get(j)){
						temp.add(routingID.get(j));
					}
					ConnectionCache c1;
					if(temp.size()!=0){
						int toSend = Collections.max(temp);
						c1 = connection.get(toSend);
					}
					else {
						int send = Collections.max(routingID);
						c1 = connection.get(send);
					}
					
					OverlayNodeSendsData r1 = new OverlayNodeSendsData(randomDestination,currentNodeId,payLoad);
					synchronized (this) {
						c1.getrTcpSender().sendData(r1.getMyBytes());
						sendPacketCounter++;
						payLoadSendCounter+=payLoad;
						}
				}
				
			}	
		}*/
	
	



	private int randomGenerator() {
		// TODO Auto-generated method stub
		Random r1 = new Random();
		int random = r1.nextInt();
		return random;
	}


	private int randomItem() {
		// TODO Auto-generated method stub
		Random r = new Random();
		int index = r.nextInt(l.size());
		return l.get(index);
	}


	public void receivedNodeManifest(RegistrySendsNodeManifest e, Socket socket5) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		ip1 = e.rip1; ip2 = e.rip2; ip3 = e.rip3;
		port1 = e.rport1; port2 = e.rport2; port3 = e.rport3;
		uniqueID1 = e.uniqueID1; uniqueID2 = e.uniqueID2; uniqueID3 = e.uniqueID3;uniqueID4 = e.uniqueID4;
		overlaySize=e.receivedSize;
		
		receivedList = e.rece;
		/*System.out.println("Siz"+overlaySize);
		System.out.println("Siz"+e.rip4);
		System.out.println("Siz"+e.rport4);
		System.out.println("Siz"+e.uniqueID4);*/
		
		for (int i = 0; i < e.rece.length; i++) {
			receivedList[i]=e.rece[i];
			//l.add(e.rece[i]);
		}
				if(overlaySize == 3){	
			//***********Establish Connection and Send the status message*****************
				Socket s1 = new Socket(e.rip1, e.rport1);
				ConnectionCache cc = new ConnectionCache(s1,this);
				connection.put(e.uniqueID1, cc);
				routingID.add(uniqueID1);
				Socket s2 = new Socket(e.rip2, e.rport2);
				ConnectionCache cc2 = new ConnectionCache(s2,this);
				connection.put(e.uniqueID2, cc2);
				routingID.add(uniqueID2);
				Socket s3 = new Socket(e.rip3, e.rport3);
				ConnectionCache c3 = new ConnectionCache(s3,this);
				connection.put(e.uniqueID3, c3);
				routingID.add(uniqueID3);
				stat = 1;
				}
			else{
				Socket s1 = new Socket(e.rip1, e.rport1);
				ConnectionCache cc = new ConnectionCache(s1,this);
				connection.put(e.uniqueID1, cc);
				routingID.add(uniqueID1);
				Socket s2 = new Socket(e.rip2, e.rport2);
				ConnectionCache cc2 = new ConnectionCache(s2,this);
				connection.put(e.uniqueID2, cc2);
				routingID.add(uniqueID2);
				Socket s3 = new Socket(e.rip3, e.rport3);
				ConnectionCache c3 = new ConnectionCache(s3,this);
				connection.put(e.uniqueID3, c3);
				routingID.add(uniqueID3);
				Socket s4 = new Socket(e.rip4, e.rport4);
				ConnectionCache c4 = new ConnectionCache(s4,this);
				connection.put(e.uniqueID4, c4);
				routingID.add(uniqueID4);
				stat = 1;
			}
		//****************End of establish connection*************
		//System.out.println("connections stored " +connection.size());
		NodeReportsOverlaySetupStatus x = new NodeReportsOverlaySetupStatus(stat);
		//.sendData(x.getMyBytes());
		sender.sendData(x.getMyBytes());
	}

	private void receivedRegistrationStatus(
			RegistryReportsRegistrationStatus e, Socket socket2) {
		// TODO Auto-generated method stub
		currentNodeId = e.ruID;
		//System.out.println("MT "+e.getType());
		//System.out.println(e.receivedMessage+" "+e.ruID);
		System.out.println(e.receivedMessage+" "+currentNodeId);
		
	}

	public void display() {
		// TODO Auto-generated method stub
		System.out.println("Send Packet Counter "+ sendPacketCounter);
		System.out.println("Received Packet Counter "+ receivedPacketCounter);
		System.out.println("Relayed Counter "+ relayedCounter);
		System.out.println("Sent Payload Counter "+ payLoadSendCounter);
		System.out.println("Received PayLoad Counter "+ payLoadReceivedCounter);
		
	}

	@Override
	public void setServerPort(int localPort) {
		// TODO Auto-generated method stub
		this.serverPort =localPort;
	}


	public void exitOverlay() throws IOException {
		// TODO Auto-generated method stub
		OverlayNodeSendsDeregistration sendDeregistration = new OverlayNodeSendsDeregistration(ip, serverPort,currentNodeId);
		sender.sendData(sendDeregistration.getMyBytes());
		
	}

}
