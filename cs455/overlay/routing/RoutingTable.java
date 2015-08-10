/**
 * 
 */
package cs455.overlay.routing;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cs455.overlay.connection.Connection;
import cs455.overlay.node.Registry;
import cs455.overlay.transport.TCPSender;
import cs455.overlay.wireformat.RegistrySendsNodeManifest;

/**
 * @author mbhavik
 *
 */
public class RoutingTable {
	
	public ArrayList<Integer> getBackup1() {
		return backup1;
	}
	public void setBackup1(ArrayList<Integer> backup1) {
		this.backup1 = backup1;
	}
	ArrayList<Integer> backup1 = new ArrayList<Integer>();
	
	ArrayList<Integer> backup14 = new ArrayList<Integer>();
	
	public ArrayList<String> display = new ArrayList<String>();
	public void createRoutingInfo(HashMap<Integer, String> receivedMap, HashMap<Integer, Connection> connectionStored, int overlaysize ) throws UnknownHostException, IOException {
		
		//sort the received Map
		Map<Integer, String> sortedMap = new TreeMap<Integer, String>(receivedMap);
		
		HashMap<Integer , Connection> connect = connectionStored;
		ArrayList<Integer> list = new ArrayList<Integer>();
		Map<String, Integer> reverse = new HashMap<String, Integer>();			//to get unique ID from ip:port
		Set set = sortedMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
              Map.Entry me = (Map.Entry)iterator.next();
              /*System.out.print(me.getKey() + ": ");
              System.out.println(me.getValue());*/
              
              list.add((Integer) me.getKey());
              reverse.put((String)me.getValue(),(int) me.getKey());
        }
        
        ArrayList<Integer> backup = new ArrayList<Integer>(list);
        ArrayList<String> routing_info = new ArrayList<String>();
        backup1 = backup;
        Object[] b = backup.toArray();
        setBackup1(backup1);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int length =overlaysize;
		for(int i=0;i<(list.size()-length -1);){
			
			//newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+1))%(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+2))%(Math.pow(2, length)))));
			routing_info.add(""+list.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+1))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+2))%((int)(Math.pow(2, length)))));
			temp.add(list.get(i));
			list.remove(i);
			i=0;
		}
		list.addAll(temp);
		//System.out.println("List Size     ----------------------------------------------" + list.size()+" "+(list.size()-routing_info.size()));
		int c = 0;
		for(int i=0;i<(list.size()-routing_info.size()+c);){
			
			//newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+1))%(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+2))%(Math.pow(2, length)))));
			routing_info.add(""+list.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+1))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+2))%((int)(Math.pow(2, length)))));
			temp.add(list.get(i));
			list.remove(i);
			c++;
			i=0;
		}
		/*for (int i = 0; i < routing_info.size(); i++) {
			System.out.println(backup.get(i)+"-->"+routing_info.get(i));
		}*/
		
        int[] li = new int[sortedMap.size()-1];
       for (int i = 0; i < sortedMap.size(); i++) {
		//System.out.println(sorted.get(l.containsKey(Integer.parseInt(routing_info.get(i).split("-")[0]))));
    	   //System.out.println();
    	   
    	   //System.out.println("Printing th table:");
    	   display.add("For Key :"+backup.get(i)+" "+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1])))+" "+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])));
    	  // System.out.println("For Key :"+backup.get(i)+" "+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1])))+" "+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])));
    	 //  System.out.println(sorted.get(i));
       }
    	   //bhejne ka yah pe kar raha hu
    	   for (int i = 0; i < sortedMap.size(); i++) {
    		   int key = backup.get(i);
    		   	
    		   	
    		   //String senderIP = sortedMap.get(key).split(":")[0];
    		   //int senderP  = Integer.parseInt(sortedMap.get(key).split(":")[1]);
    		   //Socket sock = new Socket(senderIP, senderP);
    		   Connection s = connect.get(key);
    		   //System.out.println(s);
    		   
    		   String ip1 = sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0])).split(":")[0];
    		   String ip2 = sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1])).split(":")[0];
    		   String ip3 = sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])).split(":")[0];
    		   int p1 = Integer.parseInt(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0])).split(":")[1]);
    		   int p2 = Integer.parseInt(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1])).split(":")[1]);
    		   int p3 = Integer.parseInt(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])).split(":")[1]);
    		   int u1 = Integer.parseInt(routing_info.get(i).split("-")[0]);
    		   int u2 = Integer.parseInt(routing_info.get(i).split("-")[1]);
    		   int u3 = Integer.parseInt(routing_info.get(i).split("-")[2]);
    		   RegistrySendsNodeManifest nManifest = new RegistrySendsNodeManifest(ip1, p1, ip2, p2, ip3, p3, u1, u2, u3, b);
    		   
    		   //s.sender.sendData(nManifest.getMyBytes());
    		   //System.out.println("Send karne ke pehle");
    		   s.getSender().sendData(nManifest.getMyBytes());
    		  
    		   /*TCPSender sender= new TCPSender(sock);
    		   sender.sendData(nManifest.getMyBytes());*/
		}
    	   
	
		
	}
	//*****************************************************************************************************************************************************************
	public void createRoutingInfoFor4(HashMap<Integer, String> original,
			HashMap<Integer, Connection> connectionStored, int overlaysize) throws IOException {
		
		//sort the received Map
		Map<Integer, String> sortedMap = new TreeMap<Integer, String>(original);
//		Map<Integer, //V>
		
		HashMap<Integer , Connection> connect = connectionStored;
		ArrayList<Integer> list = new ArrayList<Integer>();
		Map<String, Integer> reverse = new HashMap<String, Integer>();			//to get unique ID from ip:port
		Set set = sortedMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
              Map.Entry me = (Map.Entry)iterator.next();
              /*System.out.print(me.getKey() + ": ");
              System.out.println(me.getValue());*/
              
              list.add((Integer) me.getKey());
              reverse.put((String)me.getValue(),(int) me.getKey());
        }
        ArrayList<Integer> dummy = new ArrayList<Integer>(list);
        ArrayList<Integer> backup = new ArrayList<Integer>(list);
        ArrayList<Integer> x = new ArrayList<Integer>(list);
        /*for (int i = x.size(); i < (2*list.size()); i++) {
        	x.add(list.get(i)); 			
			
		}*/
        ArrayList<String> routing_info = new ArrayList<String>();
        backup14 = backup;
        Object[] b = backup.toArray();
        setBackup1(backup14);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int length =4;
        /*for(int i=0;i<(list.size()-length -1);){
        //for(int i=0;i<2;){	
			//newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+1))%(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+2))%(Math.pow(2, length)))));
			routing_info.add(""+x.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+"-"+x.get(((int)Math.pow(2, i+1))%((int)(Math.pow(2, length))))+"-"+x.get(((int)Math.pow(2, i+2))%((int)(Math.pow(2, length))))+"-"+x.get(((int)Math.pow(2, i+3))%((int)(Math.pow(2, length)))));
			temp.add(list.get(i));
			list.remove(i);
			i=0;
		}
		list.addAll(temp);
		System.out.println("List Size     ----------------------------------------------" + list.size()+" "+(list.size()-routing_info.size()));
		int c = 0;
		for(int i=0;i<(list.size()-routing_info.size()+c);){
			
			//newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+1))%(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+2))%(Math.pow(2, length)))));
			routing_info.add(""+list.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+1))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+2))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+3))%((int)(Math.pow(2, length)))));
			temp.add(list.get(i));
			list.remove(i);
			c++;
			i=0;
		}*/
        
        for (int i = 0; i < dummy.size(); i++) {
        	String route = "";
			//routing_info.add()
        	
        	for (int j = 0; j < overlaysize; j++) {
				route+= dummy.get((int) ((i+Math.pow(2, j))%dummy.size()))+"-";
			}
        	routing_info.add(route);
        	
		}
		/*for (int i = 0; i < routing_info.size(); i++) {
			System.out.println(backup.get(i)+"-->"+routing_info.get(i));
		}*/
		
        int[] li = new int[sortedMap.size()-1];
        
        
    		// TODO Auto-generated method stub
        	for (int i = 0; i < sortedMap.size(); i++) {
        		//System.out.println(sorted.get(l.containsKey(Integer.parseInt(routing_info.get(i).split("-")[0]))));
            	   //System.out.println();
            	   //System.out.println("Printing the Routing Information :");
            	   display.add("For Key :"+backup.get(i)+" "+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1])))+" "+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[3])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[3])));
            	   //System.out.println("For Key :"+backup.get(i)+" "+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1])))+" "+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2]))+"-->"+reverse.get(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[3])))+"|"+sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[3])));
            	 //  System.out.println(sorted.get(i));
               }
    	
       
    	   //bhejne ka yah pe kar raha hu
    	   for (int i = 0; i < sortedMap.size(); i++) {
    		   int key = backup.get(i);
    		   	
    		   	
    		   //String senderIP = sortedMap.get(key).split(":")[0];
    		   //int senderP  = Integer.parseInt(sortedMap.get(key).split(":")[1]);
    		   //Socket sock = new Socket(senderIP, senderP);
    		   Connection s = connect.get(key);
    		   //System.out.println(s);
    		   
    		   String ip1 = sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0])).split(":")[0];
    		   String ip2 = sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1])).split(":")[0];
    		   String ip3 = sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])).split(":")[0];
    		   String ip4 = sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[3])).split(":")[0];
    		   int p1 = Integer.parseInt(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[0])).split(":")[1]);
    		   int p2 = Integer.parseInt(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[1])).split(":")[1]);
    		   int p3 = Integer.parseInt(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[2])).split(":")[1]);
    		   int p4 = Integer.parseInt(sortedMap.get(Integer.parseInt(routing_info.get(i).split("-")[3])).split(":")[1]);
    		   int u1 = Integer.parseInt(routing_info.get(i).split("-")[0]);
    		   int u2 = Integer.parseInt(routing_info.get(i).split("-")[1]);
    		   int u3 = Integer.parseInt(routing_info.get(i).split("-")[2]);
    		   int u4 = Integer.parseInt(routing_info.get(i).split("-")[3]);
    		   RegistrySendsNodeManifest nManifest = new RegistrySendsNodeManifest(ip1, p1, ip2, p2, ip3, p3,ip4,p4, u1, u2, u3,u4, b);
    		   
    		   //s.sender.sendData(nManifest.getMyBytes());
    		   //System.out.println("Send karne ke pehle");
    		   s.getSender().sendData(nManifest.getMyBytes4());
    		 
		}
    	   
	
		
	}
	
	public void displayRoutingInfo() {
		// TODO Auto-generated method stub
		System.out.println("Routing Information......");
		System.out.println();
		for (int i = 0; i < display.size(); i++) {
			System.out.println(display.get(i));
		}
		
	}

}
