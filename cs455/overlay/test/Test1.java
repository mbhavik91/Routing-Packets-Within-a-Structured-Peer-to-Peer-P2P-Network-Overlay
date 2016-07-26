/**
 * 
 */
package cs455.overlay.test;
//
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
/**
 * @author mbhavik
 *
 */
public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<Integer, String> l = new HashMap<Integer, String>();
		//Map<Integer, String> temp = new HashMap<Integer, String>();
		//Map<Integer, String> routing_info = new HashMap<Integer, String>();
		l.put(1, "10.2.2.3:1234-");
		l.put(7, "89.2.2.9:1278-");
		l.put(10,"130.2.2.4:1235-");
		l.put(9, "51.2.2.23:1278-");
		l.put(3, "51.2.2.23:1231-");
		l.put(6, "56.2.2.2:1234-");
		l.put(5, "78.2.2.23:1237-");
		l.put(4, "81.2.2.12:1233-");
		l.put(8, "90.2.2.98:12787-");
		l.put(2, "11.2.2.89:12390-");
		// create a sorted hashmap
		int i = 0;
		Map<Integer, String> sorted = new TreeMap<Integer, String>(l);
		int lenght = 4;
		int newlistsize = l.size()-lenght -1;
		Map<Integer, String> newlist = new HashMap<Integer, String>(newlistsize);
		for (Entry<Integer, String> entry : l.entrySet()) {
			//newlist.put(entry.getKey(), entry.setValue(entry.getValue(((int)Math.pow(2, i))%((int)(Math.pow(2, lenght))))));
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		
		
		
		// Display the HashMap
		Set set = sorted.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
              Map.Entry me = (Map.Entry)iterator.next();
              System.out.print(me.getKey() + ": ");
              System.out.println(me.getValue());
        }
	
}
}
