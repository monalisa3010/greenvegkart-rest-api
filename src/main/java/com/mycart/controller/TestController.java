/*package com.mycart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.StoredProc;

@RestController
public class TestController {

	@Autowired
	StoredProc storedProc;

	@GetMapping(value = "/test", produces = "application/json")
	public String testapi() {
		storedProc.registerUser();
		return "SUCCESS";
	}

}*/
