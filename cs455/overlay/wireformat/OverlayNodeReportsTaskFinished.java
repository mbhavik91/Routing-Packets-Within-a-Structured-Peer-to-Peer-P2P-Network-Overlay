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
public class OverlayNodeReportsTaskFinished implements Event {
	
	public String getReceClientIp() {
		return receClientIp;
	}

	public void setReceClientIp(String receClientIp) {
		this.receClientIp = receClientIp;
	}

	public int getRport() {
		return rport;
	}

	public void setRport(int rport) {
		this.rport = rport;
	}

	public int getRuniqueID() {
		return runiqueID;
	}

	public void setRuniqueID(int runiqueID) {
		this.runiqueID = runiqueID;
	}

	public int getReceivedI() {
		return receivedI;
	}

	public void setReceivedI(int receivedI) {
		this.receivedI = receivedI;
	}

	private int i;
	private int receivedI; 
	private String istring;
	private int serverPort;
	private int currentNodeId;
	private String receClientIp;
	private int rport;
	private int runiqueID;
	public OverlayNodeReportsTaskFinished(int i, String string, int serverPort, int currentNodeId) {
		
		//System.out.println("OverlayNodeReportsTaskFinished----------------");
		// TODO Auto-generated constructor stub
		this.i = i;
		this.istring = string;
		this.serverPort = serverPort;
		this.currentNodeId = currentNodeId;
	}
	
	public OverlayNodeReportsTaskFinished(byte[] marshalledData) throws IOException{
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		byte type = dataInputStream.readByte();
		receivedI = dataInputStream.readInt();
		int nextLenght = dataInputStream.readInt();
		byte[] clientIpInByte = new byte[nextLenght];
		dataInputStream.readFully(clientIpInByte,0,nextLenght);
		receClientIp = new String(clientIpInByte);
		rport = dataInputStream.readInt();
		runiqueID = dataInputStream.readInt();
		byteArrayInputStream.close();
		dataInputStream.close();	
	}
	
	public byte[] getMyBytes() throws IOException{byte[] marshalledBytes = null;
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
	
	dOutputStream.writeByte(MessageType.OVERLAY_NODE_REPORTS_TASK_FINISHED);
	dOutputStream.writeInt(i);
	byte[] ip = istring.getBytes(); 	//convert client ip into bytes
	int ipByteLenght = ip.length;
	dOutputStream.writeInt(ipByteLenght);
	dOutputStream.write(ip);
	dOutputStream.writeInt(serverPort);
	dOutputStream.writeInt(currentNodeId);
	dOutputStream.flush();
	marshalledBytes = byteArrayOutputStream.toByteArray();
	byteArrayOutputStream.close();
	dOutputStream.close();
	return marshalledBytes;
	}
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return MessageType.OVERLAY_NODE_REPORTS_TASK_FINISHED;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
