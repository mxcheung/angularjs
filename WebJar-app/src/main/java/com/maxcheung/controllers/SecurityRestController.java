package com.maxcheung.controllers;

import java.security.Principal;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
public class SecurityRestController {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityRestController.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }
 
	@RequestMapping(method = RequestMethod.GET, path = "/user")
	public User getUser() {
		LOG.info("Get User");
		User maker = new User(
				"maker", "dummy", Collections.emptyList());

		return maker;
	}



	@RequestMapping(method = RequestMethod.GET, path = "/user2")
	public User getUser2(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			LOG.info("Get User : {}", principal.getName());
		}
		User maker = new User(
				"maker", "dummy", Collections.emptyList());

		return maker;
	}



}