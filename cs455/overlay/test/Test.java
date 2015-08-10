package cs455.overlay.test;

import java.util.ArrayList;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int length = 3;
		ArrayList<String> l = new ArrayList<String>();
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList<String> newlist = new ArrayList<String>();
		l.add("1|10.2.2.3:1234-");
		l.add("2|10.2.2.4:1235-");
		l.add("3|10.2.2.23:1231-");
		l.add("4|10.2.2.12:1233-");
		l.add("5|10.2.2.23:1237-");
		l.add("6|10.2.2.2:1234-");
		l.add("7|10.2.2.9:1278-");
		l.add("8|10.2.2.98:12787-");
		l.add("9|10.2.2.23:1278-");
		l.add("10|10.2.2.89:12390-");
		l.add("11|10.2.1.1:140-");
		
		
		for(int i=0;i<2;){
			
			//newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+1))%(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+2))%(Math.pow(2, length)))));
			newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get(((int)Math.pow(2, i+1))%((int)(Math.pow(2, length))))+l.get(((int)Math.pow(2, i+2))%((int)(Math.pow(2, length)))));
			temp.add(l.get(i));
			l.remove(i);
			i=0;
		}
		l.addAll(temp);
		
		for(int i=0;i<(l.size()-newlist.size()+3);){
			
			//newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+1))%(Math.pow(2, length))))+l.get((int)(( Math.pow(2, i+2))%(Math.pow(2, length)))));
			newlist.add(l.get(((int)Math.pow(2, i))%((int)(Math.pow(2, length))))+l.get(((int)Math.pow(2, i+1))%((int)(Math.pow(2, length))))+l.get(((int)Math.pow(2, i+2))%((int)(Math.pow(2, length)))));
			temp.add(l.get(i));
			l.remove(i);
			i=0;
		}
		
		
		
		System.out.println(newlist.size());
		
		for (int i = 0; i < newlist.size(); i++) {
			System.out.println(i+1+ " ---> "+newlist.get(i));
		}	
	}

}
