package com.suru.fts.dto.mapper;

import org.springframework.stereotype.Component;

import com.suru.fts.dto.FeatureFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.util.BeanMapper;

@Component
public class FeatureFormBeanMapper extends BeanMapper<FeatureFormBean, Feature>{

	@Override
	public FeatureFormBean mapToBean(Feature feature) {
		FeatureFormBean featureBean = new FeatureFormBean();
		
		String featureName = feature.getName();
		featureBean.setFeatureName(featureName);
		featureBean.setDescription(feature.getDescription());
		featureBean.setStatus(String.valueOf(feature.getFeatureStatus().getName()));
		String toggleSystemName = feature.getSystemName();
		featureBean.setSystemName(toggleSystemName);
		String detailHref = "/admin/system/" + toggleSystemName + "/feature/" + featureName;
		featureBean.setDetailHref(detailHref);
		featureBean.setEditHref(detailHref + "/edit");
		featureBean.setDeleteHref(detailHref + "/delete");
		featureBean.setAddStrategyHref(detailHref + "/strategies/add");
		
		return featureBean;
	}

}
