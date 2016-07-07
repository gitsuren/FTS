package com.suru.fts.controller;

import com.suru.fts.MockMVCBaseTest;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.service.ToggleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(MockitoJUnitRunner.class)
public class StrategyControllerTest extends MockMVCBaseTest {

	private static final String ANY_STRATEGY_NAME = "anyStrategyName";
	private static final String ANY_SYSTEM_NAME = "testSystem";
	private static final String ANY_FEATURE_NAME = "feature1";
	private static final String STRATEGY_VIEW = "strategyView";

	@InjectMocks
	private StrategyController classToTest;

	@Mock
	private ViewModelBuilder mockToggleModelBuilder;

	@Mock
	private ToggleService mockToggleService;
	
	@Mock
	private ToggleSystem mockToggleSystem;

	@Override
	public Object[] getControllers() {
		return new Object[] { classToTest };
	}


	@Test
	public void testShowCreateStrategyPage() throws Exception {

		ModelAndView modelAndView = new ModelAndView(STRATEGY_VIEW);
		when(mockToggleModelBuilder.buildStrategyCreateModel(ANY_SYSTEM_NAME, ANY_FEATURE_NAME)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/system/testSystem/feature/feature1/strategies/add")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name(STRATEGY_VIEW));
		verify(mockToggleModelBuilder).buildStrategyCreateModel(ANY_SYSTEM_NAME, ANY_FEATURE_NAME);

	}


	@Test
	public void testAddStrategy() throws Exception {

		Feature mockFeature = Mockito.mock(Feature.class);
		when(mockToggleService.getSystem("testSystem")).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getFeatureByName("feature1")).thenReturn(mockFeature);
		mockMVC.perform(
				post("/admin/system/testSystem/feature/feature1/strategies/add").param("strategyType", "GROUP").param("strategyName", ANY_STRATEGY_NAME))
				.andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name("redirect:/admin/system/testSystem/feature/feature1/strategy/group/anyStrategyName"));
		//verify(mockToggleService).createStrategy(isA(Feature.class), isA(StrategyFormBean.class), isA(String.class));
	}
}
