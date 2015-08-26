package com.suru.fts;

import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.server.MockMvc;

@RunWith(MockitoJUnitRunner.class)
public abstract class MockMVCBaseTest {

	protected MockMvc mockMVC;


	@Before
	public final void setup() {
		setupBefore();
		Object[] controllersToSetup = getControllers();
		if (controllersToSetup != null && controllersToSetup.length > 0) {
			mockMVC = standaloneSetup(controllersToSetup).build();
		}
	}

	@After
	public final void tearDown() {
		tearDownBefore();
	}

	/**
	 * Hook for sub-classes
	 *
	 * @return the controllers to setup.
	 */
	public abstract Object[] getControllers();

	/**
	 * Hook for sub-classes.
	 */
	protected void setupBefore() {
	}

	/**
	 * Hook for sub-classes.
	 */
	protected void tearDownBefore() {
	}
}
