package com.vmlens.tutorialThreadSafe;

import static org.junit.Assert.*;
import org.junit.Test;

import com.vmlens.api.AllInterleavings;
import com.vmlens.tutorialThreadSafe.UniqueIdNotAtomic;

public class TestUniqueIdNotAtomic {
	private final UniqueIdNotAtomic uniqueId = new UniqueIdNotAtomic();
	private long firstId;
	private long secondId;
	private void updateFirstId() {
		firstId  = uniqueId.nextId();
	}

	private void updateSecondId() {
		secondId = uniqueId.nextId();
	}
	
	
	@Test
	public void testUniqueId() throws InterruptedException {
		
		try (AllInterleavings allInterleavings = 
			    new AllInterleavings("TestUniqueIdNotAtomic");) {
		while(allInterleavings.hasNext()) {	
		// Use two threads to call the method under test.
		Thread first = new Thread( () ->   { updateFirstId();  } ) ;
		Thread second = new Thread( () ->  { updateSecondId();  } ) ;
		// start the threads
		first.start();
		second.start();
		// Wait till both threads stopped
		first.join();
		second.join();	
		// check the result
		assertTrue(  firstId != secondId );
		}
		}
	}

}
