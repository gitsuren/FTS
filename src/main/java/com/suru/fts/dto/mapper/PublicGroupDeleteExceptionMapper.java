package com.suru.fts.dto.mapper;

import org.springframework.stereotype.Component;

import com.suru.fts.dto.PublicGroupDeleteExceptionFormBean;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.util.BeanMapper;

@Component
public class PublicGroupDeleteExceptionMapper extends BeanMapper<PublicGroupDeleteExceptionFormBean, GroupStrategy> {

	@Override
	public PublicGroupDeleteExceptionFormBean mapToBean(final GroupStrategy groupStrategy) {
		
		PublicGroupDeleteExceptionFormBean result = new PublicGroupDeleteExceptionFormBean();
		result.setFeatureName(groupStrategy.getFeatureName());
		result.setSystemName(groupStrategy.getSystemName());
//		result.setSystemName(groupStrategy.getFeature().getSystem().getSystemName());
		result.setStrategyName(groupStrategy.getName());
		return result;
	}

}
