package com.suru.fts.controller;

import static com.suru.fts.routes.Routes.STRATEGY_URI_ADD;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.suru.fts.dto.StrategyFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.service.ToggleService;

@Controller
public class StrategyController extends BaseController {

	private static String STRATEGY_REDIRECT_ADD = "redirect:/admin/system/{0}/feature/{1}/strategy/{2}/{3}";

	@Autowired
	private ToggleService toggleService;

	@Autowired
	private ViewModelBuilder modelBuilder;

	@RequestMapping(value = STRATEGY_URI_ADD, method = RequestMethod.GET)
	public ModelAndView showCreateStrategyPage(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName) {

		return modelBuilder.buildStrategyCreateModel(systemName, featureName);
	}
	
	@RequestMapping(value = STRATEGY_URI_ADD, method = RequestMethod.POST)
	public ModelAndView addStrategy(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName, @ModelAttribute final StrategyFormBean strategyForm, final HttpServletRequest request) {

		ToggleSystem toggleSystem = toggleService.getSystem(systemName);
		Feature feature = toggleSystem.getFeatureByName(featureName);
		toggleService.createStrategy(toggleSystem, feature, strategyForm, getUserId(request));
		return new ModelAndView(MessageFormat.format(STRATEGY_REDIRECT_ADD, systemName, featureName, strategyForm.getStrategyType().toLowerCase(),
				strategyForm.getStrategyName()));
	}

}
