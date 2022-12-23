package com.dhlee.jexl.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;

public class JexlFunctionTest {

	public JexlFunctionTest() {

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
	
	private void testJexlScript() {
		String exp = "user =^ 'dhl'";
		JexlEngine engine = new JexlBuilder().cache(100).create();
		JexlContext jc = new MapContext();
		jc.set("user", "dhlee");
		JexlScript expr = engine.createScript(exp);
	    Object result = expr.execute(jc);
	    System.out.println(String.format("%s = %s", exp,result) );
	}
	
	private void testSimpleConcat(boolean withCache, int cacheExpMaxLength, String word1, String word2) {
		String exp = "word1 + ' ' + word2";
		JexlEngine engine = null;
		if(withCache) {
			// important : cacheThreshold 보다 길이가 작을 경우에만 cache됨.
			engine = new JexlBuilder().cache(100).cacheThreshold(cacheExpMaxLength).create();			
		}
		else {
			engine = new JexlBuilder().create();
		}
		
		Object result = null;
		long t = System.currentTimeMillis();
		for(int i=0; i< 1000000; i++) {
			JexlContext jc = new MapContext();
			jc.set("word1", word1+i);
			jc.set("word2", word2+i);
//			JexlExpression e = engine.createExpression(exp);
//			result = e.evaluate(jc);
			
			JexlScript script = engine.createScript(exp);
			result = script.execute(jc);
			
		}
		System.out.println(String.format("%s = %s", exp,result) +  " " + (System.currentTimeMillis() - t) + "ms");
		
//	    System.out.println(String.format("%s = %s", exp,result) );
	}
	
	Map<String, Object> collectVars(JexlScript script, JexlContext context) {
	    Set<List<String>> sls = script.getVariables();
	    Map<String, Object> vars = new TreeMap<String, Object>();
	    for(List<String> ls : sls) {
	        // build the 'antish' name by concatenating
	        StringBuilder strb = new StringBuilder();
	        for(String s : ls) {
	            if (strb.length() > 0) {
	                strb.append('.');
	            }
	            strb.append(s);
	        }
	        String name = strb.toString();
	        vars.put(name, context.get(name));
	    }
	    return vars;
	}
	
	List<String> collectVars(JexlScript script) {
	    Set<List<String>> sls = script.getVariables();
	    List<String> vars = new ArrayList<String>();
	    for(List<String> ls : sls) {
	        // build the 'antish' name by concatenating
	        StringBuilder strb = new StringBuilder();
	        for(String s : ls) {
	            if (strb.length() > 0) {
	                strb.append('.');
	            }
	            strb.append(s);
	        }
	        String name = strb.toString();
	        vars.add(name);
	    }
	    return vars;
	}
	
	private void testSimpleNamespaceFunction(String s1, String s2) {
		String exp = "concat(message.group.field1, message.group.field2)";
		
		System.out.println("\n>> testSimpleNamespaceFunction exp : " + exp);
		
		// pre-defined functions
		Map<String, Object> funcs = new HashMap<String, Object>();
        
		// Top level function (null namespace function)
		funcs.put(null, new SimpleFunction());
		
		// use function name -> uf:concat(...)
//		funcs.put("uf", new SimpleFunction());
		
		JexlEngine engine = new JexlBuilder().silent(false).strict(true).safe(false).cache(10000).namespaces(funcs).create();
		
		JexlContext jc = new MapContext();
		
		JexlScript expr = engine.createScript(exp);
		List<String> vars = collectVars(expr);
		for(String varName:vars) {
			System.out.println("# Variables = " + varName);
			// find var and set to jc
		}
		
		jc.set("message.group.field1", s1);
		jc.set("message.group.field2", s2);

		Object result = expr.execute(jc);
	    System.out.println(String.format("%s = %s", exp,result) );	    
	}
	
	private void testSimpleUserFunction(String s1, String s2) {
		String exp = "uf.concat(message.group.field1, message.group.field2)";
		
		System.out.println("\n>> testSimpleUserFunction exp : " + exp);
		
		Map<String, Object> funcs = new HashMap<String, Object>();
        
		JexlEngine engine = new JexlBuilder().silent(false).strict(true).safe(false).cache(10000).namespaces(funcs).create();
		
		JexlContext jc = new MapContext();
		
		JexlScript expr = engine.createScript(exp);
		Map<String, Object> vars = collectVars(expr, jc);
		for(String varName:vars.keySet()) {
			System.out.println("# Variables = " + varName);
		}
		
		jc.set("uf", new SimpleFunction());
		jc.set("message.group.field1", s1);
		jc.set("message.group.field2", s2);
		
		JexlExpression e = engine.createExpression(exp);
		System.out.println("getSourceText = " + e.getSourceText());
		System.out.println("getParsedText = " + e.getParsedText());
		
	    Object result = e.evaluate(jc);
	    System.out.println(String.format("%s = %s", exp,result) );	    
	}
	
	public static void main(String[] args) {
		JexlFunctionTest example = new JexlFunctionTest();
 //		example.testSimpleMath(10);
//		example.testJexlScript();
//		example.testSimpleNamespaceFunction("add1", "more2");
//		example.testSimpleUserFunction("add1", "more2");

		// expression
//		word1 + ' ' + word2 = Hello999999 JEXL999999 594ms
//		word1 + ' ' + word2 = Hello999999 JEXL999999 13845ms
		// script
//		word1 + ' ' + word2 = Hello999999 JEXL999999 644ms
//		word1 + ' ' + word2 = Hello999999 JEXL999999 14354ms
		example.testSimpleConcat(true, 1024, "Hello", "JEXL");
		example.testSimpleConcat(false, 1024, "Hello", "JEXL");
		
		
	}

}
