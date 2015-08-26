//package com.suru.fts.service;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.instanceOf;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.isA;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyZeroInteractions;
//import static org.mockito.Mockito.when;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.persistence.EntityNotFoundException;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.suru.fts.dto.FeatureFormBean;
//import com.suru.fts.dto.FeatureGroupFormBean;
//import com.suru.fts.dto.GroupStrategyFormBean;
//import com.suru.fts.dto.MemberFormBean;
//import com.suru.fts.dto.StrategyFormBean;
//import com.suru.fts.dto.ToggleSystemFormBean;
//import com.suru.fts.mongo.domain.Feature;
//import com.suru.fts.mongo.domain.FeatureGroup;
//import com.suru.fts.mongo.domain.FeatureStatus;
//import com.suru.fts.mongo.domain.Member;
//import com.suru.fts.mongo.domain.ToggleSystem;
//import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
//import com.suru.fts.mongo.domain.strategy.GroupStrategy;
//import com.suru.fts.repository.ToggleRepository;
//import com.suru.fts.voter.GroupVoter;
//import com.suru.fts.voter.ToggleVoter;
//import com.suru.fts.voter.ToggleVoterManager;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class ToggleServiceTest {
//
//	private static final String ANY_USER_ID = "anyUserId";
//	private static final String FEATURE_STATUS_LIMITED_NAME = "featureStatusLimitedName";
//	private static final String FEATURE_STATUS_RELEASED_NAME = "RELEASED";
//	private static final String FEATURE_STATUS_NOT_RELEASED_NAME = "NOT_RELEASED";
//	private static final String ANY_MEMBER_NAME = "anyMemberName";
//	private static final long MEMBER_ID_2_LONG = 456789L;
//	private static final long MEMBER_ID_1_LONG = 123454L;
//	private static final String MEMBER_ID2 = "memberId2";
//	private static final String MEMBER_ID1 = "memberId1";
//	private static final String ORIGINAL_GROUP_NAME = "originalGroupName";
//	private static final String NEW_GROUP_NAME = "newGroupName";
//	private static final String ANY_GROUP_NAME = "anyGroupName";
//	private static final String ANY_GROUP_STRATEGY_NAME = "any group strategy name";
//	private static final String ANY_GROUP_STRAT_NAME = "group-strat-name";
//	private static final String GROUP_STRATEGY_NAME = "GROUP";
//	private static final String NEW_SYSTEM_DESC = "newSystemDEsc";
//	private static final String ANY_FEATURE_DESC = "YoYo";
//	private static final String NEW_FEATURE_DESC = "YoYoYO";
//	private static final String ANY_SYSTEM_DESCRIPTION = "hdgfhdgfhdgfhgdhf";
//	private static final String ID_THAT_DOES_NOT_QUALIFY = "jshdjshdjshdjhdjshdj";
//	private static final String ANY_FEATURE_NAME = "feature1";
//	private static final String ANY_ID = "LADIESMAN217";
//	private static final String ANY_SYSTEM_NAME = "testSystem";
//
//	@InjectMocks
//	private ToggleService classToTest;
//
//	@Mock
//	private ToggleRepository mockToggleRepository;
//
//	@Mock
//	private ToggleVoterManager mockToggleVoterManager;
//
//	@Mock
//	private ToggleSystem mockToggleSystem;
//
//
//	@Test
//	public void testGetFeaturesForIdWithValidSystemWithNoFeatures() {
//
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
//		Set<String> results = classToTest.getFeaturesForId(ANY_SYSTEM_NAME, ANY_ID);
//		assertEquals(0, results.size());
//		verifyZeroInteractions(mockToggleVoterManager);
//	}
//
//
//	@Test
//	public void testGetFeaturesForIdWithValidSystemWithOneFeatureNotReleased() {
//
//		Set<Feature> featureSetWithNotReleasedFeature = createFeatureSetWithNotReleasedFeature();
//		when(mockToggleSystem.getFeatures()).thenReturn(featureSetWithNotReleasedFeature);
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
//		Set<String> results = classToTest.getFeaturesForId(ANY_SYSTEM_NAME, ANY_ID);
//		assertEquals(0, results.size());
//		verifyZeroInteractions(mockToggleVoterManager);
//	}
//
//
//	@Test
//	public void testGetFeaturesForIdWithValidSystemWithOneFeatureReleased() {
//
//		Set<Feature> featureSetWithReleasedFeature = createFeatureSetWithReleasedFeature();
//		when(mockToggleSystem.getFeatures()).thenReturn(featureSetWithReleasedFeature);
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
//		Set<String> results = classToTest.getFeaturesForId(ANY_SYSTEM_NAME, ANY_ID);
//		assertEquals(1, results.size());
//		assertTrue(results.contains(ANY_FEATURE_NAME));
//		verifyZeroInteractions(mockToggleVoterManager);
//	}
//
//
//	@Test
//	public void testGetFeaturesForIdWithValidSystemAndOneLimitedFeatureWhichQualifies() {
//
//		Set<Feature> featureSetWithLimitedFeature = createFeatureSetWithLimitedFeature();
//		when(mockToggleSystem.getFeatures()).thenReturn(featureSetWithLimitedFeature);
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
//		List<ToggleVoter<?>> voters = buildVotersList();
//		when(mockToggleVoterManager.getVoters(isA(FeatureStrategy.class))).thenReturn(voters);
//		Set<String> results = classToTest.getFeaturesForId(ANY_SYSTEM_NAME, ANY_ID);
//		assertEquals(1, results.size());
//		assertTrue(results.contains(ANY_FEATURE_NAME));
//		verify(mockToggleVoterManager).getVoters(isA(FeatureStrategy.class));
//	}
//
//
//	@Test
//	public void testGetFeaturesForIdWithValidSystemAndOneLimitedFeatureWhichDoesNotQualify() {
//
//		Set<Feature> featureSetWithLimitedFeature = createFeatureSetWithLimitedFeature();
//		when(mockToggleSystem.getFeatures()).thenReturn(featureSetWithLimitedFeature);
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
//		List<ToggleVoter<?>> voters = buildVotersList();
//		when(mockToggleVoterManager.getVoters(isA(FeatureStrategy.class))).thenReturn(voters);
//		Set<String> results = classToTest.getFeaturesForId(ANY_SYSTEM_NAME, ID_THAT_DOES_NOT_QUALIFY);
//		assertEquals(0, results.size());
//		verify(mockToggleVoterManager).getVoters(isA(FeatureStrategy.class));
//	}
//
//
//	@Test(expected = EntityNotFoundException.class)
//	public void testGetFeaturesForIdWithInvalidSystem() {
//
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(null);
//		classToTest.getFeaturesForId(ANY_SYSTEM_NAME, ANY_ID);
//	}
//
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testGetFeaturesForIdWithNullSystemName() {
//		String param = null;
//		classToTest.getFeaturesForId(param, ANY_ID);
//	}
//
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testGetFeaturesForIdWithNullSystem() {
//		ToggleSystem param = null;
//		classToTest.getFeaturesForId(param, ANY_ID);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testGetFeaturesForIdWithNullID() {
//
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
//		classToTest.getFeaturesForId(ANY_SYSTEM_NAME, null);
//	}
//
//
//	@Test
//	public void testGetActiveSystems() {
//
//		List<ToggleSystem> toggleSystemList = new ArrayList<>();
//		toggleSystemList.add(mockToggleSystem);
//		when(mockToggleRepository.getAllSystems()).thenReturn(toggleSystemList);
//		List<ToggleSystem> systems = classToTest.getActiveSystems();
//		assertEquals(1, systems.size());
//		assertEquals(mockToggleSystem, systems.get(0));
//	}
//
//
//	@Test
//	public void testGetSystem() {
//
//		ToggleSystem toggleSystem = new ToggleSystem();
//		toggleSystem.setSystemName(ANY_SYSTEM_NAME);
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(toggleSystem);
//		ToggleSystem system = classToTest.getSystem(ANY_SYSTEM_NAME);
//		assertEquals(ANY_SYSTEM_NAME, system.getSystemName());
//	}
//
//
//	@Test
//	public void testCreateSystem() {
//
//		ToggleSystemFormBean form = buildSystemForm();
//		ToggleSystem result = classToTest.createSystem(form, ANY_USER_ID);
//		assertEquals(ANY_SYSTEM_NAME.toUpperCase(), result.getSystemName());
//		assertEquals(ANY_SYSTEM_DESCRIPTION.toUpperCase(), result.getDescription());
//		assertEquals(ANY_USER_ID, result.getCreatedBy());
//		assertNotNull(result.getCreationTime());
//		assertNull(result.getUpdatedBy());
//		assertNull(result.getUpdatedTime());
//		verify(mockToggleRepository).save(any(ToggleSystem.class));
//	}
//
//
//	private ToggleSystemFormBean buildSystemForm() {
//		return buildSystemForm(ANY_SYSTEM_NAME, ANY_SYSTEM_DESCRIPTION);
//	}
//
//
//	private ToggleSystemFormBean buildSystemForm(String name, String desc) {
//		ToggleSystemFormBean form = new ToggleSystemFormBean();
//		form.setSystemName(name);
//		form.setDescription(desc);
//		return form;
//	}
//
//
//	@Test
//	public void testDeleteSystem() {
//
//		ToggleSystem toggleSystem = new ToggleSystem();
//		classToTest.deleteSystem(toggleSystem);
//		verify(mockToggleRepository).deleteSystem(toggleSystem);
//	}
//
//
//	@Test
//	public void testDeleteFeature() {
//
//		ToggleSystem ts = new ToggleSystem();
//		Feature feature = createFeature();
//		ts.getFeatures().add(feature);
//		feature.setSystem(ts);
//		classToTest.deleteFeature(feature);
//		assertEquals(0, ts.getFeatures().size());
//		verify(mockToggleRepository).save(ts);
//	}
//
//
//	@Test
//	public void testCreateFeature() {
//
//		ToggleSystem ts = new ToggleSystem();
//		FeatureFormBean featureBean = createFeatureFormBean(ANY_FEATURE_NAME, ANY_FEATURE_DESC, FEATURE_STATUS_NOT_RELEASED_NAME);
//		when(mockToggleRepository.getFeatureStatusByName(FEATURE_STATUS_NOT_RELEASED_NAME)).thenReturn(createNotReleasedStatus());
//		classToTest.createFeature(ts, featureBean, ANY_USER_ID);
//		assertEquals(1, ts.getFeatures().size());
//		Feature feature = ts.getFeatureByName(ANY_FEATURE_NAME);
//		assertEquals(ANY_FEATURE_NAME.toUpperCase(), feature.getName());
//		assertEquals(ANY_FEATURE_DESC.toUpperCase(), feature.getDescription());
//		assertEquals(ts, feature.getSystem());
//		assertEquals(FEATURE_STATUS_NOT_RELEASED_NAME, feature.getFeatureStatus().getName());
//		assertEquals(ANY_USER_ID, feature.getCreatedBy());
//		assertNotNull(feature.getCreationTime());
//		assertNull(feature.getUpdatedBy());
//		assertNull(feature.getUpdatedTime());
//		verify(mockToggleRepository).save(ts);
//
//	}
//
//
//	
//	@Test
//	public void testCreateFeatureGroup() {
//
//		FeatureGroupFormBean group = new FeatureGroupFormBean();
//		group.setGroupName(ANY_GROUP_NAME);
//		FeatureGroup result = classToTest.createFeatureGroup(group, ANY_USER_ID);
//		assertEquals(ANY_GROUP_NAME.toUpperCase(), result.getDescription());
//		verify(mockToggleRepository).save(result);
//		assertEquals(ANY_USER_ID, result.getCreatedBy());
//		assertNotNull(result.getCreationTime());
//		assertNull(result.getUpdatedBy());
//		assertNull(result.getUpdatedTime());
//	}
//
//
//	private FeatureFormBean createFeatureFormBean(String featureName, String desc, String status) {
//		FeatureStatus featureStatus = new FeatureStatus();
//		featureStatus.setName(status);
//		FeatureFormBean featureBean = new FeatureFormBean();
//		featureBean.setFeatureName(featureName);
//		featureBean.setDescription(desc);
//		featureBean.setStatus(status);
//		return featureBean;
//	}
//
//
//	@Test
//	public void testUpdateFeature() {
//
//		Feature feature = createFeature();
//		FeatureFormBean featureBean = createFeatureFormBean(ANY_FEATURE_NAME, NEW_FEATURE_DESC, FEATURE_STATUS_RELEASED_NAME);
//		when(mockToggleSystem.getFeatureByName(ANY_FEATURE_NAME)).thenReturn(feature);
//		when(mockToggleRepository.getFeatureStatusByName(FEATURE_STATUS_RELEASED_NAME)).thenReturn(createReleasedStatus());
//		classToTest.updateFeature(mockToggleSystem, featureBean, ANY_USER_ID);
//		verify(mockToggleRepository).save(mockToggleSystem);
//		assertEquals(NEW_FEATURE_DESC.toUpperCase(), feature.getDescription());
//		assertEquals(FEATURE_STATUS_RELEASED_NAME, feature.getFeatureStatus().getName());
//		assertNull(feature.getCreatedBy());
//		assertNull(feature.getCreationTime());
//		assertEquals(ANY_USER_ID, feature.getUpdatedBy());
//		assertNotNull(feature.getUpdatedTime());
//		assertEquals(1, feature.getStrategies().size());
//	}
//
//
//	@Test
//	public void testUpdateSystem() {
//
//		ToggleSystemFormBean form = buildSystemForm(ANY_SYSTEM_NAME, NEW_SYSTEM_DESC);
//		when(mockToggleRepository.getSystemByName(ANY_SYSTEM_NAME)).thenReturn(mockToggleSystem);
//		classToTest.updateSystem(form, ANY_USER_ID);
//		verify(mockToggleSystem).setUpdatedBy(ANY_USER_ID);
//		verify(mockToggleSystem).setUpdatedTime(any(Timestamp.class));
//		verify(mockToggleSystem).setDescription(NEW_SYSTEM_DESC.toUpperCase());
//		verify(mockToggleRepository).save(mockToggleSystem);
//	}
//
//
//	@Test
//	public void testUpdateStrategyForGroupStrategy() {
//
//		GroupStrategy mockStrategy = Mockito.mock(GroupStrategy.class);
//		Feature mockFeature = Mockito.mock(Feature.class);
//		GroupStrategyFormBean groupForm = new GroupStrategyFormBean();
//		groupForm.setStrategyName(ANY_GROUP_STRAT_NAME);
//		when(mockStrategy.getFeature()).thenReturn(mockFeature);
//		when(mockFeature.getSystem()).thenReturn(mockToggleSystem);
//		classToTest.updateGroupStrategy(groupForm, mockStrategy, ANY_USER_ID);
//		verify(mockStrategy).setName(ANY_GROUP_STRAT_NAME.toUpperCase());
//		verify(mockStrategy).getFeature();
//		verify(mockFeature).getSystem();
//		verify(mockStrategy).setUpdatedBy(ANY_USER_ID);
//		verify(mockStrategy).setUpdatedTime(any(Timestamp.class));
//		verify(mockToggleRepository).save(mockToggleSystem);
//	}
//
//
//	@Test
//	public void testDeleteStrategy() {
//
//		FeatureStrategy mockStrategy = Mockito.mock(GroupStrategy.class);
//		Feature mockFeature = Mockito.mock(Feature.class);
//		when(mockStrategy.getFeature()).thenReturn(mockFeature);
//		when(mockFeature.getSystem()).thenReturn(mockToggleSystem);
//		classToTest.deleteStrategy(mockStrategy);
//		verify(mockToggleRepository).save(mockToggleSystem);
//	}
//
//
//	@Test
//	public void testCreateGroupStrategy() {
//		Feature feature = createFeature();
//		// start with an empty set of strategies.
//		feature.getStrategies().clear();
//		feature.setSystem(mockToggleSystem);
//
//		GroupStrategyFormBean form = new GroupStrategyFormBean();
//		form.setStrategyName(ANY_GROUP_STRATEGY_NAME);
//		form.setStrategyType(GROUP_STRATEGY_NAME);
//
//		classToTest.createGroupStrategy(feature, form, ANY_USER_ID);
//		verify(mockToggleRepository).save(mockToggleSystem);
//		assertThat(feature.getStrategies().size(), equalTo(1));
//
//		FeatureStrategy[] strats = new FeatureStrategy[feature.getStrategies().size()];
//		feature.getStrategies().toArray(strats);
//		assertEquals(1, strats.length);
//		GroupStrategy groupStrat = (GroupStrategy) strats[0];
//
//		assertThat(feature.getStrategyByName(ANY_GROUP_STRATEGY_NAME), notNullValue());
//		assertEquals(ANY_GROUP_STRATEGY_NAME.toUpperCase(), groupStrat.getName());
//		assertEquals(feature, groupStrat.getFeature());
//		assertEquals(ANY_USER_ID, groupStrat.getCreatedBy());
//		assertNotNull(groupStrat.getCreationTime());
//	}
//
//
//	@Test
//	public void testGetGroup() {
//
//		FeatureGroup testGroup = new FeatureGroup();
//		when(mockToggleRepository.getGroupByName(ANY_GROUP_NAME)).thenReturn(testGroup);
//		FeatureGroup group = classToTest.getGroup(ANY_GROUP_NAME);
//		verify(mockToggleRepository).getGroupByName(ANY_GROUP_NAME);
//		assertEquals(testGroup, group);
//	}
//
//
//	@Test
//	public void testUpdateGroup() {
//
//		FeatureGroupFormBean groupForm = new FeatureGroupFormBean();
//		groupForm.setGroupName(NEW_GROUP_NAME);
//		FeatureGroup featureGroup = new FeatureGroup();
//		featureGroup.setDescription(ORIGINAL_GROUP_NAME);
//		classToTest.updateGroup(groupForm, featureGroup, ANY_USER_ID);
//		verify(mockToggleRepository).save(featureGroup);
//		assertEquals(NEW_GROUP_NAME.toUpperCase(), featureGroup.getDescription());
//		assertEquals(ANY_USER_ID, featureGroup.getUpdatedBy());
//		assertNotNull(featureGroup.getUpdatedTime());
//	}
//
//
//	@Test
//	public void testDeleteGroup() {
//
//		FeatureGroup featureGroup = new FeatureGroup();
//		classToTest.deleteGroup(featureGroup);
//		verify(mockToggleRepository).deleteGroup(featureGroup);
//	}
//
//
//	@Test
//	public void testGetActiveGroups() {
//
//		List<FeatureGroup> expectedResults = new ArrayList<>();
//		List<FeatureGroup> results = classToTest.getActiveGroups();
//		assertEquals(expectedResults, results);
//		verify(mockToggleRepository).getAllGroups();
//	}
//
//
//	@Test
//	public void testDeleteMemberWithTwoMembersInList() {
//
//		FeatureGroup group = new FeatureGroup();
//		Member member = new Member();
//		member.setFeatureGroup(group);
//		Member member1 = new Member();
//		member1.setMemberId(MEMBER_ID1);
//		member1.setId(MEMBER_ID_1_LONG);
//		member1.setFeatureGroup(group);
//		Member member2 = new Member();
//		member2.setMemberId(MEMBER_ID2);
//		member2.setId(MEMBER_ID_2_LONG);
//		member2.setFeatureGroup(group);
//		group.getMembers().add(member1);
//		group.getMembers().add(member2);
//		classToTest.deleteMember(member1);
//		verify(mockToggleRepository).save(group);
//		assertEquals(1, group.getMembers().size());
//		assertTrue(group.getMembers().contains(member2));
//	}
//
//	@Test
//	public void testAddMember() {
//	
//		FeatureGroup mockFeatureGroup = Mockito.mock(FeatureGroup.class);
//		@SuppressWarnings("unchecked")
//		Set<Member> mockMembers = Mockito.mock(Set.class);
//		when(mockFeatureGroup.getMembers()).thenReturn(mockMembers);
//		MemberFormBean newMember = new MemberFormBean();
//		newMember.setMemberId(ANY_MEMBER_NAME);
//		ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
//		classToTest.addMember(mockFeatureGroup, newMember, ANY_USER_ID);
//		verify(mockToggleRepository).save(mockFeatureGroup);
//		verify(mockMembers).add(memberCaptor.capture());
//		assertEquals(ANY_MEMBER_NAME, memberCaptor.getValue().getMemberId(), ANY_MEMBER_NAME.toUpperCase());
//		assertEquals(mockFeatureGroup, memberCaptor.getValue().getFeatureGroup());
//		assertEquals(ANY_USER_ID, memberCaptor.getValue().getCreatedBy());
//		assertNotNull(memberCaptor.getValue().getCreationTime());
//		assertNull(memberCaptor.getValue().getUpdatedBy());
//		assertNull(memberCaptor.getValue().getUpdatedTime());
//	}
//	
//	@Test
//	public void testAddFeatureGroupToGroupStrategyWhereNoGroupsExist() {
//
//		GroupStrategy mockStrategy = Mockito.mock(GroupStrategy.class);
//		Feature mockFeature = Mockito.mock(Feature.class);
//		ToggleSystem mockSystem = Mockito.mock(ToggleSystem.class);
//		when(mockStrategy.getFeature()).thenReturn(mockFeature);
//		when(mockFeature.getSystem()).thenReturn(mockSystem);
//		@SuppressWarnings("unchecked")
//		Set<FeatureGroup> featureGroups = Mockito.mock(Set.class);
//		when(mockStrategy.getGroups()).thenReturn(featureGroups);
//		FeatureGroup featureGroup = new FeatureGroup();
//		classToTest.addFeatureGroupToGroupStrategy(mockStrategy, featureGroup);
//		verify(featureGroups).add(featureGroup);
//		verify(mockToggleRepository).save(mockSystem);
//	}
//
//	
//	@Test
//	public void testRemoveFeatureGroupFromGroupStrategy() {
//		
//		Feature mockFeature = Mockito.mock(Feature.class);
//		FeatureGroup featureGroup = new FeatureGroup();
//		GroupStrategy groupStrategy = new GroupStrategy();
//		groupStrategy.getGroups().add(featureGroup);
//		groupStrategy.setFeature(mockFeature);
//		when(mockFeature.getSystem()).thenReturn(mockToggleSystem);
//		classToTest.removeFeatureGroupFromGroupStrategy(featureGroup, groupStrategy);
//		assertEquals(0, groupStrategy.getGroups().size());
//		verify(mockToggleRepository).save(mockToggleSystem);
//	}
//	
//	@Test
//	public void testCreateStrategyWithValidForm() {
//		
//		Feature feature = new Feature();
//		feature.setSystem(mockToggleSystem);
//		StrategyFormBean form = new StrategyFormBean();
//		form.setStrategyName(ANY_GROUP_STRAT_NAME);
//		form.setStrategyType("GROUP");
//		classToTest.createStrategy(feature, form, ANY_USER_ID);
//		assertEquals(1, feature.getStrategies().size());
//		verify(mockToggleRepository).save(mockToggleSystem);
//		FeatureStrategy strat = new ArrayList<FeatureStrategy>(feature.getStrategies()).get(0);
//		assertThat(strat, instanceOf(GroupStrategy.class));
//		assertEquals(ANY_GROUP_STRAT_NAME.toUpperCase(), strat.getName());
//		assertEquals(feature, strat.getFeature());
//		assertEquals(ANY_USER_ID, strat.getCreatedBy());
//		assertNotNull(strat.getCreationTime());
//		assertNull(strat.getUpdatedBy());
//		assertNull(strat.getUpdatedTime());
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testCreateStrategyWithValidFormWithWrongStrategyType() {
//		
//		Feature feature = new Feature();
//		feature.setSystem(mockToggleSystem);
//		StrategyFormBean form = new StrategyFormBean();
//		form.setStrategyName(ANY_GROUP_STRAT_NAME);
//		form.setStrategyType("BAD_STRAT_TYPE");
//		classToTest.createStrategy(feature, form, ANY_USER_ID);
//	}
//	
//	@Test
//	public void testGetStratagiesByGroupWhenStrategiesExist() {
//	
//		FeatureGroup featureGroup = new FeatureGroup();
//		List<GroupStrategy> expected = new ArrayList<>();
//		GroupStrategy strategy = new GroupStrategy();
//		expected.add(strategy);
//		when(mockToggleRepository.getStratagiesByGroup(featureGroup)).thenReturn(expected);
//		List<GroupStrategy> results = classToTest.getStratagiesByGroup(featureGroup);
//		assertEquals(1, results.size());
//	}
//	
//	
//	private List<ToggleVoter<?>> buildVotersList() {
//		List<ToggleVoter<?>> voters = new ArrayList<>();
//		voters.add(new GroupVoter());
//		return voters;
//	}
//
//
//	private Feature createFeature() {
//		FeatureStatus status = new FeatureStatus();
//		status.setName(FEATURE_STATUS_LIMITED_NAME);
//		status.setId(1);
//		Feature feature = new Feature();
//		feature.setFeatureStatus(status);
//		feature.setName(ANY_FEATURE_NAME);
//		FeatureGroup featureGroup = new FeatureGroup();
//		Set<Member> memberSet = new HashSet<>();
//		Member member = new Member();
//		member.setMemberId(ANY_ID);
//		memberSet.add(member);
//		featureGroup.setMembers(memberSet);
//		Set<FeatureGroup> featureGroups = new HashSet<>();
//		featureGroups.add(featureGroup);
//		GroupStrategy gs = new GroupStrategy();
//		gs.setGroups(featureGroups);
//		feature.getStrategies().add(gs);
//		return feature;
//	}
//
//
//	private Set<Feature> createFeatureSetWithLimitedFeature() {
//		FeatureStatus status = new FeatureStatus();
//		status.setName(FEATURE_STATUS_LIMITED_NAME);
//		status.setId(1);
//		Set<Feature> results = new HashSet<>();
//		Feature feature = new Feature();
//		feature.setFeatureStatus(status);
//		feature.setName(ANY_FEATURE_NAME);
//		FeatureGroup featureGroup = new FeatureGroup();
//		Set<Member> memberSet = new HashSet<>();
//		Member member = new Member();
//		member.setMemberId(ANY_ID);
//		memberSet.add(member);
//		featureGroup.setMembers(memberSet);
//		Set<FeatureGroup> featureGroups = new HashSet<>();
//		featureGroups.add(featureGroup);
//		GroupStrategy gs = new GroupStrategy();
//		gs.setGroups(featureGroups);
//		feature.getStrategies().add(gs);
//		results.add(feature);
//		return results;
//	}
//
//
//	private Set<Feature> createFeatureSetWithReleasedFeature() {
//		FeatureStatus status = new FeatureStatus();
//		status.setName(FEATURE_STATUS_RELEASED_NAME);
//		status.setId(2);
//		Set<Feature> results = new HashSet<>();
//		Feature feature = new Feature();
//		feature.setFeatureStatus(status);
//		feature.setName(ANY_FEATURE_NAME);
//		results.add(feature);
//		return results;
//	}
//
//
//	private Set<Feature> createFeatureSetWithNotReleasedFeature() {
//		FeatureStatus status = new FeatureStatus();
//		status.setName(FEATURE_STATUS_NOT_RELEASED_NAME);
//		status.setId(0);
//		Set<Feature> results = new HashSet<>();
//		Feature feature = new Feature();
//		feature.setFeatureStatus(status);
//		results.add(feature);
//		return results;
//	}
//	
//	private FeatureStatus createNotReleasedStatus() {
//		FeatureStatus status = new FeatureStatus();
//		status.setId(FeatureStatus.NOT_RELEASED_ID);
//		status.setName(FEATURE_STATUS_NOT_RELEASED_NAME);
//		return status;
//	}
//
//	private FeatureStatus createReleasedStatus() {
//		FeatureStatus status = new FeatureStatus();
//		status.setId(FeatureStatus.RELEASED_ID);
//		status.setName(FEATURE_STATUS_RELEASED_NAME);
//		return status;
//	}
//
//
//
//}
