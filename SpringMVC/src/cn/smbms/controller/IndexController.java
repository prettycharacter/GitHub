package cn.smbms.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.smbms.pojo.User;
@Controller
public class IndexController{
    private Logger logger=Logger.getLogger(IndexController.class);
	
    @RequestMapping(value="/index2",method=RequestMethod.GET)
    public String index2(@RequestParam(value="username",required=false)String username,Model model) {
    	logger.info("hello springmvc");
    	model.addAttribute("username",username);
    	User user=new User();
    	user.setUserName("许鹏飞");
    	model.addAttribute(user);
    	model.addAttribute("myname",user);
    	return "index";
    }
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index(@RequestParam(value="username",required=false)String username) {
		   logger.info("hello springmvc");
		   ModelAndView mV=new ModelAndView();
		   mV.addObject("username",username);
		   mV.setViewName("index");
		   return mV;
	}
	
	
	@RequestMapping(value="/welcome",method=RequestMethod.GET)
	public ModelAndView welcome(@RequestParam(value="username",required=false)String username) {
		 logger.info("============================================welcome:"+username);
		 return new ModelAndView("index");
	}
}
