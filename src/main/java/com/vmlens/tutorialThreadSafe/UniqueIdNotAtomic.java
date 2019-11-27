package com.vmlens.tutorialThreadSafe;

public class UniqueIdNotAtomic {
	private volatile long counter = 0;
	public  long nextId() {	
		return counter++;	
	}
}
