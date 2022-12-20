package com.dhlee.jexl.test;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

public class JexlFunctionTest {

	public JexlFunctionTest() {
		// TODO Auto-generated constructor stub
	}
	
	private void testSimpleMath(int x) {
		String exp = "1 + 2 + x";
		JexlEngine engine = new JexlBuilder().cache(100).create();
		JexlContext jc = new MapContext();
		jc.set("x", x);
		JexlExpression e = engine.createExpression(exp);
	    Object result = e.evaluate(jc);
	    System.out.println(String.format("%s = %s", exp,result) );
	}
	
	
	private void testSimpleUserFunction(String s1, String s2) {
		String exp = "uf.concat(message.group.field1, message.group.field2)";
		JexlEngine engine = new JexlBuilder().cache(100).create();
		JexlContext jc = new MapContext();
		
		jc.set("uf", new SimpleFunction());
		
		jc.set("message.group.field1", s1);
		jc.set("message.group.field2", s2);
		
		JexlExpression e = engine.createExpression(exp);
	    Object result = e.evaluate(jc);
	    System.out.println(String.format("%s = %s", exp,result) );	    
	}
	
	public static void main(String[] args) {
		JexlFunctionTest example = new JexlFunctionTest();
		example.testSimpleMath(10);
		
		example.testSimpleUserFunction("add1", "more2");
	}

}
