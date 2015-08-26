package com.suru.fts.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;

public class MemberTest {

	private static final long ANY_ID = 123456L;
	private static final String ANY_NAME = "sggsgsgsgsgssgsg";

	@Test
	public void testGettersAndSetters() {
		
		FeatureGroup featureGroup = new FeatureGroup();
		Member member = new Member();
		//member.setId(ANY_ID);
		member.setFeatureGroupName(ANY_NAME);
		member.setMemberId(ANY_NAME);
		//assertEquals(ANY_ID, member.getId());
		assertEquals(featureGroup, member.getFeatureGroupName());
		assertEquals(ANY_NAME, member.getMemberId());
	}
}
