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
public class NodeReportsOverlaySetupStatus implements Event {
	
	private int s;
	private String message;
	int report;
	public int rs;
	public String receivedMessage;
	public NodeReportsOverlaySetupStatus(int s){
		System.out.println("Send karte waqt Const");
		this.s =s;
		if(s == 1){
			message = "Setup - Complete";
		}else {
			message = "Setup - problem";
		}
	}
	
	public NodeReportsOverlaySetupStatus(byte[] marshalledData) throws IOException{
		//System.out.println("Reccive ke cons");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		report = dataInputStream.readByte();
		rs = dataInputStream.readInt();
		int nextLenght = dataInputStream.readInt();
		byte[] clientIpInByte = new byte[nextLenght];
		dataInputStream.readFully(clientIpInByte,0,nextLenght);
		receivedMessage = new String(clientIpInByte);
		
		byteArrayInputStream.close();
		dataInputStream.close();
	}
	
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		
		dOutputStream.writeByte(MessageType.NODE_REPORTS_OVERLAY_SETUP_STATUS);
		dOutputStream.writeInt(s);
		byte[] ip = message.getBytes(); 	//convert client ip into bytes
		int ipByteLenght = ip.length;
		dOutputStream.writeInt(ipByteLenght);
		dOutputStream.write(ip);
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return MessageType.NODE_REPORTS_OVERLAY_SETUP_STATUS;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
