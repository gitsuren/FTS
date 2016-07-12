package com.suru.fts.model;

import com.google.common.collect.Lists;
import com.suru.fts.dto.*;
import com.suru.fts.dto.mapper.*;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.routes.Routes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.suru.fts.model.ViewModelBuilder.FEATURES;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewModelBuilderTest {

	private static final String ANY_GROUP_NAME = "anyGroupName";
	private static final String EXPECTED_GROUP_DELETE_URL = "/admin/system/MyShinySystem/feature/feature5000/strategy/group/Group/featuregroup/anyGroupDescription/delete";
	private static final String USER = "anyuser";
	private static final String ANY_FEATURE_GROUP_DESC = "AWESOME_GROUP";
	private static final String ANY_GROUP_DESCRIPTION = "anyGroupDescription";
	private static final String CANCEL_HREF = "cancelHref";
	private static final String CANCEL_CLASS_NAME = "cancelClassName";
	private static final String ANY_FEATURE_NAME = "feature5000";
	private static final String ANY_FEATURE_DESCRIPTION = "This is a test feature";
	private static final String ANY_SYSTEM_DESCRIPTION = "This is my shiny new toggle system";
	private static final String ANY_SYSTEM_NAME = "MyShinySystem";
	private static final String ANY_STRATEGY_NAME = "testStrategy";

	@InjectMocks
	private ViewModelBuilder classToTest;

	@Mock
	ToggleSystemFormBeanMapper tsbMapper;

	@Mock
	FeatureFormBeanMapper featureBeanMapper;

	@Mock
	FeatureGroupFormBeanMapper featureGroupFormBeanMapper;
	
	@Mock
	private StrategyFormBeanMapper strategyFormBeanMapper;
	
	@Mock
	private MemberFormBeanMapper memberFormBeanMapper;
		
	@Mock
	private PublicGroupDeleteExceptionMapper mockGroupDeleteExceptionMapper;

	private ToggleSystem toggleSystem = new ToggleSystem();
	private ToggleSystemFormBean toggleSystemBean;
	private List<FeatureFormBean> featureBeans = new ArrayList<FeatureFormBean>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void setUp() {
		toggleSystemBean = buildToggleSystemBean();
		toggleSystem.setSystemName(ANY_SYSTEM_NAME);
		featureBeans.add(buildFeatureBean());

		when(tsbMapper.mapToBean(Matchers.<ToggleSystem> any())).thenReturn(toggleSystemBean);
		when(featureBeanMapper.mapToBean(Matchers.<List> any())).thenReturn(featureBeans);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testBuildSystemModel() {

		ModelAndView result = classToTest.buildSystemModel(toggleSystem);
		assertEquals("admin/system", result.getViewName());
		assertEquals(3, result.getModel().size());

		ToggleSystemFormBean actualToggleSystemBean = (ToggleSystemFormBean) result.getModel().get("system");

		assertThat(actualToggleSystemBean, sameInstance(toggleSystemBean));

		List<FeatureFormBean> actualFeatureBeans = (List<FeatureFormBean>) result.getModel().get(FEATURES);
		assertNotNull(actualFeatureBeans);
		assertThat(actualFeatureBeans, sameInstance(featureBeans));
		assertEquals("/admin/system/MyShinySystem/features/add", result.getModel().get("addFeatureHref"));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testBuildSystemModelWithNoFeatures() {

		when(featureBeanMapper.mapToBean(Matchers.<List> any())).thenReturn(null);

		ModelAndView result = classToTest.buildSystemModel(toggleSystem);
		assertEquals("admin/system", result.getViewName());
		assertEquals(3, result.getModel().size());
		ToggleSystemFormBean actualToggleSystemBean = (ToggleSystemFormBean) result.getModel().get("system");

		assertThat(actualToggleSystemBean, sameInstance(toggleSystemBean));
		List<FeatureFormBean> actualFeatureBeans = (List<FeatureFormBean>) result.getModel().get(FEATURES);
		assertNull(actualFeatureBeans);
		assertEquals("/admin/system/MyShinySystem/features/add", result.getModel().get("addFeatureHref"));
	}

	@Test
	public void testBuildSystemsModel() {

		List<ToggleSystem> systems = new ArrayList<ToggleSystem>();
		ModelAndView result = classToTest.buildSystemsModel(systems);
		assertEquals("admin/systemlist", result.getViewName());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testBuildFeatureModel() {

		ToggleSystem system = createToggleSystem();		
		Feature feature = createFeature(system);		
		FeatureStrategy strategy = new GroupStrategy();
		strategy.setName(ANY_STRATEGY_NAME);
		feature.getStrategies().add(strategy);

		List<StrategyFormBean> stBeans = new ArrayList<>();
		FeatureFormBean fBean = buildFeatureBean();
		stBeans.add(new StrategyFormBean());
		when(featureBeanMapper.mapToBean(feature)).thenReturn(fBean);
		when(strategyFormBeanMapper.mapToBean(Matchers.<List> any())).thenReturn(stBeans);

		ModelAndView result = classToTest.buildFeatureModel(feature);
		assertEquals("admin/feature", result.getViewName());
		assertEquals(4, result.getModel().size());
		assertEquals("/admin/system/MyShinySystem/feature/feature5000",result.getModel().get("addOrEditAction"));
		assertEquals(fBean,result.getModel().get("feature"));
		assertEquals(true,result.getModel().get("isEdit"));
		assertEquals(stBeans,result.getModel().get("strategies"));
	}

	private Feature createFeature(ToggleSystem system) {
		Feature feature = new Feature();
		feature.setName(ANY_FEATURE_NAME);
		feature.setSystemName(system.getSystemName());
		return feature;
	}

	private ToggleSystem createToggleSystem() {
		ToggleSystem system = new ToggleSystem();
		system.setSystemName(ANY_SYSTEM_NAME);
		return system;
	}

	@Test
	public void testBuildFeatureCreateModel() {

		ModelAndView result = classToTest.buildFeatureCreateModel(ANY_SYSTEM_NAME);
		assertEquals("admin/feature", result.getViewName());
		assertEquals(3, result.getModel().size());
		assertEquals("/admin/system/MyShinySystem/features/add",result.getModel().get("addOrEditAction"));
		assertEquals(false,result.getModel().get("isEdit"));
	}

	@Test
	public void testBuildDeleteConfirmationView() {

		ModelAndView result = classToTest.buildDeleteConfirmationView(ANY_SYSTEM_NAME, CANCEL_CLASS_NAME, CANCEL_HREF);
		assertEquals("admin/deleteSystemConfirmation", result.getViewName());
		assertEquals(CANCEL_CLASS_NAME, result.getModel().get("cancelClass"));
		assertEquals(CANCEL_HREF, result.getModel().get("cancelHref"));
		assertEquals("/admin/system/delete", result.getModel().get("deleteHref"));
		assertEquals(ANY_SYSTEM_NAME, result.getModel().get("systemName"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildGroupStrategyModel() {

		ToggleSystem system = createToggleSystem();		
		Feature feature = createFeature(system);	
		feature.setSystemName(system.getSystemName());
		GroupStrategy strategy = new GroupStrategy();
		strategy.setName("Group");
		strategy.setFeatureName(ANY_FEATURE_NAME);
		strategy.setSystemName(ANY_SYSTEM_NAME);
		
		Set<FeatureGroup> groups = newHashSet();
		FeatureGroup fg= new FeatureGroup();
		fg.setDescription(ANY_FEATURE_GROUP_DESC);
		groups.add(fg);
		strategy.setGroups(groups);
		feature.getStrategies().add(strategy);
		
		List<FeatureGroupFormBean> featureGroupFormBeans = newArrayList();
		FeatureGroupFormBean groupFormBean = new FeatureGroupFormBean();
		groupFormBean.setGroupName(ANY_GROUP_DESCRIPTION);
		featureGroupFormBeans.add(groupFormBean);
		
		List<FeatureGroup> groupsList = Lists.newArrayList(groups);
		GroupStrategyFormBean groupStrategyBean = new GroupStrategyFormBean();
		when(strategyFormBeanMapper.mapToBean(strategy)).thenReturn(groupStrategyBean);
		when(featureGroupFormBeanMapper.mapToBean(groups)).thenReturn(featureGroupFormBeans);
		
		ModelAndView result = classToTest.buildGroupStrategyModel(strategy, groupsList);
		assertEquals(6, result.getModel().size());
		assertEquals("admin/groupStrategy", result.getViewName());
		assertEquals(true, result.getModel().get("isEdit"));
		assertEquals("/admin/system/MyShinySystem/feature/feature5000/strategy/group/Group",
				result.getModel().get("addOrEditAction"));
		assertEquals(groupStrategyBean, result.getModel().get("groupStrategy"));
		assertEquals("/admin/system/MyShinySystem/feature/feature5000/strategy/group/Group/groups", result.getModel().get("chooseGroupHref"));
		assertEquals(featureGroupFormBeans,result.getModel().get("featureGroups"));
		assertEquals(1, ((List<String>) result.getModel().get("groupNames")).size());
		assertEquals(ANY_FEATURE_GROUP_DESC, ((List<String>) result.getModel().get("groupNames")).get(0));
		List<FeatureGroupFormBean> groupBeans =  (List<FeatureGroupFormBean>) result.getModel().get("featureGroups");
		assertEquals(EXPECTED_GROUP_DELETE_URL, groupBeans.get(0).getRemoveFeatureGroupHref());
		assertEquals(ANY_GROUP_DESCRIPTION, groupBeans.get(0).getGroupName());
	}

	@Test
	public void testBuildGroupStrategyCreateModel() {

		ModelAndView result = classToTest.buildGroupStrategyCreateModel(ANY_SYSTEM_NAME, ANY_FEATURE_NAME,ANY_STRATEGY_NAME);
		assertEquals("admin/groupStrategy", result.getViewName());
		assertEquals(false, result.getModel().get("isEdit"));
		assertEquals("/admin/system/MyShinySystem/feature/feature5000/strategy/group/testStrategy/add",
				result.getModel().get("addOrEditAction"));

	}
	
	@Test
	public void testBuildStrategyCreateModel() {
		ModelAndView result = classToTest.buildStrategyCreateModel(ANY_SYSTEM_NAME, ANY_FEATURE_NAME);
		assertEquals("admin/strategy", result.getViewName());
		assertEquals("/admin/system/MyShinySystem/feature/feature5000/strategies/add",
				result.getModel().get("addOrEditAction"));
	}

	@Test
	public void testBuildPublicGroupModelWithNoMembers() {

		FeatureGroup group = new FeatureGroup();
		group.setDescription("my_test_group");
		
		Member member = new Member();
		String memberId = "SB95447";
		member.setMemberId(memberId);
		Set<Member> members = new HashSet<Member>();
		members.add(member);
		List<Member> memberLists = new ArrayList<>(members);
		group.setMembers(members);
		MemberFormBean memberFormBean = new MemberFormBean();
		memberFormBean.setMemberId(memberId);
		List<MemberFormBean> memFormBeans = new ArrayList<MemberFormBean>();
		memFormBeans.add(memberFormBean);
		when(memberFormBeanMapper.mapToBean(memberLists)).thenReturn(memFormBeans);
		FeatureGroupFormBean featureGroupFormBean = new FeatureGroupFormBean();
		when(featureGroupFormBeanMapper.mapToBean(group)).thenReturn(featureGroupFormBean);
		ModelAndView result = classToTest.buildPublicGroupModel(group);
		assertEquals(4, result.getModel().size());
		assertEquals("admin/publicGroup", result.getViewName());
		assertEquals(memFormBeans, result.getModel().get("members"));
		assertEquals(featureGroupFormBean, result.getModel().get("group"));
		assertEquals(true, result.getModel().get("isEdit"));
		assertEquals("/admin/group/my_test_group", result.getModel().get("addOrEditAction"));
	}
	
	@Test
	public void testBuildPublicGroupCreateModel(){
		ModelAndView result = classToTest.buildPublicGroupCreateModel();
		assertEquals("admin/publicGroup", result.getViewName());
		assertEquals(false, result.getModel().get("isEdit"));
		assertEquals(Routes.PUBLIC_GROUP_URI_ADD, result.getModel().get("addOrEditAction"));
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBuildHomeModel() {

		List<ToggleSystem> systemsList = new ArrayList<>();
		ToggleSystem testSystem = createToggleSystem();
		systemsList.add(testSystem);
		List<FeatureGroup> groupsList = new ArrayList<>();
		FeatureGroup testGroup = new FeatureGroup();
		testGroup.setDescription(ANY_GROUP_DESCRIPTION);
		groupsList.add(testGroup);

		List<ToggleSystemFormBean> systemBeanList = new ArrayList<>();
		List<FeatureGroupFormBean> fgbList = new ArrayList<>();
		systemBeanList.add(buildToggleSystemBean());
		when(tsbMapper.mapToBean(systemsList)).thenReturn(systemBeanList);
		when(featureGroupFormBeanMapper.mapToBean(groupsList)).thenReturn(fgbList);
		ModelAndView result = classToTest.buildHomeModel(systemsList, groupsList);
		assertEquals(4, result.getModel().size());
		assertEquals("admin/home", result.getViewName());
		
		List<ToggleSystemFormBean> systembeans = (List<ToggleSystemFormBean>) result.getModel().get("systems");
		assertEquals(1, systembeans.size());
		List<FeatureGroupFormBean> groups = (List<FeatureGroupFormBean>) result.getModel().get("groups");
		assertEquals(0, groups.size());
		assertEquals("/admin/systems/create", result.getModel().get("addNewSystemHref"));
		assertEquals("/admin/groups", result.getModel().get("addNewGroupHref"));
	}
	
	@Test
	public void testBuildSystemCreateModel(){
		ModelAndView addMV = classToTest.buildSystemCreateModel(false, ANY_SYSTEM_NAME);
		String expectedView = "admin/systemsEdit";
		assertEquals(expectedView, addMV.getViewName());
		assertEquals("/admin/systems/create", addMV.getModel().get("addOrEditAction"));
		
		ModelAndView editMV = classToTest.buildSystemCreateModel(true, ANY_SYSTEM_NAME);
		assertEquals(expectedView, editMV.getViewName());
		assertEquals("/admin/system/MyShinySystem/edit", editMV.getModel().get("addOrEditAction"));
	}
	
	@Test
	public void testBuildMemberCreateModel(){
		ModelAndView result = classToTest.buildMemberCreateModel(ANY_GROUP_DESCRIPTION);
		assertEquals(1, result.getModel().size());
		String expectedView = "admin/member";
		assertEquals(expectedView, result.getViewName());
		assertEquals("/admin/group/anyGroupDescription/members", result.getModel().get("addOrEditAction"));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBuildGroupStrategyChooseFeatureGroupModel(){
		List<String> groupNames = new ArrayList<String>();
		groupNames.add("GROUP1");
		ModelAndView result = classToTest.buildGroupStrategyChooseFeatureGroupModel(ANY_STRATEGY_NAME, ANY_FEATURE_NAME,ANY_STRATEGY_NAME, groupNames);
		assertEquals(2, result.getModel().size());
		assertEquals("admin/chooseFeatureGroup", result.getViewName());
		assertEquals("/admin/system/testStrategy/feature/feature5000/strategy/group/testStrategy/groups", result.getModel().get("addOrEditAction"));
		assertEquals(groupNames, result.getModel().get("groupNames"));
		assertEquals(1, ((List<String>) result.getModel().get("groupNames")).size());
	}
	
	@Test
	public void testBuildFeaturesForUserPage(){
		List<ToggleSystem> systems = newArrayList();
		ModelAndView result =classToTest.buildFeaturesForUserPage(systems);
		assertEquals("admin/featureForUser", result.getViewName());
		assertEquals(1, result.getModel().size());
		assertEquals(systems, result.getModel().get("systems"));
	}
	
	@Test
	public void testBuildSystemFeaturesForUserModel(){
		Set<String> featureNames=newHashSet();
		featureNames.add("sb95447");
		List<ToggleSystem> systems = newArrayList();
		ModelAndView result =classToTest.buildSystemFeaturesForUserModel(ANY_SYSTEM_NAME, USER,featureNames,systems);
		assertEquals("admin/featureForUser", result.getViewName());
		assertEquals(6, result.getModel().size());
		assertEquals(ANY_SYSTEM_NAME, result.getModel().get("systemName"));
		assertEquals(USER, result.getModel().get("userId"));
		assertEquals("/admin/getfeatures", result.getModel().get("action"));
		assertEquals(featureNames, result.getModel().get("featureNames"));
		assertEquals(true, result.getModel().get("hasFeatures"));
		assertEquals(systems, result.getModel().get("systems"));
	}

	@Test
	public void testBuildPublicGroupDeleteExceptionModel() {
		
		List<GroupStrategy> strategies = new ArrayList<>();
		GroupStrategy strategy = new GroupStrategy();
		strategy.setName(ANY_STRATEGY_NAME);
		strategies.add(strategy);
		Feature mockFeature = Mockito.mock(Feature.class);
		ToggleSystem mockSystem = Mockito.mock(ToggleSystem.class);
		strategy.setFeatureName(ANY_FEATURE_NAME);
		strategy.setSystemName(ANY_SYSTEM_NAME);
		
		when(mockFeature.getName()).thenReturn(ANY_FEATURE_NAME);
		when(mockFeature.getSystemName()).thenReturn("S1");
		when(mockSystem.getSystemName()).thenReturn(ANY_SYSTEM_NAME);
		ModelAndView result = classToTest.buildPublicGroupDeleteExceptionModel(ANY_GROUP_NAME, strategies);
		assertEquals("admin/publicGroupDeleteException", result.getViewName());
		assertEquals(2, result.getModel().size());
		assertEquals(ANY_GROUP_NAME, result.getModel().get("groupName"));
		
	}
	
	private ToggleSystemFormBean buildToggleSystemBean() {
		ToggleSystemFormBean toggleSystemBean = new ToggleSystemFormBean();
		toggleSystemBean.setSystemName(ANY_SYSTEM_NAME);
		toggleSystemBean.setDescription(ANY_SYSTEM_DESCRIPTION);
		return toggleSystemBean;
	}

	private FeatureFormBean buildFeatureBean() {
		FeatureFormBean fb = new FeatureFormBean();
		fb.setStatus("LIMITED");
		fb.setDescription(ANY_FEATURE_DESCRIPTION);
		fb.setFeatureName(ANY_FEATURE_NAME);
		return fb;
	}
}
