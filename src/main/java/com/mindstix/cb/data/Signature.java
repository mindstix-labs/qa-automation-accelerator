/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.data;

import java.util.List;

/**
 * Class represents Signatures
 * 
 * @author Mindstix
 */
public class Signature {

	private List<SignatureCoordinates> signatures;
	
	public List<SignatureCoordinates> getSignatures(){
		return signatures;
	}
	
	public void setSignatures(List<SignatureCoordinates> signatures) {
		this.signatures = signatures;
	}
}
