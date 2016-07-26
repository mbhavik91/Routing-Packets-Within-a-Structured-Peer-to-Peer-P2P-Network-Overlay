/**
 * 
 */
package cs455.overlay.test;
//
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

//import javax.swing.text.html.HTMLDocument.Iterator;

/**
 * @author mbhavik
 *
 */
public class Test2 {

	public static void main(String[] args) {
		Map<Integer, String> l = new HashMap<Integer, String>();
		l.put(1, "10.2.2.3:1234-");
		l.put(2, "89.2.2.9:1278-");
		l.put(3,"130.2.2.4:1235-");
		l.put(4, "51.2.2.23:1278-");
		l.put(5, "51.2.2.23:1231-");
		l.put(6, "56.2.2.2:1234-");
		l.put(7, "458.2.2.23:1237-");
		l.put(8, "41.2.2.12:1233-");
		l.put(9, "90.2.2.98:12787-");
		l.put(10, "99.2.2.89:12390-");
		//sortedHM
		Map<Integer, String> sorted = new TreeMap<Integer, String>(l);
		ArrayList<Integer> list = new ArrayList<Integer>();
		Map<String, Integer> ulta = new HashMap<String, Integer>();
		
		Set set = sorted.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
              Map.Entry me = (Map.Entry)iterator.next();
              /*System.out.print(me.getKey() + ": ");
              System.out.println(me.getValue());*/
              
              list.add((Integer) me.getKey());
              ulta.put((String)me.getValue(),(int) me.getKey());
        }
        ArrayList<Integer> backup = new ArrayList<Integer>(list);
        ArrayList<String> routing_info = new ArrayList<String>();
        
        
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int length =3;
		for(int i=0;i<(list.size()-length -1);){
			
			//newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+1))%(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+2))%(Math.pow(2, length)))));
			routing_info.add(""+list.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+1))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+2))%((int)(Math.pow(2, length)))));
			temp.add(list.get(i));
			list.remove(i);
			i=0;
		}
		list.addAll(temp);
		
		for(int i=0;i<(list.size()-routing_info.size()+3);){
			
			//newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+1))%(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+2))%(Math.pow(2, length)))));
			routing_info.add(""+list.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+1))%((int)(Math.pow(2, length))))+"-"+list.get(((int)Math.pow(2, i+2))%((int)(Math.pow(2, length)))));
			temp.add(list.get(i));
			list.remove(i);
			i=0;
		}
        
        
		//created a backed up list for future use
        
        for (int i = 0; i < routing_info.size(); i++) {
			System.out.println(backup.get(i)+"-->"+routing_info.get(i));
		}
        int k = Integer.parseInt(routing_info.get(0).split("-")[0]);
        /*Set<Entry<Integer, String>> s = l.entrySet();
        Iterator<Entry<Integer, String>> i = s.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            System.out.print(me.getKey());
            System.out.println(me.getValue());
            
      }*/
       for (int i = 0; i < sorted.size(); i++) {
		//System.out.println(sorted.get(l.containsKey(Integer.parseInt(routing_info.get(i).split("-")[0]))));
    	   System.out.println("Key :"+backup.get(i)+" Routing info is "+ulta.get(sorted.get(Integer.parseInt(routing_info.get(i).split("-")[0])))+"*"+sorted.get(Integer.parseInt(routing_info.get(i).split("-")[0]))+"-->"+sorted.get(Integer.parseInt(routing_info.get(i).split("-")[1]))+"-->"+sorted.get(Integer.parseInt(routing_info.get(i).split("-")[2])));
    	 //  System.out.println(sorted.get(i));
	} 
        /*HashMap<Integer, String> r = new HashMap<Integer, String>();
        for (int i = 0; i < sorted.size(); i++) {
			r.put(backup.get(i), sorted.get(Integer.parseInt(routing_info.get(i).split("-")[0]))+"-->"+sorted.get(Integer.parseInt(routing_info.get(i).split("-")[1]))+"-->"+sorted.get(Integer.parseInt(routing_info.get(i).split("-")[2])));
		}*/
        
		
	}

}
