package com.suru.fts.dto.mapper;

import com.suru.fts.dto.FeatureFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.FeatureStatus;
import com.suru.fts.mongo.domain.ToggleSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FeatureFormBeanMapperTest {
	
	private static final String ANY_FEATURE_STATUS_NAME = "anyFeatureStatusName";
	private static final String ANY_SYSTEM_NAME = "TestSystem";
	private static final String ANY_SYSTEM_DESCRIPTION = "This is a test syste";
	private static final String ANY_FEATURE_NAME = "TestFeature";
	private static final String ANY_FEATURE_DESCRIPTION = "This is a test feature";
	private static final long ANY_FEATURE_ID = 123;
	
	@InjectMocks
	private FeatureFormBeanMapper featureBeanMapper;
	
	@Test
	public void testMapToBean(){
		Feature feature = buildFeature();
		FeatureFormBean bean = featureBeanMapper.mapToBean(feature);
		assertEquals(ANY_FEATURE_NAME, bean.getFeatureName());
		assertEquals(ANY_FEATURE_DESCRIPTION, bean.getDescription());
		assertEquals(ANY_FEATURE_STATUS_NAME, bean.getStatus());
		assertEquals("S1", bean.getSystemName());
		String uri = "/admin/system/S1/feature/TestFeature";
		assertEquals(uri, bean.getDetailHref());
		assertEquals(uri + "/delete", bean.getDeleteHref());
		assertEquals(uri + "/edit", bean.getEditHref());
		assertEquals(uri + "/strategies/add", bean.getAddStrategyHref());
		
	}
	
	private Feature buildFeature() {
		FeatureStatus status = new FeatureStatus();
		status.setName(ANY_FEATURE_STATUS_NAME);
		Feature feature = new Feature();
		//feature.setId(ANY_FEATURE_ID);
		feature.setFeatureStatus(status);
		feature.setDescription(ANY_FEATURE_DESCRIPTION);
		feature.setName(ANY_FEATURE_NAME);
		
		ToggleSystem system = new ToggleSystem();
		system.setSystemName(ANY_SYSTEM_NAME);
		system.setDescription(ANY_SYSTEM_DESCRIPTION);
//		feature.setSystem(system);
		feature.setSystemName("S1");
		return feature;
	}

}
