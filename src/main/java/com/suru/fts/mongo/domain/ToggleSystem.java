package com.suru.fts.mongo.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "systems")
public class ToggleSystem {

	private static final long serialVersionUID = 1L;

	@Id
	private String systemName;
	private String description;
	private List<Feature> features = new ArrayList<>();

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

//	public Set<Feature> getFeatures() {
//		return features;
//	}
//
//	public void setFeatures(final Set<Feature> features) {
//		this.features = features;
//	}
	
	

	public Feature getFeatureByName(String featureName) {
		for (Feature feature : features) {
			if (feature.getName().equalsIgnoreCase(featureName)) {
				return feature;
			}
		}
		return null;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

}
