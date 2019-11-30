package cn.smbms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Past;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.utils.Constants;
import cn.smbms.utils.PageSupport;

@Controller
@RequestMapping("/bill")
public class BillController extends BaseController {
	 private Logger logger=Logger.getLogger(BillController.class);
	@Resource 
	private BillService billService;
	@Resource
	 private ProviderService providrService;
	//订单列表
	@RequestMapping(value="/billList")
	public String billList(Model model,
			               @RequestParam(value="queryProductName",required=false) String queryProductName,
			               @RequestParam(value="queryProviderId",required=false) String queryProviderId,
			               @RequestParam(value="queryIsPayment",required=false) String queryIsPayment,
			               @RequestParam(value="pageIndex",required=false) String pageIndex) {
		logger.info("商品名称========="+queryProductName);
		logger.info("供应商名称========="+queryProviderId);
		logger.info("是否付款========="+queryIsPayment);
		logger.info("页码数========="+pageIndex);
		Integer pageNo=1;
		Integer providerId=null;
		Integer isPayment=null;
		List<Bill> billList=null;
		PageSupport pageSupport=null;
		List<Provider> providerList=null;
		int totalCount=0;
		try {
			if(queryProviderId!=null&&queryProviderId!="") {
				providerId=Integer.parseInt(queryProviderId);
			}
			if(queryIsPayment!=null&&queryIsPayment!="") {
				isPayment=Integer.parseInt(queryIsPayment);
			}
			if(pageIndex!=null&&pageIndex!="") {
				pageNo=Integer.parseInt(pageIndex);
			}
			Integer pageSize=Constants.PAGE_SIZE;
			billList=billService.getBillList(queryProductName, providerId, isPayment, pageNo, pageSize);
			pageSupport=new PageSupport();
			pageSupport.setPageSize(pageSize);
			pageSupport.setCurrentPageNo(pageNo);
		    totalCount=billService.getBillCount(queryProductName, providerId, isPayment);
			pageSupport.setTotalCount(totalCount);
			
			providerList=providrService.getAllProvider();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		 model.addAttribute("queryProductName", queryProductName);
		 model.addAttribute("queryProviderId", providerId);
		 model.addAttribute("queryIsPayment",isPayment);
		 model.addAttribute("billList",billList);
		 model.addAttribute("providerList", providerList);
		 model.addAttribute("currentPageNo",pageNo);
		 model.addAttribute("totalCount",totalCount);
		 model.addAttribute("totalPageCount",pageSupport.getTotalPageCount());
		return "billlist";
	}
	//增加列表
	@RequestMapping(value="/addBill",method=RequestMethod.GET)
	public String addBill(@ModelAttribute("bill")Bill bill) {
		return "billadd";
	}
	//增加订单
	@RequestMapping(value="/billSave",method=RequestMethod.POST)
	public String billSave(Bill bill,HttpSession session) {
		 bill.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		 bill.setCreationDate(new Date());
		 boolean result=false;
		 try {
			result = billService.add(bill);
			if(result) {
				return "redirect:/bill/billList";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "billadd";
		}
		 return "billadd";
	}
	//跳转到修改页面并查出Bill信息
	@RequestMapping(value="/modifyBill/{id}",method=RequestMethod.GET)
	public String billModify(@PathVariable String id,Model model) {
		int billid=0;
		Bill bill=null;
		try {
			if(id!=null&&id!="") {
			     billid=Integer.parseInt(id);
				}
			bill = billService.getBillById(billid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("bill",bill);
		return "billmodify";
	}
	//跟新用户修改的内容
	@RequestMapping(value="/modifySave",method=RequestMethod.POST)
	public String modifySave(Bill bill,HttpSession session) {
		 bill.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		 bill.setModifyDate(new Date());
		 try {
			if(billService.modify(bill)) {
				 return "redirect:/bill/billList";
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return "billmodify";
	}
	@RequestMapping(value="/proList",method=RequestMethod.GET)
	@ResponseBody
	public String getProList() {
		List<Provider> proList=providrService.getAllProvider();
		return JSON.toJSONString(proList);
	}
	
	@RequestMapping(value="/billview/{id}",method=RequestMethod.GET)
	public String getBillView(@PathVariable String id,Model model) {
		Integer billid=Integer.parseInt(id);
		try {
			Bill bill=billService.getBillById(billid);
			model.addAttribute("bill",bill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  "billview";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	@ResponseBody
	public String delete(@RequestParam String billid) {
		HashMap<String,String> hashMap=new HashMap<String,String>();
		if(billid==null||billid.equals("")) {
			 hashMap.put("delResult","notexist");
			 return JSON.toJSONString(hashMap);
		}
		int id=Integer.parseInt(billid);
		try {
			if(billService.delete(id)) {
				 hashMap.put("delResult","true");
				 return JSON.toJSONString(hashMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		  hashMap.put("delResult","false");
		  return JSON.toJSONString(hashMap);
	}
}
