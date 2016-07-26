/**
 * 
 */
package cs455.overlay.connection;

import java.io.IOException;
import java.net.Socket;
//
import cs455.overlay.transport.TCPReceiver;
import cs455.overlay.transport.TCPSender;

/**
 * @author mbhavik
 *
 */
public class Connection {
	
	public TCPSender getSender() {
		return sender;
	}

	public void setSender(TCPSender sender) {
		this.sender = sender;
	}

	public TCPReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(TCPReceiver receiver) {
		this.receiver = receiver;
	}

	private TCPSender sender;
	private TCPReceiver receiver;
	
	public Connection(Socket socket) throws IOException{
		
		sender = new TCPSender(socket);
		/*receiver = new TCPReceiver(socket);
		Thread t = new Thread(receiver);
		t.start();*/
		
	}

}
