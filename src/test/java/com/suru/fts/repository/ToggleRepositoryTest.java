//package com.suru.fts.repository;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.hibernate.Criteria;
//import org.hibernate.SessionFactory;
//import org.hibernate.classic.Session;
//import org.hibernate.criterion.Criterion;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.suru.fts.mongo.domain.FeatureGroup;
//import com.suru.fts.mongo.domain.FeatureStatus;
//import com.suru.fts.mongo.domain.ToggleSystem;
//import com.suru.fts.mongo.domain.strategy.GroupStrategy;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class ToggleRepositoryTest {
//
//	private static final long ANY_GROUP_ID = 12345L;
//	private static final String ANY_FEATURE_STATUS_NAME = "anyFeatureStatusName";
//	private static final long ANY_FEATURE_STATUS_ID = 1L;
//	
//	@InjectMocks
//	private ToggleRepository classToTest;
//
//	@Mock
//	private SessionFactory mockSessionFactory;
//
//	@Mock
//	private Session mockSession;
//
//	@Mock
//	private Criteria mockCriteria;
//
//
//	@Test
//	public void testgetSystemBySystemName() {
//
//		ToggleSystem expectedSystem = new ToggleSystem();
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		when(mockSession.createCriteria(ToggleSystem.class)).thenReturn(mockCriteria);
//		when(mockCriteria.uniqueResult()).thenReturn(expectedSystem);
//		ArgumentCaptor<Criterion> criterionCaptor = ArgumentCaptor.forClass(Criterion.class);
//		ToggleSystem system = classToTest.getSystemByName("testSystem");
//		verify(mockCriteria).add(criterionCaptor.capture());
//		assertEquals("systemName=testSystem", criterionCaptor.getValue().toString());
//		assertEquals(expectedSystem, system);
//	}
//
//
//	@Test
//	public void testSaveForToggleSystem() {
//
//		ToggleSystem testSystem = new ToggleSystem();
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		classToTest.save(testSystem);
//		verify(mockSession).saveOrUpdate(testSystem);
//	}
//
//
//	@Test
//	public void testSaveForFeatureGroup() {
//
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		FeatureGroup group = new FeatureGroup();
//		classToTest.save(group);
//		verify(mockSession).saveOrUpdate(group);
//	}
//
//
//	@Test
//	public void testGetAllSystems() {
//
//		List<ToggleSystem> systems = new ArrayList<>();
//		systems.add(new ToggleSystem());
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		when(mockSession.createCriteria(ToggleSystem.class)).thenReturn(mockCriteria);
//		when(mockCriteria.list()).thenReturn(systems);
//		List<ToggleSystem> results = classToTest.getAllSystems();
//		assertEquals(1, results.size());
//		verify(mockCriteria).list();
//	}
//
//
//	@Test
//	public void testDeleteSystem() {
//
//		ToggleSystem toggleSystem = new ToggleSystem();
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		classToTest.deleteSystem(toggleSystem);
//		verify(mockSession).delete(toggleSystem);
//	}
//
//
//	@Test
//	public void testGetGroupByName() {
//		FeatureGroup featureGroup = new FeatureGroup();
//		String description = "test desc";
//		featureGroup.setDescription(description);
//		featureGroup.setId(123456L);
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		when(mockSession.createCriteria(FeatureGroup.class)).thenReturn(mockCriteria);
//		when(mockCriteria.uniqueResult()).thenReturn(featureGroup);
//		FeatureGroup group = classToTest.getGroupByName("group1");
//		assertEquals(featureGroup, group);
//	}
//	
//	@Test
//	public void testDeleteGroup() {
//		
//		FeatureGroup featureGroup = new FeatureGroup();
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		classToTest.deleteGroup(featureGroup);
//		verify(mockSession).delete(featureGroup);
//	}
//	
//	@Test
//	public void testGetAllGroups() {
//		
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		when(mockSession.createCriteria(FeatureGroup.class)).thenReturn(mockCriteria);
//		List<FeatureGroup> groupList = new ArrayList<>();
//		when(mockCriteria.list()).thenReturn(groupList );
//		List<FeatureGroup> groups = classToTest.getAllGroups();
//		verify(mockCriteria).list();
//		assertEquals(groupList, groups);
//		
//	}
//	
//	@Test
//	public void testGetFeatureStatusByName() {
//	
//		FeatureStatus expectedStatus = new FeatureStatus();
//		expectedStatus.setName(ANY_FEATURE_STATUS_NAME);
//		expectedStatus.setId(ANY_FEATURE_STATUS_ID);
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		when(mockCriteria.uniqueResult()).thenReturn(expectedStatus);
//		when(mockSession.createCriteria(FeatureStatus.class)).thenReturn(mockCriteria);
//		ArgumentCaptor<Criterion> criterionCaptor = ArgumentCaptor.forClass(Criterion.class);
//		FeatureStatus result = classToTest.getFeatureStatusByName(ANY_FEATURE_STATUS_NAME);
//		verify(mockCriteria).add(criterionCaptor.capture());
//		assertEquals(ANY_FEATURE_STATUS_ID, result.getId());
//		assertEquals(ANY_FEATURE_STATUS_NAME, result.getName());
//		assertEquals("name=anyFeatureStatusName", criterionCaptor.getValue().toString());
//	}
//	
//	@Test
//	public void testGetFeatureStatusById() {
//
//		FeatureStatus expectedStatus = new FeatureStatus();
//		expectedStatus.setName(ANY_FEATURE_STATUS_NAME);
//		expectedStatus.setId(ANY_FEATURE_STATUS_ID);
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		when(mockSession.get(FeatureStatus.class, Long.valueOf(ANY_FEATURE_STATUS_ID))).thenReturn(expectedStatus);
//		FeatureStatus result = classToTest.getFeatureStatusById(ANY_FEATURE_STATUS_ID);
//		assertEquals(ANY_FEATURE_STATUS_ID, result.getId());
//		assertEquals(ANY_FEATURE_STATUS_NAME, result.getName());
//	}
//	
//	@Test
//	public void testGetStratagiesByGroupWhenStratagiesExist() {
//		
//		List<GroupStrategy> expectedList = new ArrayList<>();
//		GroupStrategy groupStrat = new GroupStrategy();
//		expectedList.add(groupStrat);
//		FeatureGroup featureGroup = new FeatureGroup();
//		featureGroup.setId(ANY_GROUP_ID);
//		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);
//		when(mockSession.createCriteria(GroupStrategy.class)).thenReturn(mockCriteria);
//		ArgumentCaptor<Criterion> criterionCaptor = ArgumentCaptor.forClass(Criterion.class);
//		when(mockCriteria.list()).thenReturn(expectedList);
//		List<GroupStrategy> results = classToTest.getStratagiesByGroup(featureGroup);
//		verify(mockCriteria).add(criterionCaptor.capture());
//		assertEquals(1, results.size());
//		assertEquals("groups.id=12345", criterionCaptor.getValue().toString());
//	}
//}
