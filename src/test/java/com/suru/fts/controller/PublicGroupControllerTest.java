package com.suru.fts.controller;

import com.suru.fts.MockMVCBaseTest;
import com.suru.fts.dto.FeatureGroupFormBean;
import com.suru.fts.dto.MemberFormBean;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.service.ToggleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(MockitoJUnitRunner.class)
public class PublicGroupControllerTest extends MockMVCBaseTest {

	private static final String ANY_VIEW_NAME = "anyViewName";
	private static final String MEMBER_VIEW = "member";
	private static final String ANY_MEMBER_ID = "anyMemberId";
	private static final String ANY_MEMBER_NAME = "member1";
	private static final String ANY_GROUP_NAME = "group1";

	private static final String GROUP_VIEW = "groupView";

	@InjectMocks
	private PublicGroupController classToTest;

	@Mock
	private ToggleService mockToggleService;

	@Mock
	private ViewModelBuilder mockViewBuilder;


	@Test
	public void testGet() throws Exception {

		FeatureGroup group = new FeatureGroup();
		ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
		when(mockToggleService.getGroup(ANY_GROUP_NAME)).thenReturn(group);
		when(mockViewBuilder.buildPublicGroupModel(group)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/group/group1")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(GROUP_VIEW));
		verify(mockViewBuilder).buildPublicGroupModel(group);
	}


	@Test
	public void testUpdate() throws Exception {

		FeatureGroup group = new FeatureGroup();
		ModelAndView modelAndView = new ModelAndView(GROUP_VIEW);
		when(mockToggleService.getGroup(ANY_GROUP_NAME)).thenReturn(group);
		when(mockViewBuilder.buildPublicGroupModel(group)).thenReturn(modelAndView);
		mockMVC.perform(post("/admin/group/group1")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(GROUP_VIEW));
		verify(mockToggleService).updateGroup(isA(FeatureGroupFormBean.class), eq(group), isA(String.class));
		verify(mockViewBuilder).buildPublicGroupModel(group);
	}


	@Test
	public void testDelete() throws Exception {

		FeatureGroup group = new FeatureGroup();
		when(mockToggleService.getGroup(ANY_GROUP_NAME)).thenReturn(group);
		mockMVC.perform(get("/admin/group/group1/delete")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name("redirect:/admin"));
		verify(mockToggleService).deleteGroup(group);
	}

	@Test
	public void testDeleteWhenNotAbleTo() throws Exception {

		ModelAndView modelAndView = new ModelAndView(ANY_VIEW_NAME);
		FeatureGroup group = new FeatureGroup();
		when(mockToggleService.getGroup(ANY_GROUP_NAME)).thenReturn(group);
		List<GroupStrategy> strategyList = new ArrayList<>();
		GroupStrategy groupStrat = new GroupStrategy();
		strategyList.add(groupStrat);
		when(mockToggleService.getStratagiesByGroup(group)).thenReturn(strategyList);
		when(mockViewBuilder.buildPublicGroupDeleteExceptionModel(ANY_GROUP_NAME, strategyList)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/group/group1/delete")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(ANY_VIEW_NAME));
		verify(mockToggleService, times(0)).deleteGroup(group);
	}

	@Test
	public void testAdd() throws Exception {

		FeatureGroup featureGroup = new FeatureGroup();
		featureGroup.setDescription(ANY_GROUP_NAME);
		when(mockToggleService.createFeatureGroup(isA(FeatureGroupFormBean.class), isA(String.class))).thenReturn(featureGroup);
		mockMVC.perform(post("/admin/groups").param("description", ANY_GROUP_NAME)).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name("redirect:/admin/group/group1"));
	}


	@Test
	public void testDeleteMember() throws Exception {

		FeatureGroup mockFeatureGroup = Mockito.mock(FeatureGroup.class);
		Member mockMember = Mockito.mock(Member.class);
		when(mockFeatureGroup.getMember(ANY_MEMBER_NAME)).thenReturn(mockMember);
		when(mockToggleService.getGroup(ANY_GROUP_NAME)).thenReturn(mockFeatureGroup);
		mockMVC.perform(get("/admin/group/group1/member/member1/delete")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name("redirect:/admin/group/group1"));
		verify(mockToggleService).deleteMember(isA(Member.class));
	}


	@Test
	public void testAddMember() throws Exception {

		FeatureGroup mockFeatureGroup = Mockito.mock(FeatureGroup.class);
		when(mockToggleService.getGroup(ANY_GROUP_NAME)).thenReturn(mockFeatureGroup);
		ArgumentCaptor<MemberFormBean> formBeanArgumentCaptor = ArgumentCaptor.forClass(MemberFormBean.class);
		mockMVC.perform(post("/admin/group/group1/members").param("memberId", ANY_MEMBER_ID)).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name("redirect:/admin/group/group1"));
		verify(mockToggleService).getGroup(ANY_GROUP_NAME);
		verify(mockToggleService).addMember(eq(mockFeatureGroup), formBeanArgumentCaptor.capture(), isA(String.class));
		assertEquals(ANY_MEMBER_ID, formBeanArgumentCaptor.getValue().getMemberId());
	}
	
	@Test
	public void testShowAddMember() throws Exception{
		ModelAndView modelAndView = new ModelAndView(MEMBER_VIEW);
		when(mockViewBuilder.buildMemberCreateModel(ANY_GROUP_NAME)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/group/group1/members")).andExpect(status().is(HttpStatus.OK.value()))
		.andExpect(view().name(MEMBER_VIEW));
	}
	
	@Test
	public void testShowAddPublicGroup() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView(ANY_VIEW_NAME);
		when(mockViewBuilder.buildPublicGroupCreateModel()).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/groups")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(ANY_VIEW_NAME));
	}


	@Override
	public Object[] getControllers() {

		return new Object[] { classToTest };
	}
}
