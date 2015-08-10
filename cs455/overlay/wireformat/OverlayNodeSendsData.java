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
public class OverlayNodeSendsData implements Event{
	public int getReceivedDestID() {
		return receivedDestID;
	}
	public void setReceivedDestID(int receivedDestID) {
		this.receivedDestID = receivedDestID;
	}
	public int getReceivedSourceID() {
		return receivedSourceID;
	}
	public void setReceivedSourceID(int receivedSourceID) {
		this.receivedSourceID = receivedSourceID;
	}
	public long getReceivedPayLoad() {
		return receivedPayLoad;
	}
	public void setReceivedPayLoad(long receivedPayLoad) {
		this.receivedPayLoad = receivedPayLoad;
	}
	public int getReceivedTrace1() {
		return receivedTrace1;
	}
	public void setReceivedTrace1(int receivedTrace1) {
		this.receivedTrace1 = receivedTrace1;
	}
	public int getReceivedTrace2() {
		return receivedTrace2;
	}
	public void setReceivedTrace2(int receivedTrace2) {
		this.receivedTrace2 = receivedTrace2;
	}

	private int destID; private int sourceID; private long payLoad; private int trace1; private int trace2; int report;
	private int receivedDestID; private int receivedSourceID; private long receivedPayLoad; private int receivedTrace1; private int receivedTrace2;
	public OverlayNodeSendsData(int destID, int sourceID, long payLoad){
		//System.out.println("Overlay Node start send data const-------------------------");
		this.destID = destID;
		this.sourceID = sourceID;
		this.payLoad = payLoad;
		//this.trace1 = trace1;
		//this.trace2 = trace2;
		
	}
	public OverlayNodeSendsData(byte[] marshalledData) throws IOException{
		//System.out.println("Overlay Node start receive data const-------------------------");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		report = dataInputStream.readByte();
		receivedDestID = dataInputStream.readInt();
		receivedSourceID = dataInputStream.readInt();
		receivedPayLoad = dataInputStream.readLong();
		byteArrayInputStream.close();
		dataInputStream.close();
	}
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		
		dOutputStream.writeByte(MessageType.OVERLAY_NODE_SENDS_DATA);
		
		dOutputStream.writeInt(destID);
		dOutputStream.writeInt(sourceID);
		dOutputStream.writeLong(payLoad);
		//dOutputStream.writeInt(trace1);
		//dOutputStream.writeInt(trace2);
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	
	
	
	
	
	
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return MessageType.OVERLAY_NODE_SENDS_DATA;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
