package com.jaw.staff.controller;

public class StaffHolidayMasterListVO {
	private String stfLvCat;
	private String holDate;
	private String holDesc;
	private int rowId;
	private String hrefIcon;
	
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
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getHrefIcon() {
		return hrefIcon;
	}
	public void setHrefIcon(String hrefIcon) {
		this.hrefIcon = hrefIcon;
	}
	@Override
	public String toString() {
		return "StaffHolidayMasterListVO [stfLvCat=" + stfLvCat + ", holDate="
				+ holDate + ", holDesc=" + holDesc + ", rowId=" + rowId
				+ ", hrefIcon=" + hrefIcon + "]";
	}
	

}
