package com.suru.fts.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.isA;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

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
import com.suru.fts.dto.FeatureFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.service.ToggleService;

@RunWith(MockitoJUnitRunner.class)
public class FeatureControllerTest extends MockMVCBaseTest {

	private static final String FEATURE_VIEW = "featureView";
	private static final String SYSTEM_VIEW = "systemView";

	@InjectMocks
	private FeatureController classToTest;
	
	@Mock
	private ToggleService mockToggleService;
	
	@Mock
	private ViewModelBuilder mockToggleModelBuilder;
	
	@Override
	public Object[] getControllers() {
	
		return new Object[] {classToTest};
	}
	
	@Test
	public void testGetFeature() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView(FEATURE_VIEW);
		Feature feature = new Feature();
		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		when(mockToggleService.getSystem("testSystem")).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getFeatureByName("featureName")).thenReturn(feature);	
		when(mockToggleModelBuilder.buildFeatureModel(feature)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/system/testSystem/feature/featureName")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(FEATURE_VIEW));
	}
	
	@Test
	public void testDeleteFeature() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView(SYSTEM_VIEW);
		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		Feature feature = new Feature();
		when(mockToggleService.getSystem("testSystem")).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getFeatureByName("featureName")).thenReturn(feature);
		when(mockToggleModelBuilder.buildSystemModel(mockToggleSystem)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/system/testSystem/feature/featureName/delete")).andExpect(status().is(HttpStatus.OK.value()));
		verify(mockToggleService).deleteFeature(feature);
	}
	
	@Test
	public void testAddFeatureGET() throws Exception {
		
		mockMVC.perform(get("/admin/system/testSystem/features/add")).andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testAddFeaturePOST() throws Exception {
		
		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		ArgumentCaptor<FeatureFormBean> ffbCaptor = ArgumentCaptor.forClass(FeatureFormBean.class);

		when(mockToggleService.getSystem("testSystem")).thenReturn(mockToggleSystem);
		mockMVC.perform(post("/admin/system/testSystem/features/add?featureName=newFeature&description=This is a test&status=LIMITED")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name("redirect:/admin/system/testSystem"));;
		verify(mockToggleService).createFeature(eq(mockToggleSystem), ffbCaptor.capture(), isA(String.class));
		assertEquals("newFeature", ffbCaptor.getValue().getFeatureName());
		assertEquals("This%20is%20a%20test", ffbCaptor.getValue().getDescription());
		assertEquals("LIMITED", ffbCaptor.getValue().getStatus());
		assertTrue(ffbCaptor.getValue().isLimited());
		assertFalse(ffbCaptor.getValue().isNotReleased());
		assertFalse(ffbCaptor.getValue().isReleased());
		
		
	}
	
	@Test
	public void testUpdateFeature() throws Exception {
		
		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		Feature feature = new Feature();
		ArgumentCaptor<FeatureFormBean> ffbCaptor = ArgumentCaptor.forClass(FeatureFormBean.class);
		when(mockToggleService.getSystem("testSystem")).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getFeatureByName("newFeature")).thenReturn(feature);
		mockMVC.perform(post("/admin/system/testSystem/feature/featureName?featureName=newFeature&description=This is a test&status=RELEASED")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name("redirect:/admin/system/testSystem"));
		verify(mockToggleService).updateFeature(eq(mockToggleSystem), ffbCaptor.capture(), isA(String.class));
		assertEquals("newFeature", ffbCaptor.getValue().getFeatureName());
		assertEquals("This%20is%20a%20test", ffbCaptor.getValue().getDescription());
		assertEquals("RELEASED", ffbCaptor.getValue().getStatus());
	}
}
