//package com.suru.fts.domain;
//
//import static org.junit.Assert.*;
//
//import org.apache.commons.lang.builder.HashCodeBuilder;
//import org.junit.Test;
//
//import com.suru.fts.mongo.domain.IdObject;
//
//public class IdObjectTest {
//
//	private static final long ANY_ID = 132456L;
//
//	@Test
//	public void testGettersAndSetters() {
//		
//		IdObject object = new IdObject();
//		object.setId(ANY_ID);
//		assertEquals(ANY_ID, object.getId());
//	}
//	
//	@Test
//	public void testEqualsWithNullValue() {
//		
//		IdObject object = new IdObject();
//		object.setId(ANY_ID);
//		assertFalse(object.equals(null));
//	}
//	
//	@Test
//	public void testEqualsWithSameValue() {
//		
//		IdObject object = new IdObject();
//		object.setId(ANY_ID);
//		assertTrue(object.equals(object));
//	}
//
//	@Test
//	public void testEqualsWithSameIdValue() {
//		
//		IdObject object = new IdObject();
//		object.setId(ANY_ID);
//		IdObject object2 = new IdObject();
//		object2.setId(ANY_ID);
//		assertTrue(object.equals(object2));
//	}
//	
//	@Test
//	public void testEqualsWithDifferentIdValue() {
//		
//		IdObject object = new IdObject();
//		object.setId(ANY_ID);
//		IdObject object2 = new IdObject();
//		object2.setId(ANY_ID + 1);
//		assertFalse(object.equals(object2));
//	}
//	
//	@Test
//	public void testHashCode() {
//		
//		int expected = new HashCodeBuilder().append(ANY_ID).toHashCode();
//		IdObject object = new IdObject();
//		object.setId(ANY_ID);
//		assertEquals(expected, object.hashCode());
//	}
//	
//	@Test
//	public void testToString() {
//		
//		IdObject object = new IdObject();
//		object.setId(ANY_ID);
//		assertEquals("IdObject[id=132456]", object.toString());
//	}
//}
