package com.dhlee.rhino.test;

import java.io.FileReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

public class JavaScriptExample {

	public JavaScriptExample() {

	}
	
	private static void testScript() {
		ScriptEngineManager mgr = new ScriptEngineManager();
        // Now we can go and get a script engine we want. 
        // This can be done either by finding a factory that supports 
        // our required scripting language 
        // (engine = factory.getScriptEngine();)
        // or by requesting a script engine that supports a 
        // given language by name from the script engine manager.
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
 
        // Now we have a script engine instance that 
        // can execute some JavaScript
         
        try {
            String script = "var mdef;";
            script += "var bGlowing = false;";
            script += "var defPlus = 0;";
            script += "if (mdef < 50) {";
            script += "    bGlowing = false;";
            script += "    defPlus = 25;";
            script += "} else {";
            script += "    bGlowing = true;";
            script += "    defPlus = -25;";
            script += "}";
            engine.put("mdef", 50);
             
            engine.eval(script);
             
            System.out.println(engine.get("bGlowing"));
            System.out.println(engine.get("defPlus"));
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
	}
	
	private static void testFunctionScript() {
		ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
 
        try {
            String script = "function concat(x,y) {";
            script += " return x + ' ' + y;";
            script += "}";
            script += "res = concat(x, y);";
            engine.put("x", "hello");
            engine.put("y", "javascript"); 
            
            Object res = engine.eval(script);
            System.out.println(script + "\n => " + engine.get("res"));                        
            System.out.println(script + "\n => " + res);
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
	}
	
	private static void testSimpleScript() {
		ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
 
        try {
            String script = "x + ' ' + y;";
            engine.put("x", "hello");
            engine.put("y", "javascript");            
            Object res = engine.eval(script);            
            System.out.println(script +"\n => " +res);            
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
	}
	
	private static void testContext() {
		String script = "Number(채결정보.체결1초봉.PCI정보.PCI수치) == 20;";
		
		HashMap var1 =  new HashMap<>();
		var1.put("PCI수치", "20");
		
		HashMap var2 =  new HashMap<>();
		var2.put("PCI정보", var1);
		
		HashMap var3 =  new HashMap<>();
		var3.put("체결1초봉", var2);
		
		HashMap var4 =  new HashMap<>();
		var4.put("채결정보", var3);
		
		
		ScriptEngine sEngine = new ScriptEngineManager().getEngineByName("js");
		ScriptContext sContext = new SimpleScriptContext();
		Bindings bind = sContext.getBindings(ScriptContext.ENGINE_SCOPE);
		 
		bind.putAll(var4);
		
		StringWriter sw = new StringWriter();
		sContext.setWriter(sw);

		try {
			Object obj = sEngine.eval(script, sContext);
			
			System.out.println("Standard Output : " + sw.toString());
			System.out.println("Script Output : " + obj.toString());
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	// deprecated in JDK 11 as part of JEP 335 and has been removed from JDK15 as part of JEP 372.
	private static void testNashorn() throws Exception {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		engine.eval(new FileReader("./js/example.js"));
		// cast the script engine to an invocable instance
		Invocable invocable = (Invocable) engine;
		Object result = invocable.invokeFunction("sayHello", "rxnzero");
		System.out.println(result);
		System.out.println(result.getClass());
	}	

	public static void main(String[] args) {
		testFunctionScript();
		testSimpleScript();
		testContext();
		try {
			testNashorn();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
    }
}
