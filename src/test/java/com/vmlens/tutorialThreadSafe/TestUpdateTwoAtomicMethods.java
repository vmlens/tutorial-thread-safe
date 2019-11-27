package com.vmlens.tutorialThreadSafe;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;

import com.vmlens.api.AllInterleavings;


public class TestUpdateTwoAtomicMethods {
	public void update(ConcurrentHashMap<Integer,Integer>  map)  {
			Integer result = map.get(1);		
			if( result == null )  {
				map.put(1, 1);
			}
			else	{
				map.put(1, result + 1 );
			}	
	}
	@Test
	public void testUpdate() throws InterruptedException	{
		
		try (AllInterleavings allInterleavings = 
			    new AllInterleavings("TestUpdateTwoAtomicMethods");) {
		
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
