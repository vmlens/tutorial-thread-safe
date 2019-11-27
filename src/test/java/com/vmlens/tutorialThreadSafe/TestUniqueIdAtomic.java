package com.vmlens.tutorialThreadSafe;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.vmlens.api.AllInterleavings;

public class TestUniqueIdAtomic {

	private final UniqueIdAtomic uniqueId = new UniqueIdAtomic();
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
			    new AllInterleavings("TestUniqueIdAtomic");) {
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
