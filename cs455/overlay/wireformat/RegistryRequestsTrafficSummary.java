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
public class RegistryRequestsTrafficSummary implements Event {
	
	public RegistryRequestsTrafficSummary(){
		
		//System.out.println("RegistryRequestsTrafficSummary ----------------------------------------");
	}
	
	public RegistryRequestsTrafficSummary(byte[] marshalledData) throws IOException{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		byte report = dataInputStream.readByte();
		byteArrayInputStream.close();
		dataInputStream.close();	
	}
	
	
	
	
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		dOutputStream.writeByte(MessageType.REGISTRY_REQUESTS_TRAFFIC_SUMMARY);
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return MessageType.REGISTRY_REQUESTS_TRAFFIC_SUMMARY;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
