package com.example.teststuff;

public class Customer {

	private static String username;
	private static String password;
	private static String email;
	
	public Customer(String username, String password, String email){
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public void changeUsername(String username){
		this.username = username;
	}
	
	public void changePassword(String password){
		this.password = password;
	}
	
	public void ChangeEmail(String email){
		this.email = email;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getEmail(){
		return this.email;
	}
}
