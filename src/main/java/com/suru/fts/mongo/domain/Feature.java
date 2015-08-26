package com.suru.fts.mongo.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.suru.fts.mongo.domain.strategy.FeatureStrategy;

public class Feature{

	private static final long serialVersionUID = -482614376742329014L;

	private String name;
	private String description;
	private FeatureStatus featureStatus;
	private List<FeatureStrategy> strategies = new ArrayList<>();
	//private ToggleSystem system;
	private String systemName;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<FeatureStrategy> getStrategies() {
		return strategies;
	}

	public void setStrategies(final List<FeatureStrategy> strategies) {
		this.strategies = strategies;
	}

//	public ToggleSystem getSystem() {
//		return system;
//	}
//
//	public void setSystem(final ToggleSystem system) {
//		this.system = system;
//	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public FeatureStrategy getStrategyByName(String strategyName) {

		for (FeatureStrategy strategy : strategies) {
			if (strategy.getName().equalsIgnoreCase(strategyName)) {
				return strategy;
			}
		}
		return null;
	}

	public FeatureStatus getFeatureStatus() {
		return featureStatus;
	}

	public void setFeatureStatus(FeatureStatus featureStatus) {
		this.featureStatus = featureStatus;
	}
}
