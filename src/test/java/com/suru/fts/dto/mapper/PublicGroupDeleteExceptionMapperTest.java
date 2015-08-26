package com.suru.fts.dto.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.dto.PublicGroupDeleteExceptionFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;

@RunWith(MockitoJUnitRunner.class)
public class PublicGroupDeleteExceptionMapperTest {

	private static final String ANY_SYSTEM_NAME = "anySystemName";
	private static final String ANY_FEATURE_NAME = "anyFeatureName";
	private static final String ANY_STRATEGY_NAME = "anyStrategyName";
	
	@InjectMocks
	private PublicGroupDeleteExceptionMapper classToTest;
	
	@Test
	public void testMapToBean() {
		
		GroupStrategy groupStrategy = new GroupStrategy();
		groupStrategy.setName(ANY_STRATEGY_NAME);
		ToggleSystem mockToggleSystem = Mockito.mock(ToggleSystem.class);
		Feature mockFeature = Mockito.mock(Feature.class);
		groupStrategy.setFeatureName(ANY_FEATURE_NAME);
		groupStrategy.setSystemName(ANY_SYSTEM_NAME);
		when(mockFeature.getName()).thenReturn(ANY_FEATURE_NAME);
		when(mockFeature.getSystemName()).thenReturn("S1");
//		when(mockFeature.getSystem()).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getSystemName()).thenReturn(ANY_SYSTEM_NAME);
		PublicGroupDeleteExceptionFormBean result = classToTest.mapToBean(groupStrategy);
		assertEquals(ANY_STRATEGY_NAME, result.getStrategyName());
		assertEquals(ANY_FEATURE_NAME, result.getFeatureName());
		assertEquals(ANY_SYSTEM_NAME, result.getSystemName());
	}
}
