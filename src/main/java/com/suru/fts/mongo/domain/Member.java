package com.suru.fts.mongo.domain;

public class Member{

	private static final long serialVersionUID = 1L;

	private String memberId;
	private String featureGroupName;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(final String memberId) {
		this.memberId = memberId;
	}

	public String getFeatureGroupName() {
		return featureGroupName;
	}

	public void setFeatureGroupName(String featureGroupName) {
		this.featureGroupName = featureGroupName;
	}

//	public FeatureGroup getFeatureGroup() {
//		return featureGroup;
//	}
//
//	public void setFeatureGroup(final FeatureGroup featureGroup) {
//		this.featureGroup = featureGroup;
//	}
	
	

}
