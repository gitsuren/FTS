package com.suru.fts.voter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suru.fts.mongo.domain.strategy.FeatureStrategy;

public class ToggleVoterManager {

	private Map<String, List<ToggleVoter<?>>> voters = new HashMap<>();
	
	public List<ToggleVoter<?>> getVoters(final FeatureStrategy featureStrategy) {
		
		return voters.get(featureStrategy.getClass().getName());
	}

	public Map<String, List<ToggleVoter<?>>> getToggleVoters() {
		return voters;
	}

	public void setToggleVoters(Map<String, List<ToggleVoter<?>>> voters) {
		this.voters = voters;
	}
}
