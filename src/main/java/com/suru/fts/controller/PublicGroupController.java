package com.suru.fts.controller;

import static com.suru.fts.routes.Routes.PUBLIC_GROUP_URI;
import static com.suru.fts.routes.Routes.PUBLIC_GROUP_URI_ADD;
import static com.suru.fts.routes.Routes.PUBLIC_GROUP_URI_DELETE;
import static com.suru.fts.routes.Routes.PUBLIC_GROUP_URI_MEMBER_ADD;
import static com.suru.fts.routes.Routes.PUBLIC_GROUP_URI_MEMBER_DELETE;

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
import com.suru.fts.dto.MemberFormBean;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.service.ToggleService;


@Controller
public class PublicGroupController extends BaseController {

	@Autowired
	private ToggleService toggleService;

	@Autowired
	private ViewModelBuilder viewModelBuilder;


	@RequestMapping(value = PUBLIC_GROUP_URI, method = RequestMethod.GET)
	public ModelAndView get(@PathVariable(value = "groupName") final String groupName) {

		FeatureGroup group = toggleService.getGroup(groupName);
		ModelAndView modelAndView = viewModelBuilder.buildPublicGroupModel(group);
		return modelAndView;
	}


	@RequestMapping(value = PUBLIC_GROUP_URI, method = RequestMethod.POST)
	public ModelAndView update(@PathVariable(value = "groupName") final String groupName, @ModelAttribute final FeatureGroupFormBean groupForm, final HttpServletRequest request) {

		FeatureGroup featureGroup = toggleService.getGroup(groupName);
		toggleService.updateGroup(groupForm, featureGroup, getUserId(request));
		ModelAndView modelAndView = viewModelBuilder.buildPublicGroupModel(featureGroup);
		return modelAndView;
	}


	@RequestMapping(value = PUBLIC_GROUP_URI_DELETE, method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable(value = "groupName") final String groupName) {

		FeatureGroup featureGroup = toggleService.getGroup(groupName);
		List<GroupStrategy> usedByStrategies = toggleService.getStratagiesByGroup(featureGroup);
		if (usedByStrategies.size() > 0) {
			
			ModelAndView responseMV = viewModelBuilder.buildPublicGroupDeleteExceptionModel(groupName, usedByStrategies);
			return responseMV;
		} else {

			toggleService.deleteGroup(featureGroup);
			return new ModelAndView("redirect:/admin");
		}
	}


	@RequestMapping(value = PUBLIC_GROUP_URI_ADD, method = RequestMethod.POST)
	public ModelAndView add(@ModelAttribute final FeatureGroupFormBean group, final HttpServletRequest request) {

		FeatureGroup featureGroup = toggleService.createFeatureGroup(group, getUserId(request));
		return new ModelAndView("redirect:/admin/group/" + featureGroup.getDescription());
	}


	@RequestMapping(value = PUBLIC_GROUP_URI_ADD, method = RequestMethod.GET)
	public ModelAndView showAddPublicGroup() {
		ModelAndView modelAndView = viewModelBuilder.buildPublicGroupCreateModel();
		return modelAndView;
	}


	@RequestMapping(value = PUBLIC_GROUP_URI_MEMBER_DELETE, method = RequestMethod.GET)
	public ModelAndView deleteMember(@PathVariable(value = "groupName") final String groupName, @PathVariable(value = "memberName") final String memberName) {

		FeatureGroup featureGroup = toggleService.getGroup(groupName);
		Member member = featureGroup.getMember(memberName);
		toggleService.deleteMember(member);
		return new ModelAndView("redirect:/admin/group/" + groupName);
	}


	@RequestMapping(value = PUBLIC_GROUP_URI_MEMBER_ADD, method = RequestMethod.POST)
	public ModelAndView addMember(@PathVariable(value = "groupName") final String groupName, @ModelAttribute final MemberFormBean member, final HttpServletRequest request) {

		FeatureGroup featureGroup = toggleService.getGroup(groupName);
		toggleService.addMember(featureGroup, member, getUserId(request));
		return new ModelAndView("redirect:/admin/group/" + groupName);
	}


	@RequestMapping(value = PUBLIC_GROUP_URI_MEMBER_ADD, method = RequestMethod.GET)
	public ModelAndView showAddMember(@PathVariable(value = "groupName") final String groupName) {

		ModelAndView modelAndView = viewModelBuilder.buildMemberCreateModel(groupName);
		return modelAndView;
	}

}
