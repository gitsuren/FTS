package com.suru.fts.dto.mapper;

import org.springframework.stereotype.Component;

import com.suru.fts.dto.GroupStrategyFormBean;
import com.suru.fts.dto.StrategyFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.util.BeanMapper;

@Component
public class StrategyFormBeanMapper extends BeanMapper<StrategyFormBean, FeatureStrategy> {

	@Override
	public StrategyFormBean mapToBean(FeatureStrategy featureStrategy) {

		StrategyFormBean bean = null;
		if(featureStrategy instanceof GroupStrategy){
			bean = new GroupStrategyFormBean();
			bean.setStrategyName(featureStrategy.getName());
//			Feature feature = featureStrategy.getFeature();
			String mainUrl = "/admin/system/" + featureStrategy.getSystemName() + "/feature/" + featureStrategy.getFeatureName()
					+ "/strategy";
//			String mainUrl = "/admin/system/" + feature.getSystem().getSystemName() + "/feature/" + feature.getName()
//					+ "/strategy";
			String detailHref = mainUrl + "/group/" + featureStrategy.getName();
			bean.setDetailHref(detailHref);
			bean.setDeleteHref(detailHref + "/delete");
		}
		return bean;		
	}
}
