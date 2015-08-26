package com.suru.fts.controller;

import static com.suru.fts.routes.Routes.FEATURE_ADD_URI;
import static com.suru.fts.routes.Routes.FEATURE_DELETE_URI;
import static com.suru.fts.routes.Routes.FEATURE_URI;

import javax.servlet.http.HttpServletRequest;

import com.suru.fts.dto.FeatureFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.service.ToggleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FeatureController extends BaseController {

	@Autowired
	private ToggleService toggleService;

	@Autowired
	private ViewModelBuilder modelBuilder;


	@RequestMapping(value = FEATURE_URI, method = RequestMethod.GET)
	public ModelAndView getFeatures(@PathVariable(value = "systemName") final String systemName, @PathVariable(value = "featureName") final String featureName) {

		ToggleSystem toggleSystem = toggleService.getSystem(systemName);
		Feature feature = toggleSystem.getFeatureByName(featureName);
		return modelBuilder.buildFeatureModel(feature);
	}


	@RequestMapping(value = FEATURE_URI, method = RequestMethod.POST)
	public ModelAndView updateFeature(@PathVariable(value = "systemName") final String systemName, @ModelAttribute final FeatureFormBean featureBean, final HttpServletRequest request) {

		ToggleSystem system = toggleService.getSystem(systemName);
		toggleService.updateFeature(system, featureBean, getUserId(request));
		return new ModelAndView("redirect:/admin/system/" + systemName);
	}


	@RequestMapping(value = FEATURE_ADD_URI, method = RequestMethod.GET)
	public ModelAndView addFeature(@PathVariable(value = "systemName") final String systemName) {
		return modelBuilder.buildFeatureCreateModel(systemName);
	}


	@RequestMapping(value = FEATURE_ADD_URI, method = RequestMethod.POST)
	public ModelAndView addFeature(@PathVariable(value = "systemName") final String systemName, @ModelAttribute final FeatureFormBean featureBean, final HttpServletRequest request) {

		ToggleSystem system = toggleService.getSystem(systemName);
		toggleService.createFeature(system, featureBean, getUserId(request));
		return new ModelAndView("redirect:/admin/system/" + systemName);
	}


	@RequestMapping(value = FEATURE_DELETE_URI, method = RequestMethod.GET)
	public ModelAndView deleteFeature(@PathVariable(value = "systemName") final String systemName, @PathVariable(value = "featureName") final String featureName) {

		ToggleSystem system = toggleService.getSystem(systemName);
		Feature feature = system.getFeatureByName(featureName);
		system = toggleService.deleteFeature(systemName, featureName);
		//toggleService.deleteFeature(feature);
		return modelBuilder.buildSystemModel(system);
	}
}
