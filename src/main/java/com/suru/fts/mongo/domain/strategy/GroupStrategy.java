package com.suru.fts.mongo.domain.strategy;

import java.util.HashSet;
import java.util.Set;

import com.suru.fts.mongo.domain.FeatureGroup;

public class GroupStrategy extends FeatureStrategy {

	private static final long serialVersionUID = 1L;

	private Set<FeatureGroup> groups = new HashSet<>();

	public Set<FeatureGroup> getGroups() {
		return groups;
	}

	public void setGroups(final Set<FeatureGroup> groups) {
		this.groups = groups;
	}

	public FeatureGroup getGroup(final String groupName) {

		for (FeatureGroup group : groups) {
			if (group.getDescription().equalsIgnoreCase(groupName)) {
				return group;
			}
		}
		return null;
	}
}
