package com.suru.fts.model;

import com.suru.fts.dto.*;
import com.suru.fts.dto.mapper.*;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.routes.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ViewModelBuilder {

	private static final String GROUP_NAME = "groupName";
	private static final String MEMBER = "member";
	private static final String ADD_NEW_SYSTEM_HREF = "addNewSystemHref";
	private static final String ADD_NEW_GROUP_HREF = "addNewGroupHref";
	private static final String HOME_VIEW = "home";
	private static final String GRP_STRATEGY = "groupStrategy";
	private static final String PUBLIC_GROUP_VIEW = "publicGroup";
	private static final String PUBLIC_GROUP_DELETE_EXCEPTION_VIEW = "publicGroupDeleteException";
	private static final String ADD_OR_EDIT_ACTION = "addOrEditAction";
	public static final String SYSTEM = "system";
	public static final String SYSTEMS = "systems";
	private static final String GROUPS = "groups";
	public static final String FEATURE = "feature";
	public static final String FEATURES = "features";
	public static final String STRATEGIES = "strategies";
	private static final String ADD_FEATURE_HREF = "addFeatureHref";

	private static final String VIEW_ROOT_TEMPLATE = "admin/%s";
	private static final String SYSTEM_LISTS_VIEW = "systemlist";
	private static final String SYSTEM_VIEW = "system";
	private static final String SYSTEMS_EDIT_VIEW = "systemsEdit";
	private static final String FEATURE_VIEW = "feature";
	private static final String STRATEGY_VIEW = "strategy";
	private static final String GROUP_STRATEGY_VIEW = "groupStrategy";
	private static final String DELETE_SYSTEM_CONFIRMATION = "deleteSystemConfirmation";
	
	private static final String FEATURE_GROUP_DELETE_PATTERN = "/admin/system/{0}/feature/{1}/strategy/group/{2}/featuregroup/{3}/delete";
	
	@Autowired
	private ToggleSystemFormBeanMapper systemFormBeanMapper;

	@Autowired
	private ToggleSystemFormBeanMapper systemMapper;

	@Autowired
	private FeatureFormBeanMapper featureBeanMapper;

	@Autowired
	private FeatureGroupFormBeanMapper featureGroupFormBeanMapper;

	@Autowired
	private StrategyFormBeanMapper strategyFormBeanMapper;

	@Autowired
	private MemberFormBeanMapper memberFormBeanMapper;
	
	@Autowired
	private PublicGroupDeleteExceptionMapper publicGroupDeleteExceptionMapper;

	public ModelAndView buildSystemsModel(final List<ToggleSystem> systems) {
		ModelAndView systemsModelAndView = new ModelAndView(
				getViewName(SYSTEM_LISTS_VIEW));
		systemsModelAndView.addObject(SYSTEMS,
				systemFormBeanMapper.mapToBean(systems));
		systemsModelAndView.addObject(ADD_NEW_SYSTEM_HREF,
				Routes.SYSTEM_CREATE_URI);
		return systemsModelAndView;
	}

	public ModelAndView buildSystemModel(final ToggleSystem toggleSystem) {

		ModelAndView result = new ModelAndView(getViewName(SYSTEM_VIEW));
		result.addObject(SYSTEM, systemFormBeanMapper.mapToBean(toggleSystem));

		List<FeatureFormBean> featureBeans = featureBeanMapper
				.mapToBean(toggleSystem.getFeatures());
		result.addObject(FEATURES, featureBeans);
		result.addObject(ADD_FEATURE_HREF,
				"/admin/system/" + toggleSystem.getSystemName()
						+ "/features/add");
		return result;
	}

	public String getViewName(final String name) {
		return String.format(VIEW_ROOT_TEMPLATE, name);
	}

	public ModelAndView buildSystemCreateModel(final boolean isEdit,
			final String systemName) {

		ModelAndView result = new ModelAndView(getViewName(SYSTEMS_EDIT_VIEW));
		if (isEdit) {
			result.addObject(ADD_OR_EDIT_ACTION, "/admin/system/" + systemName
					+ "/edit");
		} else {
			result.addObject(ADD_OR_EDIT_ACTION, Routes.SYSTEM_CREATE_URI);
		}

		return result;
	}

	public ModelAndView buildFeatureModel(final Feature feature) {

		ModelAndView result = new ModelAndView(getViewName(FEATURE_VIEW));

		result.addObject(ADD_OR_EDIT_ACTION,
				"/admin/system/" + feature.getSystemName()
						+ "/feature/" + feature.getName());
		FeatureFormBean featureFormBean = featureBeanMapper.mapToBean(feature);
		result.addObject(FEATURE, featureFormBean);

		List<? extends StrategyFormBean> strategies = strategyFormBeanMapper
				.mapToBean(feature.getStrategies());
		result.addObject(STRATEGIES, strategies);
		result.addObject("isEdit", true);
		return result;
	}

	public ModelAndView buildFeatureCreateModel(final String systemName) {
		ModelAndView result = new ModelAndView(getViewName(FEATURE_VIEW));
		result.addObject("isEdit", false);
		result.addObject("isNew", true);
		result.addObject(ADD_OR_EDIT_ACTION, "/admin/system/" + systemName
				+ "/features/add");
		return result;
	}

	public ModelAndView buildDeleteConfirmationView(final String systemName,
			final String cancelClassName, final String cancelHref) {
		ModelAndView mv = new ModelAndView(
				getViewName(DELETE_SYSTEM_CONFIRMATION));
		mv.addObject("cancelClass", cancelClassName);
		mv.addObject("cancelHref", cancelHref);
		mv.addObject("deleteHref", Routes.SYSTEM_DELETE_URI);
		mv.addObject("systemName", systemName);
		return mv;
	}

	public ModelAndView buildStrategyCreateModel(final String systemName,
			final String featureName) {
		ModelAndView result = new ModelAndView(getViewName(STRATEGY_VIEW));
		result.addObject(ADD_OR_EDIT_ACTION, "/admin/system/" + systemName
				+ "/feature/" + featureName + "/strategies/add");
		return result;
	}

	public ModelAndView buildGroupStrategyModel(final GroupStrategy strategy,
			final List<FeatureGroup> groups) {

		ModelAndView result = new ModelAndView(getViewName(GROUP_STRATEGY_VIEW));
		List<String> groupNames = buildGroupNames(groups);
		//Feature feature = strategy.getFeature();

		String postUrl = "/admin/system/" + strategy.getSystemName()
				+ "/feature/" + strategy.getFeatureName() + "/strategy/group/"
				+ strategy.getName();
		result.addObject(ADD_OR_EDIT_ACTION, postUrl);
		GroupStrategyFormBean groupStrategyBean = (GroupStrategyFormBean) strategyFormBeanMapper
				.mapToBean(strategy);
		result.addObject(GRP_STRATEGY, groupStrategyBean);
		result.addObject("chooseGroupHref", postUrl + "/groups");
		result.addObject("isEdit", true);
		List<FeatureGroupFormBean> featureGroupFormBeans = featureGroupFormBeanMapper
				.mapToBean(strategy.getGroups());
		updateDeleteURLForGroups(featureGroupFormBeans, strategy);
		result.addObject("featureGroups", featureGroupFormBeans);
		result.addObject("groupNames", groupNames);
		return result;
	}

	public ModelAndView buildGroupStrategyCreateModel(final String systemName,
			final String featureName, final String strategyName) {
		ModelAndView result = new ModelAndView(getViewName(GROUP_STRATEGY_VIEW));
		result.addObject("isEdit", false);
		result.addObject(ADD_OR_EDIT_ACTION, "/admin/system/" + systemName
				+ "/feature/" + featureName + "/strategy/group/" + strategyName
				+ "/add");
		return result;
	}

	public ModelAndView buildPublicGroupModel(final FeatureGroup group) {

		ModelAndView result = new ModelAndView(getViewName(PUBLIC_GROUP_VIEW));
		result.addObject("group", featureGroupFormBeanMapper.mapToBean(group));
		result.addObject("isEdit", true);
		List<Member> members = new ArrayList<>(group.getMembers());
		List<MemberFormBean> memberFormBeans = memberFormBeanMapper
				.mapToBean(members);
		result.addObject("members", memberFormBeans);
		result.addObject(ADD_OR_EDIT_ACTION,
				"/admin/group/" + group.getDescription());
		return result;
	}

	public ModelAndView buildPublicGroupDeleteExceptionModel(final String groupName, final List<GroupStrategy> strategies) {
		
		ModelAndView result = new ModelAndView(getViewName(PUBLIC_GROUP_DELETE_EXCEPTION_VIEW));
		List<? extends PublicGroupDeleteExceptionFormBean> strategyBeans = publicGroupDeleteExceptionMapper
				.mapToBean(strategies);
		result.addObject(STRATEGIES, strategyBeans);
		result.addObject(GROUP_NAME, groupName);
		return result;
	}
	
	public ModelAndView buildPublicGroupCreateModel() {
		ModelAndView result = new ModelAndView(getViewName(PUBLIC_GROUP_VIEW));
		result.addObject(ADD_OR_EDIT_ACTION, Routes.PUBLIC_GROUP_URI_ADD);
		result.addObject("isEdit", false);
		return result;
	}

	public ModelAndView buildHomeModel(final List<ToggleSystem> systems,
			final List<FeatureGroup> groups) {

		ModelAndView result = new ModelAndView(getViewName(HOME_VIEW));
		result.addObject(SYSTEMS, systemFormBeanMapper.mapToBean(systems));
		result.addObject(GROUPS, featureGroupFormBeanMapper.mapToBean(groups));
		result.addObject(ADD_NEW_SYSTEM_HREF, Routes.SYSTEM_CREATE_URI);
		result.addObject(ADD_NEW_GROUP_HREF, Routes.PUBLIC_GROUP_URI_ADD);
		return result;
	}

	public ModelAndView buildMemberCreateModel(String groupName) {
		ModelAndView result = new ModelAndView(getViewName(MEMBER));
		result.addObject(ADD_OR_EDIT_ACTION, "/admin/group/" + groupName
				+ "/members");
		return result;
	}

	public ModelAndView buildGroupStrategyChooseFeatureGroupModel(
			String systemName, String featureName, String strategyName,
			List<String> groupNames) {
		ModelAndView result = new ModelAndView(
				getViewName("chooseFeatureGroup"));
		result.addObject(ADD_OR_EDIT_ACTION, "/admin/system/" + systemName
				+ "/feature/" + featureName + "/strategy/group/" + strategyName
				+ "/groups");
		result.addObject("groupNames", groupNames);
		return result;
	}

	private List<String> buildGroupNames(final List<FeatureGroup> groups) {

		List<String> results = new ArrayList<>();
		for (FeatureGroup group : groups) {
			results.add(group.getDescription());
		}
		return results;
	}

	public ModelAndView buildFeaturesForUserPage(List<ToggleSystem> systems) {
		ModelAndView result = new ModelAndView(getViewName("featureForUser"));
		result.addObject("systems", systems);
		return result;
	}

	public ModelAndView buildSystemFeaturesForUserModel(String systemName,
			String userId, Set<String> featureNames, List<ToggleSystem> systems) {
		ModelAndView result = new ModelAndView(getViewName("featureForUser"));
		result.addObject("systemName", systemName);
		result.addObject("userId", userId);
		result.addObject("action", "/admin/getfeatures");
		result.addObject("featureNames", featureNames);
		result.addObject("hasFeatures", true);
		result.addObject("systems", systems);
		return result;
	}

	private void updateDeleteURLForGroups(final List<FeatureGroupFormBean> featureGroupFormBeans, final GroupStrategy strategy) {

		for (FeatureGroupFormBean bean : featureGroupFormBeans) {
			String deleteLink = MessageFormat.format(FEATURE_GROUP_DELETE_PATTERN, strategy.getSystemName(), strategy.getFeatureName(), strategy.getName(), bean.getGroupName());
			bean.setRemoveFeatureGroupHref(deleteLink);
		}
	}
}
