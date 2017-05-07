package com.jaw.staff.controller;

public class StaffHolidayMasterVO {
	
	private String stfLvCat;
	private String holDate;
	private String holDesc;
	
	public String getStfLvCat() {
		return stfLvCat;
	}
	public void setStfLvCat(String stfLvCat) {
		this.stfLvCat = stfLvCat;
	}
	public String getHolDate() {
		return holDate;
	}
	public void setHolDate(String holDate) {
		this.holDate = holDate;
	}
	public String getHolDesc() {
		return holDesc;
	}
	public void setHolDesc(String holDesc) {
		this.holDesc = holDesc;
	}
	@Override
	public String toString() {
		return "StaffHolidayMasterVO [stfLvCat=" + stfLvCat + ", holDate="
				+ holDate + ", holDesc=" + holDesc + "]";
	}
	

}
