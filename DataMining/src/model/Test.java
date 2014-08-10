package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args){
		Date date = new Date(); // your date
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);

		System.out.println(year+" "+month);
		
		String[] a = null;
		String s = "Sr Technical Recruiterat at Antra Inc";
		a = s.split(" at ");
		for (String b: a) {
			System.out.println(b);
		}
	}

}
