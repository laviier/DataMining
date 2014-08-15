package dao;
import java.awt.List;
import java.text.*;
import java.util.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


public class DS_Test {
	
	private static String usingDateFormatterWithTimeZone(long input){
		Date date = new Date(input);
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sdf.setCalendar(cal);
		cal.setTime(date);
		return sdf.format(date);

	}
	
	public static void main(String[] args) {
		DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();
		String jtdate = "2014-08-15T18:51:29+0000";
		Date timeConvert = parser.parseDateTime(jtdate).toDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(timeConvert);
		System.out.print(time);
		
		Queue<Integer> q = new LinkedList<Integer>();
		//get,do not remove head; null if empty
		q.peek();
		//get,remove head; null if empty
		q.poll();
		//add 
		q.offer(1);
		q.isEmpty();
		q.size();
		
		Stack<Integer> s = new Stack<Integer>();
		s.push(1); s.add(1);
		s.empty();
		//get,do not remove head; null if empty
		s.peek();
		//get,remove head; null if empty
		s.pop();
		s.size();
		
		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a.add(1);
		a.addAll(a2);
		a.isEmpty();
		
		HashMap<String,Integer> h = new HashMap<String,Integer>();
		h.put("key", 1);
		h.get("key");
		
		int[] i = new int[2];

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
 	}
}
