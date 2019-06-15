package com.gabriel.box.application.vo;

public class DeviceVO {
	private Long id;
	private String boxName;
	private Integer pills;
	private String nextPill;
	private String lastPill;
	private String buyPils;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public Integer getPills() {
		return pills;
	}

	public void setPills(Integer pills) {
		this.pills = pills;
	}

	public String getNextPill() {
		return nextPill;
	}

	public void setNextPill(String nextPill) {
		this.nextPill = nextPill;
	}

	public String getLastPill() {
		return lastPill;
	}

	public void setLastPill(String lastPill) {
		this.lastPill = lastPill;
	}

	public String getBuyPils() {
		return buyPils;
	}

	public void setBuyPils(String buyPils) {
		this.buyPils = buyPils;
	}

}
