package com.stoica.onetwo.api.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/resource")
public class Resource {

	@GetMapping
	public String get () {
		return "Get";
	}
	
	@GetMapping("/admin")
	public String getAdm () {
		return "Get ADMIN";
	}
	
	@GetMapping("/user")
	public String getUser () {
		return "Get User";
	}
}
