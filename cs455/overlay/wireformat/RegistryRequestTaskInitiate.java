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
public class RegistryRequestTaskInitiate implements Event{

	public int getReceivedMess() {
		return receivedMess;
	}

	public void setReceivedMess(int receivedMess) {
		this.receivedMess = receivedMess;
	}
	public int ms;
	private int receivedMess;
	public RegistryRequestTaskInitiate(int messageSize) {
		// TODO Auto-generated constructor stub
		ms = messageSize;
	}

	public RegistryRequestTaskInitiate(byte[] marshalledData) throws IOException{
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		byte report = dataInputStream.readByte();
		receivedMess = dataInputStream.readInt();
		setReceivedMess(receivedMess);
		byteArrayInputStream.close();
		dataInputStream.close();
	}
	
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		
		dOutputStream.writeByte(MessageType.REGISTRY_REQUESTS_TASK_INITIATE);
		dOutputStream.writeInt(ms);
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return MessageType.REGISTRY_REQUESTS_TASK_INITIATE;
	}
	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
