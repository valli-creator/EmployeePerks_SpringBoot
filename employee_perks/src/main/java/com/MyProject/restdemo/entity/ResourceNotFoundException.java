package com.MyProject.restdemo.entity;

public class ResourceNotFoundException  extends Exception{

private String message;
	
	public ResourceNotFoundException(String message) {
		super("already exists");
	}
}
