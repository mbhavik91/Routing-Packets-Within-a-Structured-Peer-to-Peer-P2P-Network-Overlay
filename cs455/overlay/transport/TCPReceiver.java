/**
 * 
 */
package cs455.overlay.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;
import cs455.overlay.wireformat.Event;
import cs455.overlay.wireformat.EventFactory;

/**
 * @author mbhavik
 * 
 */

public class TCPReceiver implements Runnable {

	Node node;
	Socket socket;
	private DataInputStream dataInputStream;
	public byte[] common;

	public TCPReceiver(Socket socket, Node node) throws IOException {
		this.socket = socket;
		this.node = node;
		dataInputStream = new DataInputStream(socket.getInputStream());
	}

	public TCPReceiver(Socket socket2) throws IOException {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void run() {

		//System.out.println("in run");

		receive();

	}

	public void receive() {
		
		byte[] data = null;
		int dataLenght;

		try {
			while (socket!=null) {
				 //System.out.println("In Receiver --->" + dataInputStream);
				dataLenght = dataInputStream.readInt();
				data = new byte[dataLenght];
				dataInputStream.readFully(data, 0, dataLenght);
				// System.out.println(data);
				Event e = EventFactory.createEvent(data);
				node.onEvent(e, socket);
				// System.out.println("OnEvenet");

				// System.out.println("In TCP Receiver"+data);
			}

		} catch (Exception e) {
			// System.out.println("Exception in TCPReceiver");
			e.printStackTrace();
		}

	}

}
