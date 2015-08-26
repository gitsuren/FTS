package com.suru.fts.dto.mapper;

import org.springframework.stereotype.Component;

import com.suru.fts.dto.FeatureGroupFormBean;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.util.BeanMapper;

@Component
public class FeatureGroupFormBeanMapper extends BeanMapper<FeatureGroupFormBean, FeatureGroup> {

	@Override
	public FeatureGroupFormBean mapToBean(FeatureGroup group) {
		FeatureGroupFormBean bean = new FeatureGroupFormBean();
		bean.setGroupName(group.getDescription());
		String groupUrl = "/admin/group/" + group.getDescription();
		bean.setDetailHref(groupUrl);
		bean.setDeleteHref(groupUrl + "/delete");
		bean.setAddMemberHref(groupUrl + "/members");
		bean.setRemoveFeatureGroupHref("/admin/featgroup/" + group.getDescription() + "/delete");
		return bean;
	}

}
