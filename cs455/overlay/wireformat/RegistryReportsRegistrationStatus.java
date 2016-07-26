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
public class RegistryReportsRegistrationStatus implements Event{
	
	private int type = MessageType.REGISTRY_REPORTS_REGISTRATION_STATUS;
	private int status;
	private String message;
	public int rstatus;
	public String receivedMessage;
	public int rtype;
	private int uID;
	public int ruID;
	
	public RegistryReportsRegistrationStatus(byte[] marshalledData) throws IOException{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		rtype = dataInputStream.readByte();
		rstatus = dataInputStream.readInt();
		ruID = dataInputStream.readInt();
		int nextLenght = dataInputStream.readInt();
		byte[] clientMessageInByte = new byte[nextLenght];
		dataInputStream.readFully(clientMessageInByte);
		receivedMessage = new String(clientMessageInByte);
		//rport = dataInputStream.readInt();
		byteArrayInputStream.close();
		dataInputStream.close();
	}
	
	
	public RegistryReportsRegistrationStatus(int status, int uniqueId){
		
		uID = uniqueId;
		if(status == 1){
			message = "Node added successefully...!!!";
		}
		else if (status == 0) {
			message = "Node not added";
			
		}
		
	}
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		
		dOutputStream.writeByte(MessageType.REGISTRY_REPORTS_REGISTRATION_STATUS);
		dOutputStream.writeInt(status);
		dOutputStream.writeInt(uID);
		byte[] m = message.getBytes(); 	//convert client ip into bytes
		int mLenghtInByte = m.length;
		dOutputStream.writeInt(mLenghtInByte);
		dOutputStream.write(m);
		
		dOutputStream.flush();
		
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		
		return marshalledBytes;
	}


	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}


	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}
	




}
