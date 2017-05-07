package com.jaw.staff.controller;

public class StaffHolidayMaster_SearchVO {
	
	private String stfLvCat;
	private String fromDate;
	private String ToDate;
	
	@Override
	public String toString() {
		return "StaffHolidayMaster_SearchVO [stfLvCat=" + stfLvCat
				+ ", fromDate=" + fromDate + ", ToDate=" + ToDate + "]";
	}
	public String getStfLvCat() {
		return stfLvCat;
	}
	public void setStfLvCat(String stfLvCat) {
		this.stfLvCat = stfLvCat;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return ToDate;
	}
	public void setToDate(String toDate) {
		ToDate = toDate;
	}
	

}
