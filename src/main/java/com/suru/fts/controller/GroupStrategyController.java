package com.suru.fts.controller;

import static com.suru.fts.routes.Routes.GROUP_STRATEGY_URI;
import static com.suru.fts.routes.Routes.GROUP_STRATEGY_URI_ADD;
import static com.suru.fts.routes.Routes.GROUP_STRATEGY_URI_ADD_FEATURE_GROUP;
import static com.suru.fts.routes.Routes.GROUP_STRATEGY_URI_DELETE;
import static com.suru.fts.routes.Routes.GROUP_STRATEGY_URI_REMOVE_FEATURE_GROUP;

import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.suru.fts.dto.FeatureGroupFormBean;
import com.suru.fts.dto.GroupStrategyFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.service.ToggleService;


@Controller
public class GroupStrategyController extends BaseController {

	private static String STRATEGY_REDIRECT = "redirect:/admin/system/{0}/feature/{1}/strategy/group/{2}";
	private static String FEATURE_REDIRECT = "redirect:/admin/system/{0}/feature/{1}";

	@Autowired
	private ToggleService toggleService;

	@Autowired
	private ViewModelBuilder modelBuilder;


	@RequestMapping(value = GROUP_STRATEGY_URI, method = RequestMethod.GET)
	public ModelAndView getGroupStrategy(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName, @PathVariable(value = "strategyName") final String strategyName) {

		ToggleSystem toggleSystem = toggleService.getSystem(systemName);
		GroupStrategy strategy = getStrategy(toggleSystem, featureName, strategyName);
		List<FeatureGroup> groups = toggleService.getActiveGroups();
		ModelAndView modelAndView = modelBuilder.buildGroupStrategyModel(strategy, groups);
		return modelAndView;
	}


	@RequestMapping(value = GROUP_STRATEGY_URI, method = RequestMethod.POST)
	public ModelAndView updateGroupStrategy(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName, @PathVariable(value = "strategyName") final String strategyName,
			@ModelAttribute(value = "strategyForm") final GroupStrategyFormBean form, final HttpServletRequest request) {

		ToggleSystem toggleSystem = toggleService.getSystem(systemName);
		GroupStrategy strategy = getStrategy(toggleSystem, featureName, strategyName);
		toggleService.updateGroupStrategy(toggleSystem, form, strategy, getUserId(request));
		return new ModelAndView(MessageFormat.format(FEATURE_REDIRECT, systemName, featureName));
	}


	@RequestMapping(value = GROUP_STRATEGY_URI_DELETE, method = RequestMethod.GET)
	public ModelAndView deleteGroupStrategy(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName, @PathVariable(value = "strategyName") final String strategyName) {
		
		ToggleSystem toggleSystem = toggleService.getSystem(systemName);
		FeatureStrategy strategy = getStrategy(toggleSystem, featureName, strategyName);
		toggleService.deleteStrategy(toggleSystem, featureName, strategy);
		return new ModelAndView(MessageFormat.format(FEATURE_REDIRECT, systemName, featureName));
	}


	@RequestMapping(value = GROUP_STRATEGY_URI_ADD, method = RequestMethod.POST)
	public ModelAndView addGroupStrategy(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName, @ModelAttribute final GroupStrategyFormBean form, final HttpServletRequest request) {

		ToggleSystem toggleSystem = toggleService.getSystem(systemName);
		Feature feature = toggleSystem.getFeatureByName(featureName);
		toggleService.createGroupStrategy(toggleSystem, feature, form, getUserId(request));
		return new ModelAndView(MessageFormat.format(FEATURE_REDIRECT, systemName, featureName));
	}


	@RequestMapping(value = GROUP_STRATEGY_URI_ADD, method = RequestMethod.GET)
	public ModelAndView showAddGroupStrategy(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName, @PathVariable(value = "strategyName") final String strategyName) {

		return modelBuilder.buildGroupStrategyCreateModel(systemName, featureName, strategyName);
	}


	@RequestMapping(value = GROUP_STRATEGY_URI_ADD_FEATURE_GROUP, method = RequestMethod.POST)
	public ModelAndView addFeatureGroup(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName, @PathVariable(value = "strategyName") final String strategyName,
			@ModelAttribute final FeatureGroupFormBean form) {

//		ToggleSystem toggleSystem = toggleService.getSystem(systemName);
//		GroupStrategy strategy = getStrategy(toggleSystem, featureName, strategyName);
//		FeatureGroup featureGroup = toggleService.getGroup(form.getGroupName());
		toggleService.addFeatureGroupToGroupStrategy(systemName, featureName, strategyName, form.getGroupName());
		return new ModelAndView(MessageFormat.format(STRATEGY_REDIRECT, systemName, featureName, strategyName));
	}


	@RequestMapping(value = GROUP_STRATEGY_URI_REMOVE_FEATURE_GROUP, method = RequestMethod.GET)
	public ModelAndView removeFeatureGroup(@PathVariable(value = "systemName") final String systemName,
			@PathVariable(value = "featureName") final String featureName, @PathVariable(value = "strategyName") final String strategyName,
			@PathVariable(value = "groupName") final String groupName) {

		//GroupStrategy groupStrategy = getStrategy(systemName, featureName, strategyName);
		//FeatureGroup featureGroup = groupStrategy.getGroup(groupName);
		toggleService.removeFeatureGroupFromGroupStrategy(systemName, featureName, strategyName, groupName);
		return new ModelAndView(MessageFormat.format(STRATEGY_REDIRECT, systemName, featureName, strategyName));
	}


	private GroupStrategy getStrategy(final ToggleSystem toggleSystem, final String featureName, final String strategyName) {
		
		FeatureStrategy strategy = toggleSystem.getFeatureByName(featureName).getStrategyByName(strategyName);
		return (GroupStrategy) strategy;
	}

}
