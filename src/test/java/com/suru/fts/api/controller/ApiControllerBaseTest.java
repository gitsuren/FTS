package com.suru.fts.api.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.fts.api.exception.ResourceNotFoundException;
import com.suru.fts.api.controller.ApiControllerBase;

@RunWith(MockitoJUnitRunner.class)
public class ApiControllerBaseTest {

	private static final String ANY_MESSAGE_TEXT = "anyMessageText";
	
	@InjectMocks
	private ApiControllerBase classToTest;
	
	@Test
	public void testHandleResourceNotFoundException() {
		
		ResourceNotFoundException resourceException = new ResourceNotFoundException(ANY_MESSAGE_TEXT);
		String result = classToTest.handleResourceNotFoundException(resourceException);
		assertEquals(ANY_MESSAGE_TEXT, result);
	}
}
