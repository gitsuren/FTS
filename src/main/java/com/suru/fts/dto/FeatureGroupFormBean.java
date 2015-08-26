package com.suru.fts.dto;

public class FeatureGroupFormBean {

	private String groupName;
	private String detailHref;
	private String deleteHref;
	private String addMemberHref;
	private String removeFeatureGroupHref;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDetailHref() {
		return detailHref;
	}

	public void setDetailHref(String detailHref) {
		this.detailHref = detailHref;
	}

	public String getDeleteHref() {
		return deleteHref;
	}

	public void setDeleteHref(String deleteHref) {
		this.deleteHref = deleteHref;
	}

	public String getAddMemberHref() {
		return addMemberHref;
	}

	public void setAddMemberHref(String addMemberHref) {
		this.addMemberHref = addMemberHref;
	}

	public String getRemoveFeatureGroupHref() {
		return removeFeatureGroupHref;
	}

	public void setRemoveFeatureGroupHref(String removeFeatureGroupHref) {
		this.removeFeatureGroupHref = removeFeatureGroupHref;
	}

}
