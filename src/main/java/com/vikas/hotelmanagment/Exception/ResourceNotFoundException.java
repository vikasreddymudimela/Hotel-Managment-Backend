package com.vikas.hotelmanagment.Exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
public  ResourceNotFoundException(String message) {
	super(message);
}
}
