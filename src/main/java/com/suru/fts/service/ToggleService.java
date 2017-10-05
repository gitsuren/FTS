package com.suru.fts.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suru.fts.dto.FeatureFormBean;
import com.suru.fts.dto.FeatureGroupFormBean;
import com.suru.fts.dto.GroupStrategyFormBean;
import com.suru.fts.dto.MemberFormBean;
import com.suru.fts.dto.StrategyFormBean;
import com.suru.fts.dto.ToggleSystemFormBean;
import com.suru.fts.mongo.domain.Feature;
import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.FeatureStatus;
import com.suru.fts.mongo.domain.Member;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;
import com.suru.fts.mongo.repository.FeatureGroupRepository;
import com.suru.fts.mongo.repository.ToggleRepository;
import com.suru.fts.voter.ToggleVoter;
import com.suru.fts.voter.ToggleVoterManager;

@Service
public class ToggleService {

	@Autowired
	private ToggleVoterManager voterManager;

	@Autowired
	private ToggleRepository toggleRepository;
	
	@Autowired
	private FeatureGroupRepository featureGroupRepository;

//	private ToggleSystem system;

	public Set<String> getFeaturesForId(final String systemName, final String id) {

		if (systemName == null) {
			throw new IllegalArgumentException("System name must not be null");
		}
		ToggleSystem toggleSystem = getSystem(systemName);
//		ToggleSystem toggleSystem = toggleRepository.getSystemByName(systemName);
		if (toggleSystem == null) {
			throw new RuntimeException("System name: " + systemName + " is not found");
		}
		return getFeaturesForId(toggleSystem, id);
	}

	public Set<String> getFeaturesForId(final ToggleSystem toggleSystem, final String id) {
		
		if (toggleSystem == null) {
			throw new IllegalArgumentException("Toggle system must not be null");
		}
		if (id == null) {
			throw new IllegalArgumentException("Id must not be null");
		}
		Set<String> results = new HashSet<>();
		for (Feature feature : toggleSystem.getFeatures()) {
			if (FeatureStatus.RELEASED_ID == feature.getFeatureStatus().getId()) {
				results.add(feature.getName());
			} else if (FeatureStatus.LIMITED_ID == feature.getFeatureStatus().getId()) {
				checkAvailability(results, feature, id);
			}
		}
		return results;
	}

	public ToggleSystem getSystem(String systemName) {

		return toggleRepository.findOne(systemName);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void checkAvailability(final Set<String> results, final Feature feature, final String id) {

		for (FeatureStrategy featureStrategy : feature.getStrategies()) {
			for (ToggleVoter voter : voterManager.getVoters(featureStrategy)) {
				boolean vote = voter.vote(id, featureStrategy);
				if (vote) {
					results.add(feature.getName());
					break;
				}
			}
		}
	}


	public List<ToggleSystem> getActiveSystems() {
		return (List<ToggleSystem>) toggleRepository.findAll();
	}


	@Transactional
	public ToggleSystem createSystem(final ToggleSystemFormBean systemForm, final String id) {

		ToggleSystem toggleSystem = new ToggleSystem();
		toggleSystem.setSystemName(systemForm.getSystemName().toUpperCase());
		toggleSystem.setDescription(systemForm.getDescription().toUpperCase());
		//updateCreationFields(id, toggleSystem);
		toggleRepository.save(toggleSystem);
		return toggleSystem;
	}


	@Transactional
	public void saveSystem(final ToggleSystem system) {
		toggleRepository.save(system);
	}

	@Transactional
	public void deleteSystem(final ToggleSystem system) {
		toggleRepository.delete(system);
	}


	@Transactional
	public void deleteFeature(final Feature feature) {
//		ToggleSystem toggleSystem = feature.getSystem();
		ToggleSystem toggleSystem = getSystem(feature.getSystemName());
		toggleSystem.getFeatures().remove(feature);
		toggleRepository.save(toggleSystem);
	}

	
	@Transactional
	public void createFeature(final ToggleSystem system, final FeatureFormBean featureBean, final String id) {

		Feature feature = new Feature();
		feature.setName(featureBean.getFeatureName().toUpperCase());
		feature.setDescription(featureBean.getDescription().toUpperCase());
		feature.setSystemName(system.getSystemName());
		//feature.setFeatureStatus(toggleRepository.getFeatureStatusByName(featureBean.getStatus()));
		FeatureStatus featureStatus = new FeatureStatus();
		featureStatus.setId(getStatusId(featureBean.getStatus()));
		featureStatus.setName(featureBean.getStatus());
		feature.setFeatureStatus(featureStatus);
		//updateCreationFields(id, feature);
		system.getFeatures().add(feature);
		toggleRepository.save(system);
	}


	@Transactional
	public void updateFeature(final ToggleSystem system, final FeatureFormBean featureBean, final String id) {

		Feature feature = system.getFeatureByName(featureBean.getFeatureName());
		feature.setDescription(featureBean.getDescription().toUpperCase());
		//feature.setFeatureStatus(toggleRepository.getFeatureStatusByName(featureBean.getStatus()));
		feature.getFeatureStatus().setId(getStatusId(featureBean.getStatus()));
		feature.getFeatureStatus().setName(featureBean.getStatus());
		//updateUpdateFields(id, feature);
		toggleRepository.save(system);
	}


	@Transactional
	public void updateSystem(final ToggleSystemFormBean systemForm, final String id) {

		ToggleSystem system = getSystem(systemForm.getSystemName());
		system.setDescription(systemForm.getDescription().toUpperCase());
		//updateUpdateFields(id, system);
		toggleRepository.save(system);
	}


	@Transactional
	public void updateGroupStrategy(final ToggleSystem system, final GroupStrategyFormBean form, final GroupStrategy strategy, final String id) {

		strategy.setName(form.getStrategyName().toUpperCase());
		//updateUpdateFields(id, strategy);
		toggleRepository.save(system);
	}


	@Transactional
	public void deleteStrategy(final ToggleSystem system, final String featureName, final FeatureStrategy strategy) {
		
		Feature feature = system.getFeatureByName(featureName);
		feature.getStrategies().remove(strategy);
		toggleRepository.save(system);
	}


	@Transactional
	public void createStrategy(final ToggleSystem system , final Feature feature, final StrategyFormBean form, final String id) {

		FeatureStrategy strategy = getFeatureStrategyImpl(form.getStrategyType());
		//strategy.setFeature(feature);
		addStrategyToFeature(system, feature, form, strategy);
		//feature.getStrategies().add(strategy);
		//updateCreationFields(id, strategy);
		toggleRepository.save(system);

	}

	


	@Transactional
	public void createGroupStrategy(final ToggleSystem system ,final Feature feature, final GroupStrategyFormBean form, final String id) {

		FeatureStrategy strategy = new GroupStrategy();
		addStrategyToFeature(system, feature, form, strategy);
	//	updateCreationFields(id, strategy);
		//feature.getStrategies().add(strategy);
		toggleRepository.save(system);
	}


	public FeatureGroup getGroup(final String groupName) {

		return featureGroupRepository.findOne(groupName);
	}


	public void updateGroup(final FeatureGroupFormBean groupForm, final FeatureGroup featureGroup, final String id) {

		featureGroup.setDescription(groupForm.getGroupName().toUpperCase());
		//updateUpdateFields(id, featureGroup);
		featureGroupRepository.save(featureGroup);
	}


	public void deleteGroup(FeatureGroup featureGroup) {

		featureGroupRepository.delete(featureGroup);
	}


	public List<FeatureGroup> getActiveGroups() {

		return (List<FeatureGroup>) featureGroupRepository.findAll();
	}


	@Transactional
	public FeatureGroup createFeatureGroup(final FeatureGroupFormBean group, final String id) {

		FeatureGroup featureGroup = new FeatureGroup();
		featureGroup.setDescription(group.getGroupName().toUpperCase());
		//updateCreationFields(id, featureGroup);
		featureGroupRepository.save(featureGroup);
		return featureGroup;
	}


	@Transactional
	public void deleteMember(final Member member) {
	
		FeatureGroup group = getGroup(member.getFeatureGroupName());
		Set<Member> members = group.getMembers().stream()
				.filter(m -> !m.getMemberId().equalsIgnoreCase(member.getMemberId()))
				.collect(Collectors.toSet());
		group.setMembers(members);
		featureGroupRepository.save(group);
		
		//update the toggle system
	}


	@Transactional
	public void addMember(final FeatureGroup featureGroup, final MemberFormBean newMember, final String id) {
		if (StringUtils.isBlank(newMember.getMemberId())) {
			return;
		}
		Member member = new Member();
		member.setMemberId(newMember.getMemberId().toUpperCase());
		member.setFeatureGroupName(featureGroup.getDescription());
		//member.setFeatureGroup(featureGroup); 
		//updateCreationFields(id, member);
		featureGroup.getMembers().add(member);
		featureGroupRepository.save(featureGroup);
		
		//update the toggel system
	}


	@Transactional
	public void addFeatureGroupToGroupStrategy(final String systemName, final String featureName, final String strategyName, final String groupName) {

		ToggleSystem toggleSystem = getSystem(systemName);
		GroupStrategy strategy = getStrategy(toggleSystem, featureName, strategyName);
		FeatureGroup featureGroup = getGroup(groupName);
		strategy.getGroups().add(featureGroup);
		toggleRepository.save(toggleSystem);
	}


	@Transactional
	public void removeFeatureGroupFromGroupStrategy(final String systemName, final String featureName, final String strategyName, final String groupName) {
		ToggleSystem toggleSystem = getSystem(systemName);
		GroupStrategy strategy = getStrategy(toggleSystem, featureName, strategyName);
		final FeatureGroup featureGroup = getGroup(groupName);

		Set<FeatureGroup> featureGroups = strategy.getGroups().stream()
				.filter(fg -> !fg.getDescription().equalsIgnoreCase(featureGroup.getDescription()))
				.collect(Collectors.toSet());
		strategy.setGroups(featureGroups);
		toggleRepository.save(toggleSystem);
	}


	private FeatureStrategy getFeatureStrategyImpl(final String strategyType) {

		if ("GROUP".equalsIgnoreCase(strategyType)) {
			return new GroupStrategy();
		}
		throw new IllegalArgumentException("Unknown strategy: " + strategyType);
	}
//	
//	private void updateCreationFields(final String id, final IdDomainObject domainObject) {
//		domainObject.setCreatedBy(id);
//		domainObject.setCreationTime(new Timestamp(System.currentTimeMillis()));
//	}
//
//	private void updateUpdateFields(final String id, final IdDomainObject domainObject) {
//		domainObject.setUpdatedBy(id);
//		domainObject.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
//	}

	//FIXME:
	public List<GroupStrategy> getStratagiesByGroup(final FeatureGroup featureGroup) {
		return null;
	}
	
	@Transactional
	public ToggleSystem deleteFeature(String systemName, String featureName) {
		ToggleSystem system = getSystem(systemName);
		for (Feature feature : system.getFeatures()) {
			if (feature.getName().equalsIgnoreCase(featureName)) {
				system.getFeatures().remove(feature);
				break;
			}
		}
		saveSystem(system);
		return system;
	}

	public void updateFeature(String systemName, Feature feature) {
		ToggleSystem system = getSystem(systemName);
		for (Feature f : system.getFeatures()) {
			if (f.getName().equalsIgnoreCase(feature.getName())) {
				system.getFeatures().remove(f);
				break;
			}
		}
		system.getFeatures().add(feature);
		saveSystem(system);
	}
	
	private void addStrategyToFeature(final ToggleSystem system, final Feature feature,
			final StrategyFormBean form, FeatureStrategy strategy) {
		strategy.setFeatureName(feature.getName());
		strategy.setSystemName(system.getSystemName());
		strategy.setName(form.getStrategyName().toUpperCase());
		
		List<Feature> features = system.getFeatures();
		features.stream()
				.filter(f -> f.getName().equalsIgnoreCase(feature.getName()) && f.getSystemName().equalsIgnoreCase(
				system.getSystemName()))
				.collect(Collectors.toList())
				.get(0).getStrategies().add(strategy);
	}
	
	private GroupStrategy getStrategy(final ToggleSystem toggleSystem, final String featureName, final String strategyName) {
		
		FeatureStrategy strategy = toggleSystem.getFeatureByName(featureName).getStrategyByName(strategyName);
		return (GroupStrategy) strategy;
	}
	
	private Long getStatusId(String status) {
		Long statusId= -1L;
		switch(status){
		case "NOT_RELEASED": statusId =  FeatureStatus.NOT_RELEASED_ID; break;
		case "LIMITED": statusId =  FeatureStatus.LIMITED_ID; break;
		case "RELEASED": statusId =  FeatureStatus.RELEASED_ID; break;
		default : statusId= -1L;
		
		}
		
		return statusId;
	}

}
