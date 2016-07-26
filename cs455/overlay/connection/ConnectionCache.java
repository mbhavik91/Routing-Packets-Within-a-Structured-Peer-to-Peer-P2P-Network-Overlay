/**
 * 
 */
package cs455.overlay.connection;
//
import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;
import cs455.overlay.transport.TCPReceiver;
import cs455.overlay.transport.TCPSender;

/**
 * @author mbhavik
 *
 */
public class ConnectionCache {
	public TCPReceiver getrTcpReceiver() {
		return rTcpReceiver;
	}

	public void setrTcpReceiver(TCPReceiver rTcpReceiver) {
		this.rTcpReceiver = rTcpReceiver;
	}

	public TCPSender getrTcpSender() {
		return rTcpSender;
	}

	public void setrTcpSender(TCPSender rTcpSender) {
		this.rTcpSender = rTcpSender;
	}

	TCPReceiver rTcpReceiver;
	TCPSender rTcpSender;
	Node n;
	public ConnectionCache(Socket s, Node n) throws IOException{
		this.n=n;
		rTcpSender = new TCPSender(s);
		rTcpReceiver = new TCPReceiver(s,n);
		//System.out.println("Connection Cache constr");
		Thread t = new Thread(rTcpReceiver);
		setrTcpReceiver(rTcpReceiver);
		t.start();
	}
}
