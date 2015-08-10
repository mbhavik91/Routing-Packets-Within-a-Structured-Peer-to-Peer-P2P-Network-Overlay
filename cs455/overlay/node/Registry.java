/**
 * 
 */
package cs455.overlay.node;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.omg.PortableServer.THREAD_POLICY_ID;

import cs455.overlay.connection.Connection;
import cs455.overlay.routing.RoutingTable;
import cs455.overlay.transport.TCPReceiver;
import cs455.overlay.transport.TCPSender;
import cs455.overlay.util.InteractiveCommandPareser;
import cs455.overlay.wireformat.Event;
import cs455.overlay.wireformat.MessageType;
import cs455.overlay.wireformat.NodeReportsOverlaySetupStatus;
import cs455.overlay.wireformat.OverlayNodeReportsTaskFinished;
import cs455.overlay.wireformat.OverlayNodeReportsTrafficSummary;
import cs455.overlay.wireformat.OverlayNodeSendsDeregistration;
import cs455.overlay.wireformat.OverlayNodeSendsRegistrationRequest;
import cs455.overlay.wireformat.RegistryReportsDeregistrationStatus;
import cs455.overlay.wireformat.RegistryReportsRegistrationStatus;
import cs455.overlay.wireformat.RegistryRequestTaskInitiate;
import cs455.overlay.wireformat.RegistryRequestsTrafficSummary;

/**
 * @author mbhavik
 *
 */
public class Registry implements Node{

	public HashMap<Integer, Connection> getConnectionStored() {
		return connectionStored;
	}

	public void setConnectionStored(HashMap<Integer, Connection> connectionStored) {
		this.connectionStored = connectionStored;
	}

	/**
	 * @param args
	 *
	 */
	Node node;
	public int registryPort;
	Socket socket;
	ServerSocket serverSocket;
	TCPSender sender;
	private int removalStatus=0;
	public ArrayList<String> ipNodes = new ArrayList<String>();
	public HashMap<Integer, String> uIDipNodes = new HashMap<Integer, String>();
	public ArrayList<Integer> uIDTracker = new ArrayList<Integer>();
	public HashMap<Integer, Socket> idSocketTracker = new HashMap<Integer, Socket>();
	public int uniqueId;
	public int status;
	public HashMap<Integer, String> original = new HashMap<Integer, String>();
	public HashMap<Integer, Connection> connectionStored = new HashMap<Integer, Connection>();
	public int messageSize;
	public ArrayList< Integer> l = new ArrayList<Integer>();
	public int nodeTrackerCounter;
	public ArrayList<String> receivedTaskComplete = new ArrayList<String>();
	public int receivedCounter = 0;
	public ArrayList<String> summary = new ArrayList<String>();
	public long sendPack;
	public long recePack;
	public long relay;
	public long payloadsend;
	public long payloadreceived;
	public int syncCounter;
	public ArrayList<Integer> getConn= new ArrayList<Integer>();
	public Registry(int port) throws IOException{
		
		System.out.println("Registry is up and running......");
		System.out.println("Registry IP is :"+InetAddress.getLocalHost().getHostAddress());
		InteractiveCommandPareser icp = new InteractiveCommandPareser(this);
		icp.start();
		this.registryPort = port;
		//startRegistryThread();
		ServerSocket serverSocket = new ServerSocket(registryPort);
		while(true){
		Socket socket = serverSocket.accept();
		//System.out.println("Registyr accept"+socket.getLocalPort());
		TCPReceiver receiver = new TCPReceiver(socket, this);
		Thread t = new Thread(receiver);
		sender = new TCPSender(socket);
		t.start();
		}
	}
	/*
	private void startRegistryThread() {
		// TODO Auto-generated method stub
		
		new ResgistryThread(registryPort, node).start();
	}*/

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		Registry r = new Registry(Integer.parseInt(args[0]));
		
	}

	@Override
	public void onEvent(Event e, Socket socket) throws IOException {
		
		//ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mt);
		//DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		//int x = dataInputStream.readByte();
		int type = e.getType();
		switch (type) {
		case MessageType.OVERLAY_NODE_SENDS_REGISTRATION_REQUEST: sendRegistrationStatus((OverlayNodeSendsRegistrationRequest) e, socket);
			break;
			
		case MessageType.NODE_REPORTS_OVERLAY_SETUP_STATUS: receivedSetupStatus((NodeReportsOverlaySetupStatus) e, socket);break;
		
		case MessageType.OVERLAY_NODE_REPORTS_TASK_FINISHED: receivedTaskFinished((OverlayNodeReportsTaskFinished)e,socket);break;
		
		case MessageType.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY: receivedSummary((OverlayNodeReportsTrafficSummary)e,socket);break;
		
		case MessageType.OVERLAY_NODE_SENDS_DEREGISTRATION_REQUEST: receivedDeregistrationRequest((OverlayNodeSendsDeregistration)e,socket);break;
		default:
			break;
		}
				
	}
	private void receivedDeregistrationRequest(
			OverlayNodeSendsDeregistration e, Socket socket2) throws IOException {
		// TODO Auto-generated method stub
		
		if(uIDipNodes.containsKey(e.messagingNodeId)){
			original.remove(e.messagingNodeId);
			//connectionStored.remove(e.messagingNodeId);
			uIDipNodes.remove(e.messagingNodeId);
			for (int i = 0; i < ipNodes.size(); i++) {
				if(ipNodes.contains(e.getMessagingNodeId()+"-"+e.getMessagingNodeIp()+":"+e.getMessagingNodeport()))
					//System.out.println("Remove karne ki try kar raha huuu.....");
					ipNodes.remove(i);
			}
			removalStatus = 1;
		}
		RegistryReportsDeregistrationStatus dstat = new RegistryReportsDeregistrationStatus(removalStatus);
		new TCPSender(socket2).sendData(dstat.getMyBytes());
	}

	private void receivedSummary(OverlayNodeReportsTrafficSummary e,
			Socket socket2) {
		// TODO Auto-generated method stub
		synchronized (this) {

			summary.add(e.getRececurrentNodeId()+"--->"+e.getRecesendPacketCounter()+"\t"+e.getRecereceivedPacketCounter()+ "\t" +e.getRecerelayedCounter()+"\t"+e.getRecepayLoadSendCounter()+"\t"+e.getRecepayLoadReceivedCounter());
			
			sendPack += e.getRecesendPacketCounter();
			recePack += e.getRecereceivedPacketCounter();
			relay += e.getRecerelayedCounter();
			payloadsend += e.getRecepayLoadSendCounter();
			payloadreceived += e.getRecepayLoadReceivedCounter();
			syncCounter++;	
		}
		//System.out.println("Summary Received "+syncCounter);
		if(syncCounter==nodeTrackerCounter){
			displaySummary();
		}
		
		
	}

	private void displaySummary() {
		// TODO Auto-generated method stub
		
		System.out.println("Node ID \t Packets Sent \t PacketsReceived \t Packets Relayed\t Payload Send \t Payload Received ");
		for (int i = 0; i < summary.size(); i++) {
			System.out.println(summary.get(i));
		}
		System.out.println("TOTAL"+"\t"+sendPack+"\t"+recePack+"\t"+relay+"\t"+payloadsend+"\t"+payloadreceived);
	}

	private void receivedTaskFinished(OverlayNodeReportsTaskFinished e,
			Socket socket2) throws IOException  {
		// TODO Auto-generated method stub
		
		synchronized (this) {
			receivedTaskComplete.add(e.getReceClientIp()+" "+e.getRport()+" "+e.getRuniqueID());
			receivedCounter+=e.getReceivedI();
			getConn.add(e.getRuniqueID());
		}
		
		
		//System.out.println("Reported Back ---------------------------------------------------------------"+receivedCounter);
		
		if(receivedCounter == nodeTrackerCounter){
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		for (int i = 0; i < getConn.size(); i++) {
			int k = getConn.get(i);
			Connection conn = connectionStored.get(k);
			RegistryRequestsTrafficSummary request = new RegistryRequestsTrafficSummary(); 
			conn.getSender().sendData(request.getMyBytes());
			//sender.sendData(request.getMyBytes());
		}	
	}
		
	}

	private void receivedSetupStatus(NodeReportsOverlaySetupStatus e, Socket socket2) {
		// TODO Auto-generated method stub
		//System.out.println("Setup "+ e.receivedMessage);
		
	}

	private void sendRegistrationStatus(OverlayNodeSendsRegistrationRequest e, Socket socket2) throws IOException {
		
		uniqueId = generateUniqueId();
		String value = e.receivedClientIp+":"+e.rport;
		uIDipNodes.put(uniqueId, value);
		ipNodes.add(uniqueId+"-"+e.receivedClientIp+":"+e.rport);
		original.put(uniqueId, value);
		l.add(uniqueId);
		nodeTrackerCounter++;
		status = 1;
		Connection c = new Connection(socket2);
		connectionStored.put(uniqueId, c);
		
		RegistryReportsRegistrationStatus s = new RegistryReportsRegistrationStatus(status, uniqueId);		
		new TCPSender(socket2).sendData(s.getMyBytes());
		setConnectionStored(connectionStored);
		
	}

	private int generateUniqueId() {
		// TODO Auto-generated method stub
		int a;
		Random random = new Random();
		a = random.nextInt(128);
		if(uIDTracker.size()==0){
			uIDTracker.add(a);
		}
		else {
			/*while(uIDTracker.contains(a) == true){
				a = random.nextInt(128);
			}*/
			if(uIDTracker.contains(a)){
				
				while(uIDTracker.contains(a)){
					a = random.nextInt(128);
						
				}
				//uIDTracker.add(a);
			}
			uIDTracker.add(a);
		}
		return a;		
	}

	

	public void displayListMessagingNodes() {
		
		System.out.println("Nodes preset in the system are....");
		for(int i=0; i<ipNodes.size();i++){
			System.out.println("Unique ID is :"+ipNodes.get(i).split("-")[0]+" IP is :"+ipNodes.get(i).split(":")[0].split("-")[1]+" Port is :"+ipNodes.get(i).split(":")[1]);
		}
			
	}

	@Override
	public void setServerPort(int localPort) {
		// TODO Auto-generated method stub
		
	}

	public void taskInitiate(int startSize) throws IOException {
		// TODO Auto-generated method stub
		messageSize = startSize;
		//System.out.println("In Task initiate");
		//System.out.println("Arraylist size "+ l.size());
		for (int i = 0; i < l.size(); i++) {
			int key = l.get(i);
			Connection c = connectionStored.get(key);
			RegistryRequestTaskInitiate t = new RegistryRequestTaskInitiate(messageSize); 
			c.getSender().sendData(t.getMyBytes());
		}
		
		
		
	}
}
