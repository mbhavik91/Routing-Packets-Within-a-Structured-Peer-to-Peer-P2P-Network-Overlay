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
public class OverlayNodeSendsRegistrationRequest implements Event{
	
	private String ipOfClient;
	private int portOfClient;
	public String receivedClientIp;
	public int rport;
	public int report;
	public OverlayNodeSendsRegistrationRequest(byte[] marshalledData) throws IOException{
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		report = dataInputStream.readByte();
		int nextLenght = dataInputStream.readInt();
		byte[] clientIpInByte = new byte[nextLenght];
		dataInputStream.readFully(clientIpInByte,0,nextLenght);
		receivedClientIp = new String(clientIpInByte);
		rport = dataInputStream.readInt();
		byteArrayInputStream.close();
		dataInputStream.close();	
	}

	public OverlayNodeSendsRegistrationRequest(String ip, int port){
		
		this.ipOfClient = ip;
		this.portOfClient = port;
	}
	
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		
		dOutputStream.writeByte(MessageType.OVERLAY_NODE_SENDS_REGISTRATION_REQUEST);
		byte[] ip = ipOfClient.getBytes(); 	//convert client ip into bytes
		int ipByteLenght = ip.length;
		dOutputStream.writeInt(ipByteLenght);
		dOutputStream.write(ip);
		dOutputStream.writeInt(portOfClient);
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	//everything good till now

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return MessageType.OVERLAY_NODE_SENDS_REGISTRATION_REQUEST;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}
}
