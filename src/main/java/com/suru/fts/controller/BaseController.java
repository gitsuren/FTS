package com.suru.fts.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

	private static final String USER_HEADER = "SM_USER";
	
	public String getUserId(final HttpServletRequest request) {
		String user = request.getHeader(USER_HEADER);
		if (user == null) {
			return "TOGGLE_SYSTEM";
		} else {
			return user;
		}
	}
	protected ModelAndView redirect(final String to) {
		return new ModelAndView("redirect:" + to);
	}
}
