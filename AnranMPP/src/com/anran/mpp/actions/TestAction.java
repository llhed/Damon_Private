package com.anran.mpp.actions;

import com.anran.mpp.services.TestService;

public class TestAction {
	
	private TestService testServices;
	
	
	
	public void setTestServices(TestService testServices) {
		this.testServices = testServices;
	}



	public String execute(){
		int flag = testServices.test();
		return "success";
	}
	
	public String test(){
		return "success";
	}
}
