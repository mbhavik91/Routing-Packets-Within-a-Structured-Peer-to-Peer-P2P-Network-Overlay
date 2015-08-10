/**
 * 
 */
package cs455.overlay.transport;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author mbhavik
 *
 */
public class TCPSender {
	

	
	private Socket socket;
	private DataOutputStream dataOutputStream;
	
	public TCPSender(Socket socket) throws IOException{
		this.socket = socket;
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	public synchronized void sendData(byte[] data) throws IOException {
		
		//socket.getOutputStream();
		int dataLenght = data.length;
		try {
			//System.out.println("TCP Sender");
			dataOutputStream.writeInt(dataLenght);
			dataOutputStream.write(data, 0, dataLenght);
			dataOutputStream.flush();
			
		} catch (Exception e) {
			System.out.println("Error in TCPSender");
		}
	}


}
