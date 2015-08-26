//package com.suru.fts.domain.program;
//
//import java.sql.Timestamp;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.suru.fts.mongo.domain.Feature;
//import com.suru.fts.mongo.domain.FeatureGroup;
//import com.suru.fts.mongo.domain.FeatureStatus;
//import com.suru.fts.mongo.domain.Member;
//import com.suru.fts.mongo.domain.ToggleSystem;
//import com.suru.fts.mongo.domain.strategy.GroupStrategy;
//import com.suru.fts.service.program.SpringConfigTestProgram;
//
//public class DomainObjectsTestProgram extends SpringConfigTestProgram {
//
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	@Override
//	protected void execute() throws Exception {
//		
//		//createStatus();
//		//createFeatureStatusRows();
//		//createMember();
//		//createGroup();
//		//createEmptySystem();
//		//createSystemWithAFeature();
//		//createSystemWithAFeatureAndAGroupStrategy();
//		
//	}
//
//	@SuppressWarnings("unused")
//	private void createStatus() {
//		
//		sessionFactory.getCurrentSession().beginTransaction();
//		FeatureStatus status1 = new FeatureStatus();
//		status1.setId(2);
//		status1.setName("RELEASED");
//		status1.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		status1.setCreatedBy("DU80449");
//		sessionFactory.getCurrentSession().saveOrUpdate(status1);
//		sessionFactory.getCurrentSession().getTransaction().commit();
//	}
//	
//	@SuppressWarnings("unused")
//	private void createSystemWithAFeatureAndAGroupStrategy() {
//		// TODO Auto-generated method stub
//	
//		sessionFactory.getCurrentSession().beginTransaction();
//		ToggleSystem system = new ToggleSystem();
//		system.setSystemName("DAN_TEST");
//		system.setDescription("this is a test");
//		system.setCreatedBy("du80449");
//		system.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		system.setUpdatedBy("chuck");
//		system.setUpdatedTime(system.getCreationTime());
//		
//		Feature feature = new Feature();
//		feature.setName("NEW_FEATURE");
//		feature.setDescription("feature desc");
//		feature.setSystem(system);
//		feature.setCreatedBy("du80449");
//		feature.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		feature.setUpdatedBy(null);
//		feature.setUpdatedTime(null);
//		
//		
//		FeatureStatus status = (FeatureStatus) sessionFactory.getCurrentSession().get(FeatureStatus.class, new Long(1));
//		feature.setFeatureStatus(status);
//		system.getFeatures().add(feature);
//		
//		GroupStrategy groupStrat = new GroupStrategy();
//		groupStrat.setFeature(feature);
//		groupStrat.setName("group strat");
//		groupStrat.setCreatedBy("du80449");
//		groupStrat.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		groupStrat.setUpdatedBy(null);
//		groupStrat.setUpdatedTime(null);
//		feature.getStrategies().add(groupStrat);
//
//		FeatureGroup group = new FeatureGroup();
//		group.setDescription("test group");
//		group.setCreatedBy("du80449");
//		group.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		group.setUpdatedBy("chuck");
//		group.setUpdatedTime(group.getCreationTime());
//
//		Member member = new Member();
//		member.setMemberId("DU80449");
//		member.setCreatedBy("DU80449");
//		member.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		member.setUpdatedBy("FRED");
//		member.setUpdatedTime(member.getCreationTime());
//		member.setFeatureGroup(group);
//		group.getMembers().add(member);
//		groupStrat.getGroups().add(group);
//		
//		sessionFactory.getCurrentSession().saveOrUpdate(system);
//		sessionFactory.getCurrentSession().getTransaction().commit();
//
//		
//	}
//
//	@SuppressWarnings("unused")
//	private void createSystemWithAFeature() {
//		
//		
//		sessionFactory.getCurrentSession().beginTransaction();
//		ToggleSystem system = new ToggleSystem();
//		system.setSystemName("DAN_TEST");
//		system.setDescription("this is a test");
//		system.setCreatedBy("du80449");
//		system.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		system.setUpdatedBy("chuck");
//		system.setUpdatedTime(system.getCreationTime());
//		
//		Feature feature = new Feature();
//		feature.setName("NEW_FEATURE");
//		feature.setDescription("feature desc");
//		feature.setSystem(system);
//		feature.setCreatedBy("du80449");
//		feature.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		feature.setUpdatedBy(null);
//		feature.setUpdatedTime(null);
//		
//		
//		FeatureStatus status = (FeatureStatus) sessionFactory.getCurrentSession().get(FeatureStatus.class, new Long(1));
//		feature.setFeatureStatus(status);
//		system.getFeatures().add(feature);
//		sessionFactory.getCurrentSession().saveOrUpdate(system);
//		sessionFactory.getCurrentSession().getTransaction().commit();
//	}
//
//	@SuppressWarnings("unused")
//	private void createEmptySystem() {
//		
//		sessionFactory.getCurrentSession().beginTransaction();
//		ToggleSystem system = new ToggleSystem();
//		system.setSystemName("DAN_TEST");
//		system.setDescription("this is a test");
//		system.setCreatedBy("du80449");
//		system.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		system.setUpdatedBy("chuck");
//		system.setUpdatedTime(system.getCreationTime());
//		
//		sessionFactory.getCurrentSession().saveOrUpdate(system);
//		sessionFactory.getCurrentSession().getTransaction().commit();
//	}
//
//	@SuppressWarnings("unused")
//	private void createGroup() {
//		
//		sessionFactory.getCurrentSession().beginTransaction();
//		FeatureGroup group = new FeatureGroup();
//		group.setDescription("test group");
//		group.setCreatedBy("du80449");
//		group.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		group.setUpdatedBy("chuck");
//		group.setUpdatedTime(group.getCreationTime());
//
//		Member member = new Member();
//		member.setMemberId("DU80449");
//		member.setCreatedBy("DU80449");
//		member.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		member.setUpdatedBy("FRED");
//		member.setUpdatedTime(member.getCreationTime());
//		member.setFeatureGroup(group);
//		group.getMembers().add(member);
//		
//		sessionFactory.getCurrentSession().saveOrUpdate(group);
//		sessionFactory.getCurrentSession().getTransaction().commit();
//
//	}
//
//	@SuppressWarnings("unused")
//	private void createMember() {
//	
//		sessionFactory.getCurrentSession().beginTransaction();
//		Member member = new Member();
//		member.setMemberId("DU80449");
//		member.setCreatedBy("DU80449");
//		member.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		member.setUpdatedBy("FRED");
//		member.setUpdatedTime(member.getCreationTime());
//		
//		sessionFactory.getCurrentSession().saveOrUpdate(member);
//		sessionFactory.getCurrentSession().getTransaction().commit();
//		
//	}
//
//	@Transactional
//	private void createFeatureStatusRows() {
//		
//		sessionFactory.getCurrentSession().beginTransaction();
//		
//		FeatureStatus notReleased = new FeatureStatus();
//		notReleased.setName("NOT RELEASED");
//		notReleased.setCreatedBy("DU80449");
//		notReleased.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		notReleased.setUpdatedBy("DU80449");
//		notReleased.setUpdatedTime(notReleased.getCreationTime());
//		
//		FeatureStatus limited = new FeatureStatus();
//		limited.setName("LIMITED");
//		limited.setCreatedBy("DU80449");
//		limited.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		limited.setUpdatedBy("DU80449");
//		limited.setUpdatedTime(limited.getCreationTime());
//		
//		FeatureStatus released = new FeatureStatus();
//		released.setName("LIMITED");
//		released.setCreatedBy("DU80449");
//		released.setCreationTime(new Timestamp(System.currentTimeMillis()));
//		released.setUpdatedBy("DU80449");
//		released.setUpdatedTime(released.getCreationTime());
//		
//		sessionFactory.getCurrentSession().saveOrUpdate(notReleased);
//		sessionFactory.getCurrentSession().saveOrUpdate(limited);
//		sessionFactory.getCurrentSession().saveOrUpdate(released);
//		sessionFactory.getCurrentSession().getTransaction().commit();
//	}
//
//	public static void main(String[] args) throws Exception {
//		DomainObjectsTestProgram program = new DomainObjectsTestProgram();
//		program.run();
//	}
//}
