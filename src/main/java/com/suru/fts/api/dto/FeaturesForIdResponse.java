package com.suru.fts.api.dto;

import java.util.ArrayList;
import java.util.List;

public class FeaturesForIdResponse {

	private String systemName;
	private String id;	
	private List<String> features = new ArrayList<>();
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getFeatures() {
		return features;
	}
	public void setFeatures(List<String> features) {
		this.features = features;
	}
	
	
}
