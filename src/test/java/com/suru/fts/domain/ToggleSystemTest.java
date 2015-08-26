package com.suru.fts.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.ToggleSystem;

public class ToggleSystemTest {

	private static final String EXISTING_FEATURE_NAME = "existingFeatureName";
	private static final String ANY_FEATURE_NAME = "anyFeatureName";
	private static final long ANY_ID = 132456L;
	private static final String ANY_SYSTEM_NAME = "anysystem Name";
	private static final String ANY_DESC = "any desc";

	@Test
	public void testGettersAndSetters() {
		
		List<Feature> featureSet = new ArrayList<>();
		ToggleSystem toggleSystem = new ToggleSystem();
		toggleSystem.setDescription(ANY_DESC);
		toggleSystem.setFeatures(featureSet);
		//toggleSystem.setId(ANY_ID);
		toggleSystem.setSystemName(ANY_SYSTEM_NAME);
		assertEquals(ANY_DESC, toggleSystem.getDescription());
		assertEquals(featureSet, toggleSystem.getFeatures());
		//assertEquals(ANY_ID, toggleSystem.getId());
		assertEquals(ANY_SYSTEM_NAME, toggleSystem.getSystemName());
	}
	
	@Test
	public void testGetFeatureByNameWhereNotFound() {
		
		ToggleSystem system = new ToggleSystem();
		Feature result = system.getFeatureByName(ANY_FEATURE_NAME);
		assertNull(result);
	}
	
	@Test
	public void testGetFeatureByNameWhereFeatureNameIsNull() {
		
		ToggleSystem system = new ToggleSystem();
		Feature result = system.getFeatureByName(null);
		assertNull(result);
	}
	
	@Test
	public void testGetFeatureByNameWhereWhereSystemHasFeatureButNotTheOneWeAreLookingFor() {
		
		ToggleSystem system = new ToggleSystem();
		Feature feature = new Feature();
		feature.setName(EXISTING_FEATURE_NAME);
		system.getFeatures().add(feature);
		Feature result = system.getFeatureByName(ANY_FEATURE_NAME);
		assertNull(result);
	}
	
	@Test
	public void testGetFeatureByNameWhereWhereSystemHasFeatureAndItIsWhatWeAreLookingFor() {
		
		ToggleSystem system = new ToggleSystem();
		Feature feature = new Feature();
		feature.setName(ANY_FEATURE_NAME);
		system.getFeatures().add(feature);
		Feature result = system.getFeatureByName(ANY_FEATURE_NAME);
		assertEquals(feature, result);
	}
}
