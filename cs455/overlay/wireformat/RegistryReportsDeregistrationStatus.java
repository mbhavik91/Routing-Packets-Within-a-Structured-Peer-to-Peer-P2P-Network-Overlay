package cs455.overlay.wireformat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistryReportsDeregistrationStatus implements Event{
	
	public String getReceivedDeregistrationMessage() {
		return receivedDeregistrationMessage;
	}

	public void setReceivedDeregistrationMessage(
			String receivedDeregistrationMessage) {
		this.receivedDeregistrationMessage = receivedDeregistrationMessage;
	}

	private int statusOfDeregistration;
	private String deregistrationMessage;
	public int control_type;
	public int receivedDeregistrationStatus;
	public String receivedDeregistrationMessage;
	public RegistryReportsDeregistrationStatus(int stat){
		//System.out.println("RegistryReportsDeregistrationStatus................send karte waqt");
		statusOfDeregistration = stat;
		if (statusOfDeregistration == 1) {
			deregistrationMessage = "Deregistered successfully !!!!";
		} else {
			deregistrationMessage = "Node not registered ";
		}
	}
	
	public RegistryReportsDeregistrationStatus(byte[] marshalledData) throws IOException{
		//System.out.println("RegistryReportsDeregistrationStatus................during UNMARSHALLING");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		control_type = dataInputStream.readByte();
		receivedDeregistrationStatus = dataInputStream.readInt();
		//ruID = dataInputStream.readInt();
		int nextLenght = dataInputStream.readInt();
		byte[] clientMessageInByte = new byte[nextLenght];
		dataInputStream.readFully(clientMessageInByte,0,nextLenght);
		receivedDeregistrationMessage = new String(clientMessageInByte);
		
		byteArrayInputStream.close();
		dataInputStream.close();
	}
	
	
	
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		
		dOutputStream.writeByte(MessageType.REGISTRY_REPORTS_DEREGISTRATION_STATUS);
		dOutputStream.writeInt(statusOfDeregistration);
		//dOutputStream.writeInt(uID);
		byte[] m = deregistrationMessage.getBytes(); 	//convert client ip into bytes
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
		return MessageType.REGISTRY_REPORTS_DEREGISTRATION_STATUS;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
