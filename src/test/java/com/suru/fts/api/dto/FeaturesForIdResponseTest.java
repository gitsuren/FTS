package com.suru.fts.api.dto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FeaturesForIdResponseTest {

	private static final String ANY_FEATURE_NAME = "anyFeatureName";
	private static final String ANY_SYSTEM_NAME = "anySystemName";
	private static final String ANY_ID = "anyId";

	@Test
	public void test() {
		
		List<String> features = new ArrayList<>();
		features.add(ANY_FEATURE_NAME);
		FeaturesForIdResponse testClass = new FeaturesForIdResponse();
		testClass.setId(ANY_ID);
		testClass.setSystemName(ANY_SYSTEM_NAME);
		testClass.setFeatures(features);
		assertEquals(ANY_ID, testClass.getId());
		assertEquals(ANY_SYSTEM_NAME, testClass.getSystemName());
		assertEquals(1, testClass.getFeatures().size());
		assertEquals(features, testClass.getFeatures());
		assertEquals(ANY_FEATURE_NAME, testClass.getFeatures().get(0));
	}
}
