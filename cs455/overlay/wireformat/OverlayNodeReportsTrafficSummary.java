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
public class OverlayNodeReportsTrafficSummary implements Event{

	public int getRececurrentNodeId() {
		return rececurrentNodeId;
	}

	public void setRececurrentNodeId(int rececurrentNodeId) {
		this.rececurrentNodeId = rececurrentNodeId;
	}

	public long getRecesendPacketCounter() {
		return recesendPacketCounter;
	}

	public void setRecesendPacketCounter(long recesendPacketCounter) {
		this.recesendPacketCounter = recesendPacketCounter;
	}

	public long getRecereceivedPacketCounter() {
		return recereceivedPacketCounter;
	}

	public void setRecereceivedPacketCounter(long recereceivedPacketCounter) {
		this.recereceivedPacketCounter = recereceivedPacketCounter;
	}

	public long getRecepayLoadSendCounter() {
		return recepayLoadSendCounter;
	}

	public void setRecepayLoadSendCounter(long recepayLoadSendCounter) {
		this.recepayLoadSendCounter = recepayLoadSendCounter;
	}

	public long getRecepayLoadReceivedCounter() {
		return recepayLoadReceivedCounter;
	}

	public void setRecepayLoadReceivedCounter(long recepayLoadReceivedCounter) {
		this.recepayLoadReceivedCounter = recepayLoadReceivedCounter;
	}

	public long getRecerelayedCounter() {
		return recerelayedCounter;
	}

	public void setRecerelayedCounter(long recerelayedCounter) {
		this.recerelayedCounter = recerelayedCounter;
	}

	private int currentNodeId;
	private long sendPacketCounter;private long receivedPacketCounter;
	 private long payLoadSendCounter;
	private long payLoadReceivedCounter;
	private long relayedCounter;
	
	private int rececurrentNodeId;
	private long recesendPacketCounter;private long recereceivedPacketCounter;
	 private long recepayLoadSendCounter;
	private long recepayLoadReceivedCounter;
	private long recerelayedCounter;
	
	public OverlayNodeReportsTrafficSummary(int currentNodeId,
			long sendPacketCounter,long relayedCounter, long receivedPacketCounter,
			long payLoadSendCounter, long payLoadReceivedCounter) {
		this.currentNodeId = currentNodeId;
		this.sendPacketCounter = sendPacketCounter;
		this.relayedCounter = relayedCounter;
		this.receivedPacketCounter = receivedPacketCounter;
		this.payLoadSendCounter = payLoadSendCounter;
		this.payLoadReceivedCounter = payLoadReceivedCounter;
		}
	
	public OverlayNodeReportsTrafficSummary(byte[] marshalledData) throws IOException{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		byte typr = dataInputStream.readByte();
		rececurrentNodeId = dataInputStream.readInt();
		recesendPacketCounter = dataInputStream.readLong();
		recerelayedCounter = dataInputStream.readLong();
		recereceivedPacketCounter = dataInputStream.readLong();
		recepayLoadSendCounter = dataInputStream.readLong();
		recepayLoadReceivedCounter = dataInputStream.readLong();
		
		byteArrayInputStream.close();
		dataInputStream.close();
		
	}
	
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		
		dOutputStream.writeByte(MessageType.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY);
		dOutputStream.writeInt(currentNodeId);
		dOutputStream.writeLong(sendPacketCounter);
		dOutputStream.writeLong(relayedCounter);
		dOutputStream.writeLong(receivedPacketCounter);
		dOutputStream.writeLong(payLoadSendCounter);
		dOutputStream.writeLong(payLoadReceivedCounter);
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	//runs perfect!
	
	
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return MessageType.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
