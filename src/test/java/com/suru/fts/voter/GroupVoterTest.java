package com.suru.fts.voter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;

@RunWith(MockitoJUnitRunner.class)
public class GroupVoterTest {

	private static final String ANY_VALID_ID = "tester1";
	private static final String INVALID_ID = "tester2";
	
	@InjectMocks
	private GroupVoter classToTest;
	
	@Test
	public void testVoteWithValidIdForGroup() {
		
		GroupStrategy featureStrategy = buildGroupStrategyForId(ANY_VALID_ID);
		boolean result = classToTest.vote(ANY_VALID_ID, featureStrategy);
		assertTrue(result);
	}
	
	@Test
	public void testVoteWithIdNotInGroup() {
		
		GroupStrategy featureStrategy = buildGroupStrategyForId(ANY_VALID_ID);
		boolean result = classToTest.vote(INVALID_ID, featureStrategy);
		assertFalse(result);
	}

	@Test
	public void testVoteWithWhenNoGroupsExist() {
		
		GroupStrategy featureStrategy = buildGroupStrategyWithNoFeatureGroups();
		boolean result = classToTest.vote(INVALID_ID, featureStrategy);
		assertFalse(result);
	}
	
	private GroupStrategy buildGroupStrategyForId(final String id) {
		GroupStrategy featureStrategy = new GroupStrategy();
		FeatureGroup featureGroup = new FeatureGroup();
		Member member = new Member();
		member.setMemberId(id);
		featureGroup.getMembers().add(member);
		featureStrategy.getGroups().add(featureGroup);
		return featureStrategy;
	}
	
	private GroupStrategy buildGroupStrategyWithNoFeatureGroups() {
		GroupStrategy featureStrategy = new GroupStrategy();
		return featureStrategy;
	}
}
