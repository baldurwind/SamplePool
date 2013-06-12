package com.acn.powermock;

import static org.powermock.api.support.membermodification.MemberMatcher.method;
import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.acn.ezmock.SystemPropertyMockDemo;



@RunWith(PowerMockRunner.class) 
@SuppressStaticInitializationFor({"UtilityClass.class"})
@PrepareForTest({SystemPropertyMockDemo.class,UtilityClass.class})     
public class PowerMockTest {     
	  
   @Test    
    public void testStaticMethod() throws Exception {     
        PowerMock.mockStatic(System.class);     
        EasyMock.expect(System.getProperty(EasyMock.isA(String.class))).andReturn("my property");     
        PowerMock.replayAll();     
        Assert.assertEquals("my property", new SystemPropertyMockDemo().getSystemProperty());     
        PowerMock.verifyAll();     
    }    
     @Test
    public void testPrivateArgsMethod() {
    	String methodName="privateArgsMethod";
   	 UtilityClass utilityClass=PowerMock.createPartialMock(UtilityClass.class,methodName);
   	 String param="I'm a girl";
   	 String result="I'm a boy";
    	try {
		 	PowerMock.expectPrivate(utilityClass, methodName, EasyMock.isA(String.class)).andReturn(result);
		 	PowerMock.replay(utilityClass);
		 	Assert.assertFalse(param.equals(utilityClass.exposePrivateArgsMethod(param)) );
		  PowerMock.verify(utilityClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }   	 
    @Test
    public void testPrivateStaticArgsMethod() {
    	String methodName="privateStaticArgsMethod";
    	PowerMock.mockStaticPartial(UtilityClass.class,methodName);
    	UtilityClass utilityClass=PowerMock.createPartialMock(UtilityClass.class,methodName);
    	//PowerMock.mockStaticPartial(UtilityClass.class, "displayBoy");
    	String param="I'm a girl";
    	String result="I'm a boy";
    	try {
		 	PowerMock.expectPrivate(utilityClass, methodName, EasyMock.isA(String.class)).andReturn(result);
		 	PowerMock.replay(UtilityClass.class);
			Assert.assertFalse(param.equals(utilityClass.exposePrivateStaticArgsMethod(param)) );
		  PowerMock.verify(UtilityClass.class);
		  
		  
		} catch (Exception e) {
			e.printStackTrace();
		}
    }    
    
    @Test
    public void testPlusOneToStub(){
    	String methodName="plusOneToStub";
    	String param="1";
    	UtilityClass utilityClass= PowerMock.createMock(UtilityClass.class);
    	PowerMock.stub(method(UtilityClass.class, methodName,String.class)).toReturn("1wow");
    	PowerMock.replayAll();
    	System.out.println(utilityClass.plusOneToStub("1wow"));
    	
    	
    	
    	
    }
    
}