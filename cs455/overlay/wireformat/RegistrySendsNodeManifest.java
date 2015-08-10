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
public class RegistrySendsNodeManifest implements Event{
	
	//public RegistryReportsRegistrationStatus()
	String ip1; int p1; String ip2; int p2; String ip4;int p4;int u4;
	String ip3; int p3; int u1; int u2; int u3;
	int[] b;
	public int uniqueID1; public int uniqueID2; public int uniqueID3;
	public int rport1; public int rport2; public int rport3;
	public String rip1; public String rip2; public String rip3;
	Object[] b2;
	public int[] rece;
	public int uniqueID4;public int rport4;public String rip4;
	public int receivedSize;
	public RegistrySendsNodeManifest(String ip1, int p1, String ip2, int p2,
			String ip3, int p3, int u1, int u2, int u3, Object[] b2) {
		this.ip1 = ip1;
		this.ip2 = ip2;
		this.ip3 = ip3;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
		//System.out.println("Node Manifest ke Const");
		this.b2 = b2;
	}
	
	public RegistrySendsNodeManifest(byte[] marshalledData) throws IOException{
		
		//System.out.println("In const RSNM");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(marshalledData);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		
		dataInputStream.readByte();
		receivedSize = dataInputStream.readInt();
		if(receivedSize == 3){
		uniqueID1 = dataInputStream.readInt();
		int nextLenght = dataInputStream.readInt();
		byte[] client1 = new byte[nextLenght];
		dataInputStream.readFully(client1);
		rip1 = new String(client1);
		rport1 = dataInputStream.readInt();
		
		uniqueID2 = dataInputStream.readInt();
		int nextLenght3 = dataInputStream.readInt();
		byte[] client3 = new byte[nextLenght3];
		dataInputStream.readFully(client3);
		rip2 = new String(client3);
		rport2 = dataInputStream.readInt();
		
		uniqueID3 = dataInputStream.readInt();
		int nextLenght1 = dataInputStream.readInt();
		byte[] client2 = new byte[nextLenght1];
		dataInputStream.readFully(client2);
		rip3 = new String(client2);
		rport3 = dataInputStream.readInt();
		
		int len = dataInputStream.readInt();
		rece = new int[len];
		for (int i = 0; i < rece.length; i++) {
			rece[i] = dataInputStream.readInt();
		}
		byteArrayInputStream.close();
		dataInputStream.close();
		}
		else {

			uniqueID1 = dataInputStream.readInt();
			int nextLenght = dataInputStream.readInt();
			byte[] client1 = new byte[nextLenght];
			dataInputStream.readFully(client1);
			rip1 = new String(client1);
			rport1 = dataInputStream.readInt();
			
			uniqueID2 = dataInputStream.readInt();
			int nextLenght3 = dataInputStream.readInt();
			byte[] client3 = new byte[nextLenght3];
			dataInputStream.readFully(client3);
			rip2 = new String(client3);
			rport2 = dataInputStream.readInt();
			
			uniqueID3 = dataInputStream.readInt();
			int nextLenght1 = dataInputStream.readInt();
			byte[] client2 = new byte[nextLenght1];
			dataInputStream.readFully(client2);
			rip3 = new String(client2);
			rport3 = dataInputStream.readInt();
			
			uniqueID4 = dataInputStream.readInt();
			int nextLenght4 = dataInputStream.readInt();
			byte[] client4 = new byte[nextLenght4];
			dataInputStream.readFully(client4);
			rip4 = new String(client4);
			rport4 = dataInputStream.readInt();
			
			int len = dataInputStream.readInt();
			rece = new int[len];
			for (int i = 0; i < rece.length; i++) {
				rece[i] = dataInputStream.readInt();
			}
			byteArrayInputStream.close();
			dataInputStream.close();
			
		}
		
	}
	
	public RegistrySendsNodeManifest(String ip12, int p12, String ip22,
			int p22, String ip32, int p32, String ip4, int p4, int u12,
			int u22, int u32, int u4, Object[] b3) {
		// TODO Auto-generated constructor stub
		this.ip1 = ip12;
		this.ip2 = ip22;
		this.ip3 = ip32;
		this.ip4 = ip4;
		this.p1 = p12;
		this.p2 = p22;
		this.p3 = p32;
		this.p4 = p4;
		this.u1 = u12;
		this.u2 = u22;
		this.u3 = u32;
		this.u4 = u4;
		//System.out.println("Node Manifest ke Const");
		this.b2 = b3;
		
	}
	
	public byte[] getMyBytes4() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		int q= 4;
		dOutputStream.writeByte(MessageType.REGISTRY_SENDS_NODE_MANIFEST_REQUEST);
		dOutputStream.writeInt(q);
		dOutputStream.writeInt(u1);
		byte[] ip = ip1.getBytes(); 	//convert client ip into bytes
		int ipByteLenght = ip.length;
		dOutputStream.writeInt(ipByteLenght);
		dOutputStream.write(ip);  // ip lakhayu
		dOutputStream.writeInt(p1);
		
		
		dOutputStream.writeInt(u2);
		byte[] ipx = ip2.getBytes(); 	//convert client ip into bytes
		int ipByteLenght1 = ipx.length;
		dOutputStream.writeInt(ipByteLenght1);
		dOutputStream.write(ipx);  // ip lakhayu
		dOutputStream.writeInt(p2);
		
		dOutputStream.writeInt(u3);
		byte[] ipy = ip3.getBytes(); 	//convert client ip into bytes
		int ipByteLenghtx = ipy.length;
		dOutputStream.writeInt(ipByteLenghtx);
		dOutputStream.write(ipy);  // ip lakhayu
		dOutputStream.writeInt(p3);
		
		dOutputStream.writeInt(u4);
		byte[] ipz = ip4.getBytes(); 	//convert client ip into bytes
		int ipByteLenghtxz = ipz.length;
		dOutputStream.writeInt(ipByteLenghtxz);
		dOutputStream.write(ipz);  // ip lakhayu
		dOutputStream.writeInt(p4);
		
		int l = b2.length;
//		byte[] o = new byte[l];
		dOutputStream.writeInt(l);;
		for (int i = 0; i < b2.length; i++) {
			dOutputStream.writeInt((int) b2[i]);
		}
		//byte[] b1 = b.to
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	
	
	public byte[] getMyBytes() throws IOException{
		byte[] marshalledBytes = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		int lm=3;
		dOutputStream.writeByte(MessageType.REGISTRY_SENDS_NODE_MANIFEST_REQUEST);
		dOutputStream.writeInt(lm);
		dOutputStream.writeInt(u1);
		byte[] ip = ip1.getBytes(); 	//convert client ip into bytes
		int ipByteLenght = ip.length;
		dOutputStream.writeInt(ipByteLenght);
		dOutputStream.write(ip);  // ip lakhayu
		dOutputStream.writeInt(p1);
		
		
		dOutputStream.writeInt(u2);
		byte[] ipx = ip2.getBytes(); 	//convert client ip into bytes
		int ipByteLenght1 = ipx.length;
		dOutputStream.writeInt(ipByteLenght1);
		dOutputStream.write(ipx);  // ip lakhayu
		dOutputStream.writeInt(p2);
		
		dOutputStream.writeInt(u3);
		byte[] ipy = ip3.getBytes(); 	//convert client ip into bytes
		int ipByteLenghtx = ipy.length;
		dOutputStream.writeInt(ipByteLenghtx);
		dOutputStream.write(ipy);  // ip lakhayu
		dOutputStream.writeInt(p3);
		 
		int lx = b2.length;
//		byte[] o = new byte[l];
		dOutputStream.writeInt(lx);;
		for (int i = 0; i < b2.length; i++) {
			dOutputStream.writeInt((int) b2[i]);
		}
		//byte[] b1 = b.to
		dOutputStream.flush();
		marshalledBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		dOutputStream.close();
		return marshalledBytes;
	}
	
	
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		//return control_message;
		return MessageType.REGISTRY_SENDS_NODE_MANIFEST_REQUEST;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

}
