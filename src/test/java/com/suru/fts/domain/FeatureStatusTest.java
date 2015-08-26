//package com.suru.fts.domain;
//
//import static org.junit.Assert.*;
//
//import java.sql.Timestamp;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.suru.fts.mongo.domain.FeatureStatus;
//
//@RunWith(MockitoJUnitRunner.class)
//public class FeatureStatusTest {
//
//	private static final long ANY_ID = 1L;
//	private FeatureStatus featureStatus;
//	
//	@Before
//	public void setup() {
//	
//		featureStatus = new FeatureStatus();
//		featureStatus.setCreatedBy("anyCreatedBy");
//		featureStatus.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		featureStatus.setId(ANY_ID);
//		featureStatus.setName("anyStatusName");
//		featureStatus.setUpdatedBy("anyUpdatedBy");
//		featureStatus.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
//	}
//	
//	@Test
//	public void testEqualsMethodWhenObjectPassedIsEqual() {
//	
//		boolean result = featureStatus.equals(featureStatus);
//		assertTrue(result);
//	}
//	
//	@Test
//	public void testEqualsMethodWhenObjectPassedIsNull() {
//	
//		FeatureStatus testObject = null;
//		boolean result = featureStatus.equals(testObject);
//		assertFalse(result);
//	}
//	
//	@Test
//	public void testHashCode() {
//		
//		int expectedResult = featureStatus.hashCode();
//		FeatureStatus testObject = new FeatureStatus();
//		testObject.setId(ANY_ID);
//		int foundResult = testObject.hashCode();
//		assertEquals(expectedResult, foundResult);
//	}
//}
