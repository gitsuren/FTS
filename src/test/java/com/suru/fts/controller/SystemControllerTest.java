package com.suru.fts.controller;

import com.suru.fts.MockMVCBaseTest;
import com.suru.fts.api.exception.NoSuchMethodException;
import com.suru.fts.api.exception.ResourceNotFoundException;
import com.suru.fts.dto.ToggleSystemFormBean;
import com.suru.fts.model.ViewModelBuilder;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.service.ToggleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(MockitoJUnitRunner.class)
public class SystemControllerTest extends MockMVCBaseTest {

	private static final String SM_USER = "SM_USER";
	private static final String ANY_USER_ID = "anyUserId";
	private static final String NO_SUCH_METHOD_EXCEPTION_TEXT = "NoSuchMethodExceptionText";
	private static final String ANY_RESOURCE_NOT_FOUND_MESSAGE = "testing 1...2...3";
	private static final String SYSTEM_VIEW_NAME = "testView1000";
	private static final String SYSTEMS_VIEW_NAME = "blah";
	private static final String SYSTEMS_CREATE_VIEW_NAME = "blahCreate";
	private static final String SHOW_DELETE_CONFIRMATION_VIEW = "deleteConfirmation";

	private static final String ANY_SYSTEM_NAME = "testSystem";

	@InjectMocks
	private SystemController classToTest;

	@Mock
	private ToggleService mockToggleService;

	@Mock
	private ViewModelBuilder mockToggleModelBuilder;

	@Mock
	private ToggleSystem mockToggleSystem;

	@Mock
	private HttpServletRequest mockRequest;
	
	@Override
	public Object[] getControllers() {

		return new Object[] { classToTest };
	}


	@Test
	public void testGetSystemWhereSystemNameIsNotFound() throws Exception {

		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(null);
		mockMVC.perform(get("/admin/testSystem")).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGetSystemWhereSystemNameIsNotFoundToVerifyCorrectException() {
		
		classToTest.getSystem(ANY_SYSTEM_NAME);
		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(null);
	}


	@Test
	public void testGetSystemWhenSystemIsValid() throws Exception {

		ModelAndView modelAndView = new ModelAndView(SYSTEM_VIEW_NAME);
		when(mockToggleService.getSystem(any(String.class))).thenReturn(mockToggleSystem);
		when(mockToggleModelBuilder.buildSystemModel(mockToggleSystem)).thenReturn(modelAndView);

		mockMVC.perform(get("/admin/system/testSystem")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(SYSTEM_VIEW_NAME));

		verify(mockToggleService).getSystem(ANY_SYSTEM_NAME);
		verify(mockToggleModelBuilder).buildSystemModel(mockToggleSystem);
	}


	@Test
	public void testHandleResourceNotFoundException() {
		ResourceNotFoundException exception = new ResourceNotFoundException(ANY_RESOURCE_NOT_FOUND_MESSAGE);
		String result = classToTest.handleResourceNotFoundException(exception);
		assertEquals(ANY_RESOURCE_NOT_FOUND_MESSAGE, result);
	}
	
	@Test
	public void testHandleNoSuchMethodError() {
		
		NoSuchMethodException exception = new NoSuchMethodException(NO_SUCH_METHOD_EXCEPTION_TEXT);
		String result = classToTest.handleNoSuchMethodError(exception);
		assertEquals(NO_SUCH_METHOD_EXCEPTION_TEXT, result);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testGetSystems() throws Exception {

		ModelAndView modelAndView = new ModelAndView(SYSTEMS_VIEW_NAME);
		List<ToggleSystem> toggleSystems = new ArrayList<>();
		toggleSystems.add(mockToggleSystem);
		when(mockToggleService.getActiveSystems()).thenReturn(toggleSystems);
		when(mockToggleModelBuilder.buildSystemsModel(any(List.class))).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/systems")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(SYSTEMS_VIEW_NAME));
		verify(mockToggleService).getActiveSystems();
		verify(mockToggleModelBuilder).buildSystemsModel(any(List.class));
	}


	@Test
	public void testBeginCreate() throws Exception {

		ModelAndView modelAndView = new ModelAndView(SYSTEMS_CREATE_VIEW_NAME);
		when(mockToggleModelBuilder.buildSystemCreateModel(false, null)).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/systems/create")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(SYSTEMS_CREATE_VIEW_NAME));
		verify(mockToggleModelBuilder).buildSystemCreateModel(false, null);
	}


	@Test
	public void testAddSystem() throws Exception {

		String systemName = "TestSystem";
		String desc = "any description";
		ArgumentCaptor<ToggleSystemFormBean> tsfb=ArgumentCaptor.forClass(ToggleSystemFormBean.class);
		when(mockToggleService.createSystem(tsfb.capture(), isA(String.class))).thenReturn(mockToggleSystem);
		when(mockToggleSystem.getSystemName()).thenReturn(systemName);
		mockMVC.perform(post("/admin/systems/create").param("systemName", systemName).param("description", desc)).andExpect(status().is(HttpStatus.FOUND.value())).andExpect(view().name("redirect:/admin/system/TestSystem"));
		verify(mockToggleService).createSystem(tsfb.capture(), isA(String.class));
		assertEquals(systemName, tsfb.getValue().getSystemName());
		assertEquals(desc, tsfb.getValue().getDescription());
	}


	@Test
	public void testdeleteSystemPostAction() throws Exception {

		when(mockToggleService.getSystem("123435")).thenReturn(mockToggleSystem);
		mockMVC.perform(post("/admin/system/delete?systemName=123435").param("action", "delete")).andExpect(status().is(HttpStatus.FOUND.value()))
				.andExpect(view().name("redirect:/admin"));
		verify(mockToggleService, times(1)).deleteSystem(mockToggleSystem);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteSystemToVerifyExceptionIsThrownWhenSystemIsNull() throws Exception {

		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(null);
		classToTest.deleteSystemPostAction(ANY_SYSTEM_NAME, "delete");
	}

	@Test
	public void testdeleteSystemPostActionOnCancel() throws Exception {

		mockMVC.perform(post("/admin/system/delete?systemName=123435").param("action", "cancel")).andExpect(status().is(HttpStatus.FOUND.value()))
				.andExpect(view().name("redirect:/admin"));
	}

	@Test
	public void testdeleteSystemPostActionWIthInvalidAction() throws Exception {

		mockMVC.perform(post("/admin/system/delete?systemName=123435").param("action", "junk")).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testShowDeleteConfirmation() throws Exception {

		ModelAndView modelAndView = new ModelAndView(SHOW_DELETE_CONFIRMATION_VIEW);
		when(mockToggleModelBuilder.buildDeleteConfirmationView(eq("123456"), any(String.class), any(String.class))).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/system/123456/delete")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(view().name(SHOW_DELETE_CONFIRMATION_VIEW));
	}


	@Test
	public void testUpdateSystem() throws Exception {

		String systemName = "TestSystem";
		String desc = "any description";
		String viewName = "redirect:/admin/system/TestSystem";
		ArgumentCaptor<ToggleSystemFormBean> tsfb=ArgumentCaptor.forClass(ToggleSystemFormBean.class);
		//TODO : fix me - is it really FOUND or OK?? test web hook as well
		mockMVC.perform(post("/admin/system/TestSystem/edit").param("systemName", "TestSystem").param("description", "any description")).andExpect(status().is(HttpStatus.FOUND.value())).andExpect(view().name(viewName));
		verify(mockToggleService).updateSystem(tsfb.capture(), isA(String.class));
		assertEquals(systemName, tsfb.getValue().getSystemName());
		assertEquals(desc, tsfb.getValue().getDescription());
	}


	@Test
	public void testEditSystem() throws Exception {

		ModelAndView modelAndView = new ModelAndView(SYSTEM_VIEW_NAME);
		when(mockToggleService.getSystem("TestSystem")).thenReturn(mockToggleSystem);
		when(mockToggleModelBuilder.buildSystemCreateModel(true, "TestSystem")).thenReturn(modelAndView);
		mockMVC.perform(get("/admin/system/TestSystem/edit")).andExpect(status().is(HttpStatus.OK.value())).andExpect(view().name(SYSTEM_VIEW_NAME));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testEditSystemToVerifyException() {

		when(mockToggleService.getSystem(ANY_SYSTEM_NAME)).thenReturn(null);
		classToTest.editSystem(ANY_SYSTEM_NAME);
	}
	
	@Test
	public void testGetUserIdWithValidId() {
		
		when(mockRequest.getHeader(SM_USER)).thenReturn(ANY_USER_ID);
		String result = classToTest.getUserId(mockRequest);
		assertEquals(ANY_USER_ID, result);
		verify(mockRequest).getHeader(SM_USER);
	}
}
