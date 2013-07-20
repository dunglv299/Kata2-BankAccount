package com.qsoft.bankaccount.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date;

		try {
			date = (Date) formatter.parse("12-11-2013");
			long mills = date.getTime();
			System.out.println(mills);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long tmp = 1352653200000l; // "12-11-2012"
		// 1384189200000l // 12-11-2013
		Date d = new Date(tmp);
		System.out.println(d);

		List<String> aList = new ArrayList<String>();
		aList.add("1");
		aList.add("2");

		List<String> bList = new ArrayList<String>();
		bList.add("1");

		System.out.println(aList.equals(bList));
	}
}
