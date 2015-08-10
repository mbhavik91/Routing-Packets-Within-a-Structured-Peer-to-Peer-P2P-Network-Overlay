/**
 * 
 */
package cs455.overlay.wireformat;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author mbhavik
 *
 */
public class EventFactory {
	
	//public static byte[] receivedData;
	/*public EventFactory(byte[] data) throws IOException{
		receivedData = data;
	}*/

	public static Event createEvent(byte[] data) throws IOException{
		
		
		
		int controlType;
		EventFactory e;
		//MessageType mtype = null;
		Event ev  = null;
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(byteArrayInputStream));
		controlType = dataInputStream.readByte();
		switch (controlType) {
		case MessageType.OVERLAY_NODE_SENDS_REGISTRATION_REQUEST: 
			OverlayNodeSendsRegistrationRequest r = new OverlayNodeSendsRegistrationRequest(data);
			ev = r;
			break;
			
		case MessageType.REGISTRY_REPORTS_REGISTRATION_STATUS:
			RegistryReportsRegistrationStatus rstatus = new RegistryReportsRegistrationStatus(data);
			ev = rstatus;
			break;
		
		case MessageType.OVERLAY_NODE_SENDS_DEREGISTRATION_REQUEST: OverlayNodeSendsDeregistration deRegister = new OverlayNodeSendsDeregistration(data);ev = deRegister;break;
				
		case MessageType.REGISTRY_REPORTS_DEREGISTRATION_STATUS: RegistryReportsDeregistrationStatus deRegisterStatus = new RegistryReportsDeregistrationStatus(data);ev = deRegisterStatus;
				break;
				
		case MessageType.REGISTRY_SENDS_NODE_MANIFEST_REQUEST: //System.out.println("In Event Fact");
			RegistrySendsNodeManifest nm = new RegistrySendsNodeManifest(data);
			ev = nm;
				break;
		case MessageType.OVERLAY_NODE_SENDS_DATA: OverlayNodeSendsData rx = new OverlayNodeSendsData(data);
												ev = rx;break;
				
		case MessageType.NODE_REPORTS_OVERLAY_SETUP_STATUS: NodeReportsOverlaySetupStatus s = new NodeReportsOverlaySetupStatus(data);
																						ev = s;break;
		
		case MessageType.REGISTRY_REQUESTS_TASK_INITIATE: RegistryRequestTaskInitiate task = new RegistryRequestTaskInitiate(data);ev=task;break;
																						
		case MessageType.OVERLAY_NODE_REPORTS_TASK_FINISHED:OverlayNodeReportsTaskFinished taskFin = new OverlayNodeReportsTaskFinished(data);ev = taskFin; break;
		
		case MessageType.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY: OverlayNodeReportsTrafficSummary tsum = new OverlayNodeReportsTrafficSummary(data); ev = tsum; break;
		
		case MessageType.REGISTRY_REQUESTS_TRAFFIC_SUMMARY: RegistryRequestsTrafficSummary trafficSummary = new RegistryRequestsTrafficSummary(data); ev = trafficSummary; break;
				
		default:
			break;
		}
		return ev;
	}
	
}
