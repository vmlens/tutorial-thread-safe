package com.vmlens.tutorialThreadSafe;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.vmlens.api.AllInterleavings;

public class TestUpdateSingleAtomicMethod {
	


	public void update(ConcurrentHashMap<Integer,Integer>  map) {
			map.compute(1, (key, value) -> {
				if (value == null) {
					return 1;
				} 
				return value + 1;
			});
	}
	@Test
	public void testUpdate() throws InterruptedException	{
		
		try (AllInterleavings allInterleavings = 
			    new AllInterleavings("TestUpdateSingleAtomicMethod");) {
			while(allInterleavings.hasNext()) {		
		final ConcurrentHashMap<Integer,Integer>  map = new  ConcurrentHashMap<Integer,Integer>();	
			
		Thread first = new Thread( () ->   { update(map);  } ) ;
		Thread second = new Thread( () ->  { update(map);  } ) ;
		// start the threads
		first.start();
		second.start();
		// Wait till both threads stopped
		first.join();
		second.join();	
		// check the result
		assertEquals( 2 , map.get(1).intValue() );
		}
		}
	}
}
