/**
 * 
 */
package cs455.overlay.wireformat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author mbhavik
 *
 */
public class OverlayNodeSendsDeregistration implements Event {
	
	public String getMessagingNodeIp() {
		return messagingNodeIp;
	}

	public void setMessagingNodeIp(String messagingNodeIp) {
		this.messagingNodeIp = messagingNodeIp;
	}

	public int getMessagingNodeport() {
		return messagingNodeport;
	}

	public void setMessagingNodeport(int messagingNodeport) {
		this.messagingNodeport = messagingNodeport;
	}

	public int getMessagingNodeId() {
		return messagingNodeId;
	}

	public void setMessagingNodeId(int messagingNodeId) {
		this.messagingNodeId = messagingNodeId;
	}

	private String nodeIP;
	private int nodePort;
	private int nodeIID;
	public String messagingNodeIp;
	public int messagingNodeport;
	public int messagingNodeId;
	public OverlayNodeSendsDeregistration(String ip, int port, int nodeID){
		nodeIP = ip;
		nodePort = port;
		nodeIID = nodeID;
	}
	
	public OverlayNodeSendsDeregistration(byte[] marshalledData) throws IOException{
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		byte control_message = dataInputStream.readByte();
		int nextLenght = dataInputStream.readInt();
		byte[] clientIpInByte = new byte[nextLenght];
		dataInputStream.readFully(clientIpInByte,0,nextLenght);
		messagingNodeIp = new String(clientIpInByte);
		messagingNodeport = dataInputStream.readInt();
		messagingNodeId = dataInputStream.readInt();
		byteArrayInputStream.close();
		dataInputStream.close();
	}
		
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		
		dOutputStream.writeByte(MessageType.OVERLAY_NODE_SENDS_DEREGISTRATION_REQUEST);
		byte[] ip = nodeIP.getBytes(); 	//convert client ip into bytess
		int ipByteLenght = ip.length;
		dOutputStream.writeInt(ipByteLenght);
		dOutputStream.write(ip);
		dOutputStream.writeInt(nodePort);
		dOutputStream.writeInt(nodeIID);
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	
	
	
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return MessageType.OVERLAY_NODE_SENDS_DEREGISTRATION_REQUEST;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
