package com.suru.fts.dto;

public class FeatureFormBean {
	private String featureName;
	private String description;
	private String status;
	private String systemName;
	private String detailHref;
	private String editHref;
	private String deleteHref;
	private String addStrategyHref;
	
	private boolean released;
	private boolean notReleased;
	private boolean limited;
	
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		released = status.equals("RELEASED");
		notReleased = status.equals("NOT_RELEASED");
		limited = status.equals("LIMITED");
	}
	public String getDetailHref() {
		return detailHref;
	}
	public void setDetailHref(String detailHref) {
		this.detailHref = detailHref;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
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
	public boolean isReleased() {		
		return released;
	}
	public boolean isNotReleased() {		
		return notReleased;
	}
	public boolean isLimited() {		
		return limited;
	}
	public String getAddStrategyHref() {
		return addStrategyHref;
	}
	public void setAddStrategyHref(String addStrategyHref) {
		this.addStrategyHref = addStrategyHref;
	}	
}
