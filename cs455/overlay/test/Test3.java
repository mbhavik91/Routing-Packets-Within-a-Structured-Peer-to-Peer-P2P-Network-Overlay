/**
 * 
 */
package cs455.overlay.test;
//
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author mbhavik
 *
 */
public class Test3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<Integer, String> l = new HashMap<Integer, String>();
		l.put(1, "10.2.2.3:1234-");
		l.put(70, "89.2.2.9:1278-");
		l.put(10,"130.2.2.4:1235-");
		l.put(9, "51.2.2.23:1278-");
		l.put(30, "51.2.2.23:1231-");
		l.put(64, "56.2.2.2:1234-");
		l.put(53, "78.2.2.23:1237-");
		l.put(49, "81.2.2.12:1233-");
		l.put(87, "90.2.2.98:12787-");
		l.put(23, "11.2.2.89:12390-");
		Map<Integer, String> sorted = new TreeMap<Integer, String>(l);
	}

}
