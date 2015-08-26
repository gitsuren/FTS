package com.suru.fts.mongo.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "featureGroups")
public class FeatureGroup{

	private static final long serialVersionUID = 1L;

	@Id
	private String description;

	private Set<Member> members = new HashSet<>();

	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(final Set<Member> members) {
		this.members = members;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Member getMember(final String memberName) {
		for (Member member : members) {
			if (member.getMemberId().equalsIgnoreCase(memberName)) {
				return member;
			}
		}
		return null;
	}
}
