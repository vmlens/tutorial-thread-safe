package com.vmlens.tutorialThreadSafe;

public class UniqueIdAtomic {
	private   long counter = 0;
	public synchronized long nextId() {	
		return counter++;	
	}
}
