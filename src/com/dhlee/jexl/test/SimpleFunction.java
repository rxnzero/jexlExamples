package com.dhlee.jexl.test;

public class SimpleFunction {

	public SimpleFunction() {
		// TODO Auto-generated constructor stub
	}
	
	public static String concat(String str1, String str2) throws Exception{
		if(str1 == null || str2 == null) throw new Exception("Parameter is null.");
		return str1 + str2;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
