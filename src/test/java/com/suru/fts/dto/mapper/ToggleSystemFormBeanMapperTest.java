package com.suru.fts.dto.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.dto.ToggleSystemFormBean;
import com.suru.fts.dto.mapper.ToggleSystemFormBeanMapper;
import com.suru.fts.mongo.domain.ToggleSystem;

@RunWith(MockitoJUnitRunner.class)
public class ToggleSystemFormBeanMapperTest {

	@InjectMocks
	private ToggleSystemFormBeanMapper mapper;

	private static final String ANY_SYSTEM_DESCRIPTION = "My Crop Insurance System";
	private static final String ANY_SYSTEM_NAME = "MYCI";
	private static final long ANY_SYSTEM_ID = 1L;

	@Test
	public void testMapToBean() {
		ToggleSystem toggleSystem = buildToggleSystem();
		ToggleSystemFormBean tsb = mapper.mapToBean(toggleSystem);
		assertEquals(ANY_SYSTEM_NAME, tsb.getSystemName());
		assertEquals(ANY_SYSTEM_DESCRIPTION, tsb.getDescription());
		assertEquals("/admin/system/" + ANY_SYSTEM_NAME, tsb.getDetailHref());
		assertEquals("/admin/system/" + ANY_SYSTEM_NAME + "/edit",
				tsb.getEditHref());
		assertEquals("/admin/system/" + ANY_SYSTEM_NAME + "/delete",
				tsb.getDeleteHref());
	}

	@Test
	public void testMapToBeanList() {

		ToggleSystem toggleSystem = buildToggleSystem();
		List<ToggleSystem> systemList = new ArrayList<>();
		systemList.add(toggleSystem);
		List<ToggleSystemFormBean> results = mapper.mapToBean(systemList);
		assertEquals(1, results.size());
		assertEquals(ANY_SYSTEM_NAME, results.get(0).getSystemName());
		assertEquals(ANY_SYSTEM_DESCRIPTION, results.get(0).getDescription());
		assertEquals("/admin/system/" + ANY_SYSTEM_NAME, results.get(0)
				.getDetailHref());
		assertEquals("/admin/system/" + ANY_SYSTEM_NAME + "/edit",
				results.get(0).getEditHref());
		assertEquals("/admin/system/" + ANY_SYSTEM_NAME + "/delete", results
				.get(0).getDeleteHref());
	}

	@Test
	public void testMapToBeanListWithNullList() {

		List<ToggleSystem> nullList = null;
		List<ToggleSystemFormBean> results = mapper.mapToBean(nullList);
		assertNull(results);
	}

	private ToggleSystem buildToggleSystem() {
		ToggleSystem toggleSystem = new ToggleSystem();
		toggleSystem.setSystemName(ANY_SYSTEM_NAME);
		toggleSystem.setDescription(ANY_SYSTEM_DESCRIPTION);
		//toggleSystem.setId(ANY_SYSTEM_ID);
		return toggleSystem;
	}
}