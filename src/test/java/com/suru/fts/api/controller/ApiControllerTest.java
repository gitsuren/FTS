package com.suru.fts.api.controller;

import com.suru.fts.MockMVCBaseTest;
import com.suru.fts.api.dto.FeaturesForIdResponse;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.service.ToggleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest extends MockMVCBaseTest {

	private static final String ANY_FEATURE_NAME = "anyFeatureName";
	private static final String ANY_ID = "anyId";
	private static final String ANY_SYSTEM_NAME = "anySystemName";

	@InjectMocks
	private ApiController classToTest;

	@Mock
	private ToggleService mockToggleService;
	
	@Mock
	private ToggleSystem mockToggleSystem;
	
	@Mock
	private List<Feature> mockFeatureSet;
	
	@Test
	public void testGetAvailableFeaturesWithValidData() throws Exception {
		
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getFeatures()).thenReturn(mockFeatureSet);
		//mockMVC.perform(get("/api/system/anySystemName/features/anyId")).andExpect(status().isOk()).andExpect(jsonPath("$.systemName").value("anySystemName")).andExpect(jsonPath("$.id").value("anyId"));
		//verify(mockToggleService).getSystem(ANY_SYSTEM_NAME);
		//verify(mockToggleService).getFeaturesForId(mockToggleSystem, ANY_ID);
	}

	@Test
	public void testGetAvailableFeaturesWhereSystemIsNotFound() throws Exception {
		
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(null);
		mockMVC.perform(get("/api/system/anySystemName/features/anyId")).andExpect(status().isNotFound());
		verify(mockToggleService).getSystem(ANY_SYSTEM_NAME);
	}
	
	@Test
	public void testPopuldateActiveFeaturesResponse() {
		
		Set<String> features = new HashSet<>();
		features.add(ANY_FEATURE_NAME);
		FeaturesForIdResponse result = classToTest.populdateActiveFeaturesResponse(ANY_SYSTEM_NAME, ANY_ID, features);
		assertEquals(ANY_SYSTEM_NAME, result.getSystemName());
		assertEquals(ANY_ID, result.getId());
		assertEquals(1, result.getFeatures().size());
		assertEquals(ANY_FEATURE_NAME, result.getFeatures().get(0));
	}
	
	@Override
	public Object[] getControllers() {
		return new Object[] {classToTest};
	}
}
