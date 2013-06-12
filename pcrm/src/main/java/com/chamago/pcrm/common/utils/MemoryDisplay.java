package com.chamago.pcrm.common.utils;

public class MemoryDisplay {
	public static void main(String a[]){
		usedmemory();
		freememory();
		maxmemory();
		totalmemory();
	}
	public static String  usedmemory(){
		long i=Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		return  " Used Memory :"+i/1024/1024+"MB.Free Memory:"+freememory()+" MB";
	}
	public static long freememory(){
		long i=	Runtime.getRuntime().freeMemory();
		return i/1024/1024;
	}
	public static void maxmemory(){
		long i=	Runtime.getRuntime().totalMemory();
		System.out.println("Max Memory:"+i/1024/1024+"MB");
	}
	public static void totalmemory(){
		long i=	Runtime.getRuntime().totalMemory();
		System.out.println("Total Memory:"+i/1024/1024+"MB");
	}
}
