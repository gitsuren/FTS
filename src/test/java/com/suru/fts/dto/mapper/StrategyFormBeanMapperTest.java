package com.suru.fts.dto.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.dto.StrategyFormBean;
import com.suru.fts.dto.mapper.StrategyFormBeanMapper;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;

@RunWith(MockitoJUnitRunner.class)
public class StrategyFormBeanMapperTest {

	private static final String ANY_SYSTEM = "system";
	private static final String ANY_FEATURE = "feature";
	private static final String ANY_FEATURE_STRATEGY = "featureStrategy";
	@InjectMocks
	private StrategyFormBeanMapper mapper;

	@Test
	public void testMapToBeanShouldReturnNullForBasicFeatureStrategy() {

		FeatureStrategy featureStrategy = new FeatureStrategy();

		featureStrategy.setName(ANY_FEATURE_STRATEGY);
		StrategyFormBean bean = mapper.mapToBean(featureStrategy);
		assertNull(bean);
	}

	@Test
	public void testMapToBeanForGroupStrategy() {
		ToggleSystem system = new ToggleSystem();
		system.setSystemName(ANY_SYSTEM);

		Feature feature = new Feature();
		feature.setName(ANY_FEATURE);
		feature.setSystemName("S1");

		FeatureStrategy featureStrategy = new GroupStrategy();
		featureStrategy.setFeatureName(ANY_FEATURE);
		featureStrategy.setSystemName(ANY_SYSTEM);

		featureStrategy.setName(ANY_FEATURE_STRATEGY);
		StrategyFormBean bean = mapper.mapToBean(featureStrategy);
		assertEquals(ANY_FEATURE_STRATEGY, bean.getStrategyName());
		assertEquals("/admin/system/system/feature/feature/strategy/group/featureStrategy", bean.getDetailHref());
		assertEquals("/admin/system/system/feature/feature/strategy/group/featureStrategy/delete",
				bean.getDeleteHref());
		assertEquals("GROUP", bean.getStrategyType());
	}

}
