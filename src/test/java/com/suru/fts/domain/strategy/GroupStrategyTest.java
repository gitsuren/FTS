package com.suru.fts.domain.strategy;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;


@RunWith(MockitoJUnitRunner.class)
public class GroupStrategyTest {

	private static final String ANOTHER_GROUP_NAME = "anotherGroupName";
	private static final String ANY_GROUP_NAME = "anyGroupName";


	@Test
	public void testGetGroupWhereNotGroupsExist() {

		GroupStrategy groupStrategy = new GroupStrategy();
		FeatureGroup result = groupStrategy.getGroup(ANY_GROUP_NAME);
		assertNull(result);
	}


	@Test
	public void testGetGroupWhereGroupsExistButNotTheGroupWeWant() {

		GroupStrategy groupStrategy = new GroupStrategy();
		FeatureGroup group1 = new FeatureGroup();
		group1.setDescription(ANOTHER_GROUP_NAME);
		groupStrategy.getGroups().add(group1);
		FeatureGroup result = groupStrategy.getGroup(ANY_GROUP_NAME);
		assertNull(result);
	}
	
	@Test
	public void testGetGroupWhereGroupsExistAndContainsTheGroupWeWant() {

		GroupStrategy groupStrategy = new GroupStrategy();
		FeatureGroup group1 = new FeatureGroup();
		group1.setDescription(ANY_GROUP_NAME);
		groupStrategy.getGroups().add(group1);
		FeatureGroup result = groupStrategy.getGroup(ANY_GROUP_NAME);
		assertEquals(ANY_GROUP_NAME, result.getDescription());
	}
}
