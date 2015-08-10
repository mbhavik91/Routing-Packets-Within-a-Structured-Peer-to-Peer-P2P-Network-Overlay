/**
 * 
 */
package cs455.overlay.node;

import java.io.IOException;
import java.net.Socket;

import cs455.overlay.wireformat.Event;
import cs455.overlay.wireformat.MessageType;

/**
 * @author mbhavik
 *
 */
public interface Node {

	public void onEvent(Event e, Socket socket) throws IOException;

	public void setServerPort(int localPort);
}
