package com.suru.fts.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;

@RunWith(MockitoJUnitRunner.class)
public class FeatureGroupTest {

	private static final String ANOTHER_MEMBER_NAME = "anotherMemberName";
	private static final String ANY_MEMBER_NAME = "anyMemberName";

	@Test
	public void testGettersAndSetters() {
		
		Set<Member> members = new HashSet<>();
		FeatureGroup fg = new FeatureGroup();
		fg.setDescription("Desc");
		//fg.setId(132465L);
		fg.setMembers(members);
		assertEquals("Desc", fg.getDescription());
		//assertEquals(132465L, fg.getId());
		assertEquals(members, fg.getMembers());	
	}
	
	@Test
	public void testGetMemberWithNullParameter() {
		
		FeatureGroup featureGroup = new FeatureGroup();
		Member result = featureGroup.getMember(null);
		assertNull(result);
	}
	
	@Test
	public void testGetMemberWithValidParameterButNothingInList() {
		
		FeatureGroup featureGroup = new FeatureGroup();
		Member result = featureGroup.getMember(ANY_MEMBER_NAME);
		assertNull(result);
	}
	
	@Test
	public void testGetMemberWithValidParameterButOtherInList() {
		
		FeatureGroup featureGroup = new FeatureGroup();
		Member member = new Member();
		member.setMemberId(ANOTHER_MEMBER_NAME);
		featureGroup.getMembers().add(member);
		Member result = featureGroup.getMember(ANY_MEMBER_NAME);
		assertNull(result);
	}

	@Test
	public void testGetMemberWithValidParameterAndInList() {
		
		FeatureGroup featureGroup = new FeatureGroup();
		Member member = new Member();
		member.setMemberId(ANY_MEMBER_NAME);
		featureGroup.getMembers().add(member);
		Member result = featureGroup.getMember(ANY_MEMBER_NAME);
		assertEquals(member, result);
	}
}
