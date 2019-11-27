package com.vmlens.tutorialThreadSafe;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;


public class TestQuiescent {
	
	@Test
	public void testSize() throws InterruptedException {
		ConcurrentHashMap<Integer,Integer>  map = new  ConcurrentHashMap<Integer,Integer>();
		Thread first = new Thread( () ->    { map.put(1,1);  } ) ;
		Thread second = new Thread( () ->   { map.put(2,2);  } ) ;
		first.start();
		second.start();
		first.join();
		second.join();	
		assertEquals( 2 ,  map.size());
	}
	
}
