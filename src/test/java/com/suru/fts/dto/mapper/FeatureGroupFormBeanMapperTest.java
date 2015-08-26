package com.suru.fts.dto.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.dto.FeatureGroupFormBean;
import com.suru.fts.dto.mapper.FeatureGroupFormBeanMapper;
import com.suru.fts.mongo.domain.FeatureGroup;


@RunWith(MockitoJUnitRunner.class)
public class FeatureGroupFormBeanMapperTest {
	
	private static final String ANY_FEATURE_GROUP_DESC = "TEST";
	@InjectMocks
	private FeatureGroupFormBeanMapper mapper;
	
	@Test
	public void testMaptoBean(){
		FeatureGroup group = new FeatureGroup();
		group.setDescription(ANY_FEATURE_GROUP_DESC);
		FeatureGroupFormBean bean = mapper.mapToBean(group);
		assertEquals(ANY_FEATURE_GROUP_DESC, bean.getGroupName());
		assertEquals("/admin/group/TEST",bean.getDetailHref());
		assertEquals("/admin/group/TEST/delete",bean.getDeleteHref());
		assertEquals("/admin/group/TEST/members",bean.getAddMemberHref());
		assertEquals("/admin/featgroup/TEST/delete",bean.getRemoveFeatureGroupHref());
	}

}
