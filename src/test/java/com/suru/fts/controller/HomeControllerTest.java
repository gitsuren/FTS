package com.suru.fts.controller;

import com.suru.fts.MockMVCBaseTest;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.service.ToggleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest extends MockMVCBaseTest {

	private static final String HOME_VIEW = "homeView";
	
	@InjectMocks
	private FeatureToggleHomeController classToTest;
	
	@Mock
	private ToggleService mockToggleService;
	
	@Mock
	private ViewModelBuilder mockViewModelBuilder;

	@SuppressWarnings("unchecked")
	@Test
	public void testDefaultMethod() throws Exception {
		
		ModelAndView expectedModel = new ModelAndView(HOME_VIEW);
		when(mockViewModelBuilder.buildHomeModel(isA(List.class), isA(List.class))).thenReturn(expectedModel);
		mockMVC.perform(get("/admin/")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(HOME_VIEW));
	}
	
	@Override
	public Object[] getControllers() {
		return new Object[] {classToTest};
	}
}
