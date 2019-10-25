package com.util;

public class DatabaseContextHolder { 
	  
	  private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>(); 
	  
	  private static String customerTypeString ;
	  
	  public static void setCustomerType(String customerType) { 
	    //contextHolder.set(customerType); 
		 customerTypeString = customerType;
	  } 
	  
	  public static String getCustomerType() { 
	    //return contextHolder.get(); 
		 return customerTypeString;
	  } 
	  
	  public static void clearCustomerType() { 
	    contextHolder.remove(); 
	  } 
	}