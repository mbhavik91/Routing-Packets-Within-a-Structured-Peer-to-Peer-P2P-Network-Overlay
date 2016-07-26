/**
 * 
 */
package cs455.overlay.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cs455.overlay.node.MessagingNode;
import cs455.overlay.node.Node;
import cs455.overlay.transport.TCPReceiver;
import cs455.overlay.transport.TCPSender;

/**
 * @author mbhavik
 *
 */
public class ServerThread extends Thread {

	Node n;
	public ServerThread(Node n) throws IOException {
		// TODO Auto-generated constructor stub
		this.n=n;
	}
	
	
	
	@Override
	public void run() {
		TCPSender s;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(0);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		n.setServerPort(serverSocket.getLocalPort());
		
		while(true){
		try {
			Socket socket = serverSocket.accept();
			TCPReceiver receiver = new TCPReceiver(socket,n);
			Thread t = new Thread(receiver);
			s = new TCPSender(socket);
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
		
}
}
