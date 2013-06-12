package com.acn.powermock;

import java.util.List;

public class UtilityClass {

	   
	   public static String publicStaticArgsMethod(String arg) {
	           return arg;
	   }
	   
	   private static String  privateStaticArgsMethod(String arg) {
           return arg;
	   }
	   
	    
	    private  String  privateArgsMethod(String str){
	    	return str;
	    }
	    
	    
	    public  String exposePrivateArgsMethod(String str){
	    	return privateArgsMethod(str);
	    }
	    public  static String exposePrivateStaticArgsMethod(String str){
	    	return privateStaticArgsMethod(str);
	    }
	    
	    public String plusOneToStub(String i){
	    	i=i+"wow";
	    	System.out.println(":"+i);
	    	return i;
	    }
	    public String invokePlusOneToStub(String i){
	    	return plusOneToStub(i);
	    }
	}