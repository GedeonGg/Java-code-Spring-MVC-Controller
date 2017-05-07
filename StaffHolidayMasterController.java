package com.jaw.staff.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.CustomAndSubjectCodeExistException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.service.IStaffHolidayMasterService;
import com.jaw.staff.service.StaffHolidayMasterService;

@Controller
public class StaffHolidayMasterController {
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IStaffHolidayMasterService staffHolidayMasterService;
	Logger logger=Logger.getLogger(StaffHolidayMasterService.class);
	@RequestMapping(value="/stfHolMaster", method = RequestMethod.GET)
	public ModelAndView staffholidaymaster(@ModelAttribute("stfhlMaster") StaffHolidayMaster_MasterVO master_MasterVO,
			HttpServletRequest httpServletRequest, HttpSession session,
			BindingResult result, HttpServletResponse response, ModelMap model)
					throws IOException, NoDataFoundException {
		logger.debug("Inside Get Method");
		ModelAndView modelAndView=new ModelAndView(ModelAndViewConstant.STAFF_HOL_MASTER_LIST, "stfhlMaster", master_MasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "sessionMap", null);
		Map sessionMap = new HashMap();
		sessionMap.put("searchVo", null);
		sessionMap.put("display_tbl", null);
		sessionMap.put("success", null);
		sessionMap.put("sessionMap", null);
		WebUtils.setSessionAttribute(httpServletRequest, "sessionMap",
				sessionMap);
		getStaffLeaveCatgory(session, httpServletRequest, response, model);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
		modelAndView.getModelMap().addAttribute("status", "true");
		return modelAndView;
	}
	private void getStaffLeaveCatgory(HttpSession session,
			HttpServletRequest httpServletRequest,
			HttpServletResponse response, ModelMap model) {
		// TODO Auto-generated method stub
		Map mapList = (Map) WebUtils.getSessionAttribute(httpServletRequest, "sessionMap");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		if((mapList!=null)&&(!mapList.containsKey("lvCatList"))){
			try {
				if(sessionCache.getStaffSession()!=null)
				{
					map = dropDownListService.getStaffLeaveCatList(sessionCache.getUserSessionDetails().getInstId(),
							sessionCache.getUserSessionDetails().getBranchId(),
							sessionCache.getStaffSession().getStfGrpId());
				}else
				{
					map = dropDownListService.getStaffLeaveCatList(sessionCache.getUserSessionDetails().getInstId(),
							sessionCache.getUserSessionDetails().getBranchId(),
							null);
				}
			} catch (NoDataFoundException e) {
			}	
		}
		mapList.put("lvCatList", map);

	}
	//List Method
	@RequestMapping(value="/stfHolMaster",method=RequestMethod.GET, params = {"Get"})
	public String listStaffSpecWrkTime(@ModelAttribute("stfhlMaster") StaffHolidayMaster_MasterVO master_MasterVO,
			HttpServletRequest httpServletRequest,HttpServletResponse response,
			ModelMap model,HttpSession session,RedirectAttributes redirectAttributes)
					throws NoDataFoundException
					{
		Map sessionMap=(Map)WebUtils.getSessionAttribute(httpServletRequest, "sessionMap");
		SessionCache sessionCache=(SessionCache) session.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView=new ModelAndView(ModelAndViewConstant.STAFF_HOL_MASTER_LIST, "stfhlMaster", master_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "error");
		if(httpServletRequest.getParameter("Get").equals("Get"))
		{
			sessionMap.put("searchVo", master_MasterVO.getMaster_SearchVO());
			sessionMap.put("addAction", null);
		}
		else
		{
			StaffHolidayMaster_SearchVO masterSearch =(StaffHolidayMaster_SearchVO) sessionMap.get("searchVo");
			master_MasterVO.setMaster_SearchVO(masterSearch);
			sessionMap.put("addAction", "AddAction");
		}
		staffHolidayMasterService.SelectStaffHolidayMasterRec(master_MasterVO, sessionCache);
		sessionMap.put("display_tbl", master_MasterVO.getHolidayMasterListVOs());
		master_MasterVO.setPageSize(master_MasterVO.getPageSize());
		redirectAttributes.addFlashAttribute("stfhlMaster", master_MasterVO);
		return "redirect:/stfHolMaster.htm?data";
					}
	@RequestMapping(value = "/stfHolMaster", method = RequestMethod.GET, params= "data")
	public ModelAndView methodComeBack(@ModelAttribute("stfhlMaster") StaffHolidayMaster_MasterVO master_MasterVO,
			HttpServletRequest servletRequest,ModelMap model)
	{
		Map sessionMap=(Map)WebUtils.getSessionAttribute(servletRequest, "sessionMap");
		StaffHolidayMaster_SearchVO staffSearch=(StaffHolidayMaster_SearchVO) sessionMap.get("searchVo");
		master_MasterVO.setMaster_SearchVO(staffSearch);
		ModelAndView modelAndView=new ModelAndView(ModelAndViewConstant.STAFF_HOL_MASTER_LIST, "stfhlMaster" ,master_MasterVO);
		return modelAndView;
	}
	//insert method
	@RequestMapping(value = "/stfHolMaster", method = RequestMethod.GET, params= "insert")
	public String insert(@ModelAttribute("stfhlMaster") StaffHolidayMaster_MasterVO master_MasterVO,
			HttpServletRequest httpServletRequest,HttpServletResponse response,ModelMap model,UserSessionDetails userSessionDetails, HttpSession session,
			RedirectAttributes redirectAttributes)
					throws DuplicateEntryException, UpdateFailedException, DatabaseException {
		{
			Map sessionMap = (Map) WebUtils.getSessionAttribute(httpServletRequest, "sessionMap");
			SessionCache sessionCache = (SessionCache) session.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ModelAndView modelAndView = new ModelAndView(ModelAndViewConstant.STAFF_HOL_MASTER_LIST, "stfhlMaster", master_MasterVO);
			httpServletRequest.setAttribute("page", modelAndView);
			httpServletRequest.setAttribute("errors", "erroradd");
			StaffHolidayMaster_SearchVO holidaySearch = (StaffHolidayMaster_SearchVO) sessionMap.get("searchVo");
			master_MasterVO.setMaster_SearchVO(holidaySearch);
			master_MasterVO.getHolidayMasterVO().setStfLvCat(httpServletRequest.getParameter("stflevcatt"));
			master_MasterVO.getHolidayMasterVO().setHolDate(httpServletRequest.getParameter("holdate"));
			master_MasterVO.getHolidayMasterVO().setHolDesc(httpServletRequest.getParameter("desc"));
			staffHolidayMasterService.insertStaffHolidayMasterRec(master_MasterVO, sessionCache);
			sessionMap.put("success",ErrorCodeConstant.ADD_SUCCESS_MESS);
			logger.debug("Data's successfully inserted ");
			return "redirect:/stfHolMaster.htm?Get";
		}
	}
	//Edit
	@RequestMapping(value="/stfHolMaster",method=RequestMethod.GET,params={"actionGet"})
	public String update(@ModelAttribute("stfhlMaster") StaffHolidayMaster_MasterVO master_MasterVO,
			HttpServletRequest servletRequest,HttpSession session,UserSessionDetails userSessionDetails ,
			RedirectAttributes redirectAttributes)throws UpdateFailedException,NoDataFoundException,
			DuplicateEntryException, DatabaseException,TableNotSpecifiedForAuditException
			{	
		Map sessionMap=(Map)WebUtils.getSessionAttribute(servletRequest, "sessionMap");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		ModelAndView modelAndView=new ModelAndView(ModelAndViewConstant.STAFF_HOL_MASTER_LIST, "stfhlMaster",master_MasterVO);
		servletRequest.setAttribute("page", modelAndView);
		servletRequest.setAttribute("errors", "erroradd");
		StaffHolidayMaster_SearchVO stfSpecWrkTimeSrchVo=(StaffHolidayMaster_SearchVO) sessionMap.get("searchVo");
		master_MasterVO.setMaster_SearchVO(stfSpecWrkTimeSrchVo);
		SessionCache sessionCache=(SessionCache) session.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		master_MasterVO.getHolidayMasterVO().setHolDesc(servletRequest.getParameter("holdesc"));
		master_MasterVO.getHolidayMasterVO().setStfLvCat(servletRequest.getParameter("leaveCattt"));
		master_MasterVO.getHolidayMasterVO().setHolDate(servletRequest.getParameter("holDateee"));
		staffHolidayMasterService.UpdateStaffHolidayMasterRec(master_MasterVO, userSessionDetails, sessionCache, applicationCache);
		redirectAttributes.addFlashAttribute("stfhlMaster", master_MasterVO);
		logger.debug("Data's Updated successfully!");
		sessionMap.put("success", ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return "redirect:/stfHolMaster.htm?Get";
			}

	//Delete Method
	@RequestMapping(value="/stfHolMaster",method=RequestMethod.GET,params={"actionDelete"})
	public String deleteMethod(@ModelAttribute("stfhlMaster") StaffHolidayMaster_MasterVO master_MasterVO,
			HttpServletRequest servletRequest,HttpSession session,UserSessionDetails userSessionDetails ,
			RedirectAttributes redirectAttributes)throws UpdateFailedException,NoDataFoundException,
			DuplicateEntryException, DatabaseException,DeleteFailedException, TableNotSpecifiedForAuditException {	
		logger.debug("Inside Update Method");
		Map sessionMap=(Map)WebUtils.getSessionAttribute(servletRequest, "sessionMap");
		ModelAndView modelAndView=new ModelAndView(ModelAndViewConstant.STAFF_HOL_MASTER_LIST, "stfhlMaster",master_MasterVO);
		sessionMap.put("success", null);
		servletRequest.setAttribute("page", modelAndView);
		servletRequest.setAttribute("errors", "erroradd");
		StaffHolidayMaster_SearchVO stfSpecWrkTimeSrchVo=(StaffHolidayMaster_SearchVO) sessionMap.get("searchVo");
		master_MasterVO.setMaster_SearchVO(stfSpecWrkTimeSrchVo);
		SessionCache sessionCache=(SessionCache) session.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session.getServletContext().
				getAttribute(ApplicationConstant.APPLICATION_CACHE);
		master_MasterVO.getHolidayMasterVO().setHolDesc(servletRequest.getParameter("holdesc"));
		master_MasterVO.getHolidayMasterVO().setStfLvCat(servletRequest.getParameter("leaveCattt"));
		master_MasterVO.getHolidayMasterVO().setHolDate(servletRequest.getParameter("holDateee"));
		staffHolidayMasterService.DeleteStaffHolidayMasterRec(master_MasterVO, sessionCache, applicationCache);
		redirectAttributes.addFlashAttribute("stfhlMaster", master_MasterVO);
		logger.debug("Data's Deleted successfully!");
		sessionMap.put("success", ErrorCodeConstant.DELETE_SUCCESS_MESS);
		WebUtils.setSessionAttribute(servletRequest, "keepStatus", "status");
		return "redirect:/stfHolMaster.htm?Get";
	}
	@ExceptionHandler({ DatabaseException.class, UpdateFailedException.class,
		DeleteFailedException.class,
		TableNotSpecifiedForAuditException.class,
		CustomAndSubjectCodeExistException.class,DatabaseException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.error("inside Exception Handler method :" + ex.getMessage());
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		String error = (String) request.getAttribute("errors");
		modelAndView.getModelMap().addAttribute(error, ex.getMessage());
		Map mapList = (Map) WebUtils.getSessionAttribute(request, "sessionMap");
		mapList.put("success", null);

		return modelAndView;
	}

	@ExceptionHandler({ NoDataFoundException.class,
		DuplicateEntryException.class })
	public ModelAndView handleNoDataException(Exception ex,
			HttpSession session, HttpServletRequest request) {
		logger.error("Exception Handledddd :" + ex.getMessage());
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		Map mapList = (Map) WebUtils.getSessionAttribute(request, "sessionMap");
		String message = (String) mapList.get("success");
		if ((message != null)
				&& (message.equals(ErrorCodeConstant.DELETE_SUCCESS_MESS))) {
		} else {
			mapList.put("success", null);
			modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		}
		mapList.put("display_tbl", null);
		return modelAndView;
	}

}
