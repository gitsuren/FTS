package com.suru.fts.controller;

import static com.suru.fts.routes.Routes.SYSTEM_FEATURES_FOR_USER_URI;
import static com.suru.fts.routes.Routes.TOGGLE_SYSTEM_HOME_URI;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.service.ToggleService;


@Controller
public class FeatureToggleHomeController {

	@Autowired
	private ToggleService toggleService;
	
	@Autowired
	private ViewModelBuilder viewModelBuilder;
	
	@RequestMapping(value = TOGGLE_SYSTEM_HOME_URI, method = RequestMethod.GET)
	public ModelAndView defaultMethod() {
		
		List<ToggleSystem> systems = toggleService.getActiveSystems();
		List<FeatureGroup> groups = toggleService.getActiveGroups();
		return viewModelBuilder.buildHomeModel(systems, groups);
	}
	
	@RequestMapping(value = SYSTEM_FEATURES_FOR_USER_URI, method = RequestMethod.GET)
	public ModelAndView showPageToGetFeaturesForUserId() {
		List<ToggleSystem> systems = toggleService.getActiveSystems();
		return viewModelBuilder.buildFeaturesForUserPage(systems);
	}
	
	@RequestMapping(value = SYSTEM_FEATURES_FOR_USER_URI, method = RequestMethod.POST)
	public ModelAndView getFeaturesForUserId(@RequestParam final String systemName, @RequestParam final String userId) {
		Set<String> featureNames =  toggleService.getFeaturesForId(systemName, userId);
		List<ToggleSystem> systems = toggleService.getActiveSystems();
		return viewModelBuilder.buildSystemFeaturesForUserModel(systemName, userId, featureNames, systems);
	}
}
