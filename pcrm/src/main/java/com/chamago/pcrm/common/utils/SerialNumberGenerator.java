package com.chamago.pcrm.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SerialNumberGenerator {
	private  char[] src = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o',
		'p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0'};
	private String serialNumber;
	
	public Color getRandColor(int fc,int bc){//给定范围获得随机颜色
	   java.util.Random random = new java.util.Random();
	   if(fc>255) fc=255;
	   if(bc>255) bc=255;
	   int r=fc+random.nextInt(bc-fc);
	   int g=fc+random.nextInt(bc-fc);
	   int b=fc+random.nextInt(bc-fc);
	   return new Color(r,g,b);
	}
	
	public  BufferedImage randomAlphanumeric(int size){
		char[] c = new char[size];
		java.util.Random random = new java.util.Random();
		int width=70, height=25;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        //以下填充背景颜色
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(1, 1, width, height);
        //设置干扰线
        g.setColor(getRandColor(160,200));
        for (int i=0;i<100;i++)
        {
	      int x = random.nextInt(width);
	      int y = random.nextInt(height);
	      int xl = random.nextInt(12);
	      int yl = random.nextInt(12);
	      g.drawLine(x,y,x+xl,y+yl);
        }
        g.setFont(new java.awt.Font("Times New Roman",java.awt.Font.BOLD,20));
		int len = src.length;
		for(int i=0;i<size;i++){
			int index= random.nextInt(len);
			c[i] = src[index];
			 //设置字体颜色
			g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
			g.drawString(String.valueOf(src[index]),15*i+6,18);
			src[index] = src[len-1];
			len--;
		}
		setSerialNumber(new String(c));
        g.dispose();
        
		return image;
	}
	public String getSerialNumber(){
		return serialNumber;
	}
	private void setSerialNumber(String sn){
		serialNumber = sn;
	}
	public static void main(String[] args){
		System.out.println(new SerialNumberGenerator().randomAlphanumeric(10));	
	}
}
