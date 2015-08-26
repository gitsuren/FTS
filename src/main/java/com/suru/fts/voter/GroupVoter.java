package com.suru.fts.voter;

import org.springframework.stereotype.Component;

import com.suru.fts.mongo.domain.FeatureGroup;
import com.suru.fts.mongo.domain.Member;
import com.suru.fts.mongo.domain.strategy.GroupStrategy;

@Component(value = "groupVoter")
public class GroupVoter implements ToggleVoter<GroupStrategy> {

	public static String ID_PARAMETER = "ID";
	
	@Override
	public boolean vote(final String id, final GroupStrategy featureStrategy) {
		
		for (FeatureGroup group : featureStrategy.getGroups()) {
			for (Member member : group.getMembers()) {
				if (member.getMemberId().equalsIgnoreCase(id)) {
					return true;
				}
			}
		}
		return false;
	}
}
