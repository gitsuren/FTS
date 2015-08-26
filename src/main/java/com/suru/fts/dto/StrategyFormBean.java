package com.suru.fts.dto;

public class StrategyFormBean implements IStrategyFormBean {

	private String strategyName;
	private String strategyType;
	private String deleteHref;
	private String detailHref;

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	@Override
	public String getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
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
