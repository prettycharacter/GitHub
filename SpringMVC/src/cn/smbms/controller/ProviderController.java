package cn.smbms.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.utils.Constants;
import cn.smbms.utils.PageSupport;

@Controller
@RequestMapping("/pro")
public class ProviderController {
	private Logger logger=Logger.getLogger(ProviderController.class);
	@Resource
	private ProviderService providerService;
	@RequestMapping("/proList")
	public String ProviderList(Model model,
							  @RequestParam(value="queryProCode",required=false)String queryProCode,
							  @RequestParam(value="queryProName",required=false)String queryProName,
							  @RequestParam(value="pageIndex",required=false)String pageIndex) {
		Integer currentPageNo=1;
		if(pageIndex!=null) {
			currentPageNo=Integer.parseInt(pageIndex);
		}
		int pageSize=Constants.pageSize;
		
		List<Provider> providerList=providerService.getProviderList(queryProName, queryProCode, currentPageNo, pageSize);
		PageSupport pageSupport=new PageSupport();
		pageSupport.setCurrentPageNo(currentPageNo);
		pageSupport.setPageSize(pageSize);
		int totalCount=providerService.getProCount(queryProCode, queryProName);
		pageSupport.setTotalCount(totalCount);
		model.addAttribute("providerList", providerList);
		model.addAttribute("queryProCode", queryProCode);
		model.addAttribute("queryProName", queryProName);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentPageNo", currentPageNo);
		model.addAttribute("totalPageCount", pageSupport.getTotalPageCount());
		logger.info("+++++++"+providerList.size());
		logger.info("+++++++"+totalCount);
		logger.info("+++++++"+currentPageNo);
		logger.info("+++++++"+pageSupport.getTotalPageCount());
		return "providerlist";
	}
	@RequestMapping("/proadd")
	public String providerAdd(@ModelAttribute("provider")Provider provider) {
		return "provideradd";
	}
	@RequestMapping("/prosave")
	public String providerSave(Provider provider,HttpSession session) {
		provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		provider.setCreationDate(new Date());
		if(providerService.add(provider)) {
			return "redirect:/pro/proList";
		}
		return "provideradd";
	}
   @RequestMapping(value="/proupdate/{id}",method=RequestMethod.GET)
   public String providerUpdate(@PathVariable String id,Model model) {
	   logger.info("进入providerUpdate方法----------------=-----------");
	   Provider provider = providerService.getProviderById(id);
	   model.addAttribute(provider);
	   return "providermodify";
   }
   @RequestMapping(value="/proupdatesave")
   public String proUpdateSave(Provider provider,HttpSession session) {
	   logger.info("进入providerUpdateSave方法--------------------------------");
	   provider.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
	   provider.setModifyDate(new Date());
	   if(providerService.modify(provider)) {
		   return "redirect:/pro/proList";
	   }
	   return "providermodify";
   }
  @RequestMapping(value="/proview/{id}",method=RequestMethod.GET)
  public String proView(@PathVariable String id,Model model){
	  logger.info("进入proview------------------------------------------");
	  Provider provider=providerService.getProviderById(id);
	  model.addAttribute(provider);
	  return "providerview";
  }
}
