package com.suru.fts.mongo.domain;


public class FeatureStatus{

	public static final long NOT_RELEASED_ID = 0L;
	public static final long LIMITED_ID = 1L;
	public static final long RELEASED_ID = 2L;

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null || !EqualsUtil.isAssignable(this, obj)) {
//			return false;
//		}
//
//		return new EqualsBuilder().append(getId(),
//				((FeatureStatus) obj).getId()).isEquals();
//
//	}
//
//	@Override
//	public int hashCode() {
//		return new HashCodeBuilder().append(getId()).toHashCode();
//	}
	
	

}
