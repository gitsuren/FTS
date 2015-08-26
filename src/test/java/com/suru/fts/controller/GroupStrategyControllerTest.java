package com.suru.fts.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import com.suru.fts.MockMVCBaseTest;
import com.suru.fts.dto.GroupStrategyFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.service.ToggleService;


@RunWith(MockitoJUnitRunner.class)
public class GroupStrategyControllerTest extends MockMVCBaseTest {

	private static final String ANY_VIEW_NAME = "anyViewName";
	private static final String ANY_GROUP_NAME = "anyGroupName";
	private static final String ANY_FEATURE_GROUP_DESCRIPTION = "anyFeatureGroupDescription";
	private static final String ANY_SYSTEM_NAME = "testSystem";
	private static final String ANY_FEATURE_NAME = "feature1";
	private static final String STRATEGY_NAME_FIELD = "strategyName";
	private static final String ANY_STRATEGY_NAME = "strategy1";
	private static final String STRATEGY_VIEW = "strategyView";

	@InjectMocks
	private GroupStrategyController classToTest;

	@Mock
	private ToggleService mockToggleService;

	@Mock
	private ViewModelBuilder mockToggleModelBuilder;


	@Test
	public void testGetStrategyWhenStrategyIsGroupStrategy() throws Exception {

		ModelAndView modelAndView = new ModelAndView(STRATEGY_VIEW);
		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		Feature mockFeature = Mockito.mock(Feature.class);
		GroupStrategy mockStrategy = new GroupStrategy();
		List<FeatureGroup> groups = new ArrayList<FeatureGroup>();
		when(mockToggleSystem.getFeatureByName(ANY_FEATURE_NAME)).thenReturn(mockFeature);
		when(mockFeature.getStrategyByName(ANY_STRATEGY_NAME)).thenReturn(mockStrategy);
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
		when(mockToggleService.getActiveGroups()).thenReturn(groups);
		when(mockToggleModelBuilder.buildGroupStrategyModel(mockStrategy, groups)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/system/testSystem/feature/feature1/strategy/group/strategy1")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name(STRATEGY_VIEW));
		verify(mockToggleModelBuilder).buildGroupStrategyModel(mockStrategy, groups);
	}


	@Test
	public void testUpdateGroupStrategy() throws Exception {

		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		Feature mockFeature = Mockito.mock(Feature.class);
		GroupStrategy mockStrategy = new GroupStrategy();
		when(mockToggleSystem.getFeatureByName(ANY_FEATURE_NAME)).thenReturn(mockFeature);
		when(mockFeature.getStrategyByName(ANY_STRATEGY_NAME)).thenReturn(mockStrategy);
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
		mockMVC.perform(post("/admin/system/testSystem/feature/feature1/strategy/group/strategy1")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name("redirect:/admin/system/testSystem/feature/feature1"));
		//verify(mockToggleService).updateGroupStrategy(isA(GroupStrategyFormBean.class), eq(mockStrategy), isA(String.class));
	}


	@Test
	public void testDeleteGroupStrategy() throws Exception {

		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		Feature mockFeature = Mockito.mock(Feature.class);
		FeatureStrategy mockStrategy = new GroupStrategy();
		//mockStrategy.setFeature(mockFeature);
		when(mockToggleSystem.getFeatureByName(ANY_FEATURE_NAME)).thenReturn(mockFeature);
		when(mockFeature.getStrategyByName(ANY_STRATEGY_NAME)).thenReturn(mockStrategy);
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
		mockMVC.perform(get("/admin/system/testSystem/feature/feature1/strategy/group/strategy1/delete")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name("redirect:/admin/system/testSystem/feature/feature1"));
		//verify(mockToggleService).deleteStrategy(mockStrategy);
	}


	@Test
	public void testAddGroupStrategy() throws Exception {

		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		FeatureStrategy mockStrategy = new GroupStrategy();
		Feature mockFeature = Mockito.mock(Feature.class);
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getFeatureByName(ANY_FEATURE_NAME)).thenReturn(mockFeature);
		when(mockFeature.getStrategyByName(ANY_STRATEGY_NAME)).thenReturn(mockStrategy);
		mockMVC.perform(post("/admin/system/testSystem/feature/feature1/strategy/group/strategy1/add").param(STRATEGY_NAME_FIELD, ANY_STRATEGY_NAME))
				.andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name("redirect:/admin/system/testSystem/feature/feature1"));
		//verify(mockToggleService).createGroupStrategy(eq(mockFeature), isA(GroupStrategyFormBean.class), isA(String.class));
	}


	@Test
	public void testAddFeatureGroup() throws Exception {

		FeatureGroup featureGroup = new FeatureGroup();
		featureGroup.setDescription(ANY_FEATURE_GROUP_DESCRIPTION);
		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		Feature mockFeature = Mockito.mock(Feature.class);
		GroupStrategy mockGroupStrategy = Mockito.mock(GroupStrategy.class);
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getFeatureByName(ANY_FEATURE_NAME)).thenReturn(mockFeature);
		when(mockFeature.getStrategyByName(ANY_STRATEGY_NAME)).thenReturn(mockGroupStrategy);
		when(mockToggleService.getGroup(ANY_FEATURE_GROUP_DESCRIPTION)).thenReturn(featureGroup);
		ArgumentCaptor<FeatureGroup> featureGroupCaptor = ArgumentCaptor.forClass(FeatureGroup.class);
		String expectedUrl = "redirect:/admin/system/testSystem/feature/feature1/strategy/group/strategy1";
		mockMVC.perform(post("/admin/system/testSystem/feature/feature1/strategy/group/strategy1/groups").param("groupName", ANY_FEATURE_GROUP_DESCRIPTION))
				.andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(expectedUrl));
		//verify(mockToggleService).addFeatureGroupToGroupStrategy(eq(mockGroupStrategy), featureGroupCaptor.capture());
		assertEquals(ANY_FEATURE_GROUP_DESCRIPTION, featureGroupCaptor.getValue().getDescription());
	}


	@Test
	public void testRemoveFeatureGroup() throws Exception {

		GroupStrategy groupStrategy = new GroupStrategy();
		FeatureGroup featureGroup = new FeatureGroup();
		featureGroup.setDescription(ANY_GROUP_NAME);
		groupStrategy.getGroups().add(featureGroup);
		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		Feature mockFeature = Mockito.mock(Feature.class);
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getFeatureByName(ANY_FEATURE_NAME)).thenReturn(mockFeature);
		when(mockFeature.getStrategyByName(ANY_STRATEGY_NAME)).thenReturn(groupStrategy);
		mockMVC.perform(get("/admin/system/testSystem/feature/feature1/strategy/group/strategy1/featuregroup/anyGroupName/delete"))
				.andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name("redirect:/admin/system/testSystem/feature/feature1/strategy/group/strategy1"));
		//verify(mockToggleService).removeFeatureGroupFromGroupStrategy(featureGroup, groupStrategy);
	}


	@Test
	public void testShowAddGroupStrategy() throws Exception {

		ModelAndView modelAndView = new ModelAndView(ANY_VIEW_NAME);
		when(mockToggleModelBuilder.buildGroupStrategyCreateModel(ANY_SYSTEM_NAME, ANY_FEATURE_NAME, ANY_STRATEGY_NAME)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/system/testSystem/feature/feature1/strategy/group/strategy1/add")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name(ANY_VIEW_NAME));
		verify(mockToggleModelBuilder).buildGroupStrategyCreateModel(ANY_SYSTEM_NAME, ANY_FEATURE_NAME, ANY_STRATEGY_NAME);
	}


	@Override
	public Object[] getControllers() {
		return new Object[] { classToTest };
	}

}
