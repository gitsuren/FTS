package com.suru.fts.dto.mapper;

import org.springframework.stereotype.Component;

import com.suru.fts.dto.ToggleSystemFormBean;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.util.BeanMapper;

@Component
public class ToggleSystemFormBeanMapper extends BeanMapper<ToggleSystemFormBean, ToggleSystem> {

	private static final String ADMIN_FTS_SYSTEM = "/admin/system/";

	@Override
	public ToggleSystemFormBean mapToBean(ToggleSystem toggleSystem) {
		ToggleSystemFormBean tsb = new ToggleSystemFormBean();
		tsb.setDescription(toggleSystem.getDescription());
		tsb.setSystemName(toggleSystem.getSystemName());
		tsb.setDetailHref(ADMIN_FTS_SYSTEM + tsb.getSystemName());
		tsb.setEditHref(ADMIN_FTS_SYSTEM + tsb.getSystemName() + "/edit");
		tsb.setDeleteHref(ADMIN_FTS_SYSTEM + tsb.getSystemName()+ "/delete");

		return tsb;
	}

}
