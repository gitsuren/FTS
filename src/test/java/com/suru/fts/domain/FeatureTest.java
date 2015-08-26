package com.suru.fts.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.FeatureStatus;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;

@RunWith(MockitoJUnitRunner.class)
public class FeatureTest {

	private static final String ANY_NAME = "nameeeee";
	private static final long ANY_ID = 123456;
	private static final String ANY_DESCRIPTION = "desc";

	@Test
	public void testGettersAndSetters() {
		
		FeatureStatus featureStatus = new FeatureStatus();
		featureStatus.setId(ANY_ID);
		List<FeatureStrategy> strats = new ArrayList<>();
		strats.add(new GroupStrategy());
		ToggleSystem ts = new ToggleSystem();
		Feature feature = new Feature();
		feature.setDescription(ANY_DESCRIPTION);
		feature.setFeatureStatus(featureStatus);
		//feature.setId(ANY_ID);
		feature.setName(ANY_NAME);
		feature.setStrategies(strats);
		feature.setSystemName("TEST_NAME");
		//assertEquals(ts, feature.getSystem());
		assertEquals(strats, feature.getStrategies());
		assertEquals(ANY_NAME, feature.getName());
		//assertEquals(123456, feature.getId());
		assertEquals(featureStatus, feature.getFeatureStatus());
		assertEquals(ANY_DESCRIPTION, feature.getDescription());
	}
	
	@Test
	public void testGetStrategyByNameWhereNameIsFound() {
		
		GroupStrategy gs = new GroupStrategy();
		gs.setName("gs");
		Feature feature = new Feature();
		feature.getStrategies().add(gs);
		FeatureStrategy strategy = feature.getStrategyByName("gs");
		assertEquals(gs, strategy);
	}
	
	@Test
	public void testGetStrategyByNameWhereNameIsNotFound() {
		
		GroupStrategy gs = new GroupStrategy();
		gs.setName("gs");
		Feature feature = new Feature();
		feature.getStrategies().add(gs);
		FeatureStrategy strategy = feature.getStrategyByName("ZZ");
		assertNull(strategy);
	}
}
