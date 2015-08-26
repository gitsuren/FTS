package com.suru.fts.dto;

public class ToggleSystemFormBean {

	private String systemName;
	private String description;
	private String detailHref;
	private String editHref;
	private String deleteHref;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEditHref() {
		return editHref;
	}

	public void setEditHref(String editHref) {
		this.editHref = editHref;
	}

	public String getDeleteHref() {
		return deleteHref;
	}

	public void setDeleteHref(String deleteHref) {
		this.deleteHref = deleteHref;
	}

	public String getDetailHref() {
		return detailHref;
	}

	public void setDetailHref(String detailHref) {
		this.detailHref = detailHref;
	}

}
