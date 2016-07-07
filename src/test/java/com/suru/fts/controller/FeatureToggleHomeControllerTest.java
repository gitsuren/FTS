package com.suru.fts.controller;

import com.suru.fts.MockMVCBaseTest;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.service.ToggleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class FeatureToggleHomeControllerTest extends MockMVCBaseTest {

	private static final String ANY_VIEW = "anyView";

	@InjectMocks
	private FeatureToggleHomeController controller;

	@Mock
	private ToggleService toggleService;

	@Mock
	private ViewModelBuilder viewModelBuilder;

	@SuppressWarnings("unchecked")
	@Test
	public void testShowFTSHomePage() throws Exception {
		ModelAndView mv = new ModelAndView(ANY_VIEW);
		when(toggleService.getActiveSystems()).thenReturn(new ArrayList<ToggleSystem>());
		when(toggleService.getActiveGroups()).thenReturn(new ArrayList<FeatureGroup>());
		when(viewModelBuilder.buildHomeModel(any(List.class), any(List.class))).thenReturn(mv);

		mockMVC.perform(get("/admin")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(ANY_VIEW));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testShowPageToGetFeaturesForUserId() throws Exception {
		ModelAndView mv = new ModelAndView(ANY_VIEW);
		when(viewModelBuilder.buildFeaturesForUserPage(anyList())).thenReturn(mv);
		mockMVC.perform(get("/admin/getfeatures")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name(ANY_VIEW));
	}

	@Test
	public void testGetFeaturesForUserId() throws Exception {
		Set<String> features = newHashSet();
		features.add("Test Feature");
		List<ToggleSystem> systems = newArrayList();
		when(toggleService.getActiveSystems()).thenReturn(systems);
		when(toggleService.getFeaturesForId("12345", "testuser")).thenReturn(features);
		ModelAndView mv = new ModelAndView(ANY_VIEW);
		when(viewModelBuilder.buildSystemFeaturesForUserModel("12345", "testuser", features, systems)).thenReturn(mv);
		mockMVC.perform(post("/admin/getfeatures?systemName=12345&userId=testuser")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name(ANY_VIEW));
	}

	@Override
	public Object[] getControllers() {
		return new Object[] { controller };
	}

}
