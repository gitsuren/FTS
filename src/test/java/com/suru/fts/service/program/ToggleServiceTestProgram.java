//package com.suru.fts.service.program;
//
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Random;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.deere.jdc.jdrpshared.domain.DomainObject;
//import com.suru.fts.mongo.domain.Feature;
//import com.suru.fts.mongo.domain.FeatureGroup;
//import com.suru.fts.mongo.domain.FeatureStatus;
//import com.suru.fts.mongo.domain.Member;
//import com.suru.fts.mongo.domain.ToggleSystem;
//import com.suru.fts.mongo.domain.strategy.FeatureStrategy;
//import com.suru.fts.mongo.domain.strategy.GroupStrategy;
//import com.suru.fts.repository.ToggleRepository;
//import com.suru.fts.service.ToggleService;
//
//public class ToggleServiceTestProgram extends SpringConfigTestProgram {
//
//	private static final String ME = "SB95447";
//
//	@Autowired
//	private ToggleService toggleService;
//
//	@Autowired
//	private ToggleRepository toggleRep;
//
//	@Autowired
//	private SessionFactory sessionFactory;
//
//	@Override
//	@Transactional
//	protected void execute() throws Exception {
//
//		// runRandom();
//		// testGet();
//		// testNewSystem("MYCI4", "pepper 1");
//		// testDeleteSystem("pepper");
//		// updateFeatureGroup();
//		// testCheckForGroupUsage();
//
//		// recreateFriedDevlDataAfterRefresh();
//		// addMembersToFeatGrp("JDIC_NEW_ACR_DATA","DR16947", "JG28510");
//
//	}
//
//	@SuppressWarnings("unused")
//	private void recreateFriedDevlDataAfterRefresh() {
//		MyFeature f1 = new MyFeature("NEW_ACR_DATA", "NEW_ACR_DATA",
//				FeatureStatus.LIMITED_ID);
//		MyFeature f2 = new MyFeature("MYJD_FILE_IMPORT", "MYJD_FILE_IMPORT",
//				FeatureStatus.LIMITED_ID);
//		MyFeature f3 = new MyFeature("BFR_PART2",
//				"BEGINNING FARMER RANCHER PART 2", FeatureStatus.RELEASED_ID);
//		MyFeature f4 = new MyFeature("PRODUCTION_TEST", "PRODUCTION_TEST",
//				FeatureStatus.NOT_RELEASED_ID);
//		testNewSystem("JDA", "JOHN DEERE AGENT", f1, f2, f3, f4);
//	}
//
//	@SuppressWarnings("unused")
//	private void testCheckForGroupUsage() {
//		FeatureGroup featureGroup = toggleService.getGroup("TEST2");
//		List<GroupStrategy> results = toggleService
//				.getStratagiesByGroup(featureGroup);
//
//	}
//
//	@SuppressWarnings("unused")
//	private void runRandom() {
//
//		int outsideCount = 0;
//		int max = 999999;
//		int min = 100000;
//		Random random = new Random();
//		for (int i = 0; i < Integer.MAX_VALUE; i++) {
//			int nextRandom = random.nextInt(max - min) + min;
//			if (nextRandom < min || max > max) {
//				outsideCount++;
//			}
//		}
//		System.out.println("Count: " + outsideCount);
//	}
//
//	@SuppressWarnings("unused")
//	private void testDeleteSystem(String systemName) {
//		ToggleSystem ts = toggleService.getSystem(systemName);
//		toggleService.deleteSystem(ts);
//		System.out.println("Deleted " + systemName);
//
//	}
//
//	@SuppressWarnings("unused")
//	private void updateFeatureGroup() {
//
//		ToggleSystem ts = toggleRep.getSystemByName("MYCI3");
//		for (Feature feature : ts.getFeatures()) {
//			for (FeatureStrategy fs : feature.getStrategies()) {
//				if (fs instanceof GroupStrategy) {
//					GroupStrategy gs = (GroupStrategy) fs;
//					for (FeatureGroup group : gs.getGroups()) {
//						Member mem = new Member();
//						mem.setFeatureGroup(group);
//						mem.setMemberId("Suren");
//						group.getMembers().add(mem);
//					}
//				}
//			}
//		}
//		toggleRep.save(ts);
//	}
//
//	private void testNewSystem(final String systemName,
//			final String systemDescription, final MyFeature... features) {
//
//		ToggleSystem ts = buildToggleSystem(systemName, systemDescription);
//
//		FeatureStatus status;
//
//		for (MyFeature myFeature : features) {
//			status = (FeatureStatus) sessionFactory.getCurrentSession().get(
//					FeatureStatus.class, myFeature.statusId);
//			Feature f = buildFeature(ts, status, myFeature.name, myFeature.desc);
//
//			GroupStrategy gs = new GroupStrategy();
//			gs.setName("GROUP");
//			gs.setFeature(f);
//
//			String featGrpName = String.format("%s_%S", "JDIC", myFeature.name);
//			FeatureGroup fg = addMembersToFeatGrp(featGrpName, ME, "SM82818", "DU80449", "SV29200", "DR16947", "JG28510", "SELENIUMTESTIT");
//
//			gs.getGroups().add(fg);
//			f.getStrategies().add(gs);
//			ts.getFeatures().add(f);
//
//			addAuditFields(ME, gs, fg, f, ts);
//
//			toggleRep.save(ts);
//		}
//	}
//
//	private FeatureGroup addMembersToFeatGrp(final String featGrpName, final String... memberNames) {
//		FeatureGroup fg = toggleRep.getGroupByName(featGrpName);
//		if (null == fg) {
//			fg = new FeatureGroup();
//			fg.setDescription(featGrpName);
//		}		
//		addMember(fg, memberNames);
//		return fg;
//	}
//
//	private void addMember(FeatureGroup fg, String... memberNames) {
//		for (String memberName : memberNames) {
//			Member m = new Member();
//			m.setMemberId(memberName);
//			m.setFeatureGroup(fg);
//			fg.getMembers().add(m);
//			addAuditFields(ME, m, fg);
//			toggleRep.save(fg);
//		}
//	}
//
//	private void addAuditFields(String userName, DomainObject... dmObjs) {
//		final Timestamp now = new Timestamp(System.currentTimeMillis());
//		for (DomainObject dmObj : dmObjs) {
//			dmObj.setCreatedBy(userName);
//			dmObj.setCreationTime(now);
//			dmObj.setUpdatedBy(userName);
//			dmObj.setUpdatedTime(now);
//		}
//	}
//
//	private ToggleSystem buildToggleSystem(String systemName, String desc) {
//		ToggleSystem ts = new ToggleSystem();
//		ts.setSystemName(systemName);
//		ts.setDescription(desc);
//		return ts;
//	}
//
//	private Feature buildFeature(ToggleSystem ts, FeatureStatus status,
//			String featureName, String featureDesc) {
//		Feature f = new Feature();
//		f.setDescription(featureDesc);
//		f.setSystem(ts);
//		f.setFeatureStatus(status);
//		f.setName(featureName);
//		return f;
//	}
//
//	public class MyFeature {
//		public String name;
//		public String desc;
//		public Long statusId;
//
//		public MyFeature(String name, String desc, Long statusId) {
//			this.name = name;
//			this.desc = desc;
//			this.statusId = statusId;
//
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		ToggleServiceTestProgram program = new ToggleServiceTestProgram();
//		program.run();
//	}
//
//}
