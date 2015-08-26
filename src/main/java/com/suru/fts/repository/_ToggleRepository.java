package com.suru.fts.repository;
//package com.suru.fts.repository;
//
//import java.util.List;
//
//import org.hibernate.Criteria;
//import org.hibernate.SessionFactory;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.suru.fts.mongo.domain.FeatureGroup;
//import com.suru.fts.mongo.domain.FeatureStatus;
//import com.suru.fts.mongo.domain.ToggleSystem;
//import com.suru.fts.mongo.domain.strategy.GroupStrategy;
//
//
//@Repository
//public class ToggleRepository {
//
//	@Autowired
//	private SessionFactory sessionFactory;
//
//
//	public ToggleSystem getSystemByName(final String systemName) {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ToggleSystem.class);
//		criteria.add(Restrictions.eq("systemName", systemName).ignoreCase());
//		return (ToggleSystem) criteria.uniqueResult();
//	}
//
//
//	@Transactional
//	public void save(ToggleSystem ts) {
//		sessionFactory.getCurrentSession().saveOrUpdate(ts);
//	}
//
//
//	@SuppressWarnings("unchecked")
//	public List<ToggleSystem> getAllSystems() {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ToggleSystem.class);
//		return criteria.list();
//	}
//
//
//	@Transactional
//	public void deleteSystem(final ToggleSystem system) {
//		sessionFactory.getCurrentSession().delete(system);
//	}
//
//
//	public FeatureGroup getGroupByName(final String name) {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FeatureGroup.class);
//		criteria.add(Restrictions.eq("description", name).ignoreCase());
//		return (FeatureGroup) criteria.uniqueResult();
//	}
//
//
//	@Transactional
//	public void save(final FeatureGroup group) {
//
//		sessionFactory.getCurrentSession().saveOrUpdate(group);
//	}
//
//
//	@Transactional
//	public void deleteGroup(FeatureGroup featureGroup) {
//		
//		sessionFactory.getCurrentSession().delete(featureGroup);
//	}
//
//	public FeatureStatus getFeatureStatusByName(final String name) {
//		
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FeatureStatus.class);
//		criteria.add(Restrictions.eq("name", name));
//		return (FeatureStatus) criteria.uniqueResult();
//	}
//	
//	public FeatureStatus getFeatureStatusById(final long id) {
//		
//		return (FeatureStatus) sessionFactory.getCurrentSession().get(FeatureStatus.class, Long.valueOf(id));
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<FeatureGroup> getAllGroups() {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FeatureGroup.class);
//		return criteria.list();
//	}
//
//
//	@SuppressWarnings("unchecked")
//	public List<GroupStrategy> getStratagiesByGroup(final FeatureGroup featureGroup) {
//		
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GroupStrategy.class);
//		criteria.createAlias("groups", "groups");
//		criteria.add(Restrictions.eq("groups.id", featureGroup.getId()));
//		return criteria.list();
//	}
//}
