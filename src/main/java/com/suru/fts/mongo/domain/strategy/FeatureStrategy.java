package com.suru.fts.mongo.domain.strategy;

import com.suru.fts.mongo.domain.Feature;

public class FeatureStrategy{

	private static final long serialVersionUID = 1L;

	private String name;
	private String systemName;
	private String featureName;
//	private Feature feature;
//
//	public Feature getFeature() {
//		return feature;
//	}
//
//	public void setFeature(Feature feature) {
//		this.feature = feature;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
	

}
