package com.chamago.pcrm.common.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@SuppressWarnings("serial")
public class BasePojo   implements Serializable {

	 
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	public String shorten(String str){
		return str.substring(0,5);
	}
}
