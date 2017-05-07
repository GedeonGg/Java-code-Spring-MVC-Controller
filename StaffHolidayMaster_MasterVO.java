package com.jaw.staff.controller;

import java.util.ArrayList;
import java.util.List;

public class StaffHolidayMaster_MasterVO {
	
	private String pageSize="10000";
	private StaffHolidayMasterVO holidayMasterVO=new StaffHolidayMasterVO();
	private StaffHolidayMaster_SearchVO master_SearchVO=new StaffHolidayMaster_SearchVO();
	private List<StaffHolidayMasterListVO> holidayMasterListVOs=new ArrayList<>();
	
	public List<StaffHolidayMasterListVO> getHolidayMasterListVOs() {
		return holidayMasterListVOs;
	}
	public void setHolidayMasterListVOs(
			List<StaffHolidayMasterListVO> holidayMasterListVOs) {
		this.holidayMasterListVOs = holidayMasterListVOs;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	public StaffHolidayMasterVO getHolidayMasterVO() {
		return holidayMasterVO;
	}
	public void setHolidayMasterVO(StaffHolidayMasterVO holidayMasterVO) {
		this.holidayMasterVO = holidayMasterVO;
	}
	public StaffHolidayMaster_SearchVO getMaster_SearchVO() {
		return master_SearchVO;
	}
	public void setMaster_SearchVO(StaffHolidayMaster_SearchVO master_SearchVO) {
		this.master_SearchVO = master_SearchVO;
	}
	@Override
	public String toString() {
		return "StaffHolidayMaster_MasterVO [pageSize=" + pageSize
				+ ", holidayMasterVO=" + holidayMasterVO + ", master_SearchVO="
				+ master_SearchVO + ", holidayMasterListVOs="
				+ holidayMasterListVOs + "]";
	}
	
	

}
