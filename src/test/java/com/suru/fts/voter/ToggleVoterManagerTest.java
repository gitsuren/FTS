package com.suru.fts.voter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;


@RunWith(MockitoJUnitRunner.class)
public class ToggleVoterManagerTest {

	private ToggleVoterManager classToTest = new ToggleVoterManager();


	@Before
	public void setup() {

		List<ToggleVoter<?>> voters = new ArrayList<>();
		voters.add(new GroupVoter());
		classToTest.getToggleVoters().put("com.suru.fts.mongo.domain.strategy.GroupStrategy", voters);
	}


	@Test
	public void testGetVoter() {

		FeatureStrategy featureStrategy = new GroupStrategy();
		List<ToggleVoter<?>> voters = classToTest.getVoters(featureStrategy);
		assertNotNull(voters);
	}
	
	@Test
	public void testGettersAndSetters() {
		
		List<ToggleVoter<?>> voters = new ArrayList<>();
		voters.add(new GroupVoter());
		Map<String, List<ToggleVoter<?>>> voterMap = new HashMap<String, List<ToggleVoter<?>>>();
		voterMap.put("com.suru.myci.toggle.domain.strategy.GroupStrategy", voters);
		classToTest.setToggleVoters(voterMap);
		assertEquals(classToTest.getToggleVoters(), voterMap);
	}
}
