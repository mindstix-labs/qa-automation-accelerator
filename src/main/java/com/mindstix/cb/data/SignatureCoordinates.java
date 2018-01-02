/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.data;

import java.awt.Point;
import java.util.List;

/**
 * Class represents Signature Coordinates
 * 
 * @author Mindstix
 */
public class SignatureCoordinates {

	private String signatureID;
	private List<Point> coordinates;
	
	public String getSignatureID() {
		return this.signatureID;
	}
	
	public void setSignatureID(String signtureID) {
		this.signatureID = signtureID;
	}
	
	public List<Point> getCoordinates(){
		return coordinates;
	}
	
	public void setCoordinates(List<Point> coordinates) {
		this.coordinates = coordinates;
	}
}
