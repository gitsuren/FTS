package com.suru.fts.dto.mapper;

import com.suru.fts.dto.MemberFormBean;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MemberFormBeanMapperTest {
	
	private static final String DESCRIPTION = "description";
	private static final String MEMBER_ID = "sb95447";
	private static final String TESTERS = "TESTERS";
	@InjectMocks
	private MemberFormBeanMapper mapper;
	
	@Test
	public void testMapToBean(){
		Member member = new Member();
		member.setMemberId(MEMBER_ID);
		
		FeatureGroup group = new FeatureGroup();
		group.setDescription(DESCRIPTION);		
		member.setFeatureGroupName(TESTERS);
		
		MemberFormBean bean = mapper.mapToBean(member);
		assertEquals(MEMBER_ID, bean.getMemberId());
		assertEquals("/admin/group/TESTERS/member/sb95447/delete", bean.getDeleteHref());
	}

}
