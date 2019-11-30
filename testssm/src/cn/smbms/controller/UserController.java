package cn.smbms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.utils.Constants;
import cn.smbms.utils.PageSupport;
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private Logger logger=Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	//@Resource
	//private RoleService roleService;
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		logger.info("进入login方法=========================================");
		return "login";
	}
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode,
						  @RequestParam String userPassword,
						  HttpSession session,
						  HttpServletRequest request) {
		logger.info("进入doLogin方法=======================================");
			User user=null;
			try {
				user = userService.login(userCode, userPassword);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(user==null) {
				request.setAttribute("error", "用户名或者密码不正确");
				return "login";
			}else {
				session.setAttribute(Constants.USER_SESSION, user);
				return "redirect:/user/sys/main";
			}
	}
	@RequestMapping(value="/sys/main",method=RequestMethod.GET)
	public String main(HttpSession session) {
		logger.info("进入main方法=======================================");
		if(session.getAttribute(Constants.USER_SESSION)==null) {
			return "redirect:/user/login";
		}
		return "frame";
		}
	@RequestMapping(value="/loginout",method=RequestMethod.GET)
	public String loginOut(HttpSession session) {
		logger.info("进入loginOut方法=======================================");
		session.invalidate();
		return "login";
	}
	
	//实现用户 的分页查询
	@RequestMapping("/userList")
	public String getUserList(Model model,
							  @RequestParam(value="queryname",required=false)String queryUserName,
			 				  @RequestParam(value="queryUserRole",required=false)String userRole,
			 				  @RequestParam(value="pageIndex",required=false)String pageIndex){
				logger.info("进入userList=======================================");
				logger.info("userList---》queryUserName======================="+queryUserName);
				logger.info("userList---》userRole======================="+userRole);
				logger.info("userList---》pageIndex======================="+pageIndex);
				//1.判断用户角色选择是否为null
				Integer queryUserRole=-1;
				Integer currentPageNo=1;
				List<Role> roleList=null;
				List<User> userList=null;
				PageSupport pageSupport=null;
				int totalCount=0;
				try {
				if(userRole!=null) {
				 queryUserRole=Integer.parseInt(userRole);
				}
				if(pageIndex!=null) {
				 currentPageNo=Integer.parseInt(pageIndex);
				
				}
				Integer pageSize=Constants.pageSize;
				//按照条件查找出所有的用户
			    userList=userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
				//计算出总页数
				pageSupport=new PageSupport();
				pageSupport.setCurrentPageNo(currentPageNo);
				pageSupport.setPageSize(pageSize);
			    //根据条件查出用户总数量
			     totalCount=userService.getUserCount(queryUserName, queryUserRole);
				//当用户总数量设置时，就已经计算出了新闻总数量。
				pageSupport.setTotalCount(totalCount);
				
				
					roleList = roleService.getRoleList();
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/user/syserror";
				}
				model.addAttribute("userList",userList);
				model.addAttribute("roleList", roleList);
				model.addAttribute("currentPageNo",currentPageNo);
				model.addAttribute("queryUserRole", queryUserRole);
				model.addAttribute("queryUserName",queryUserName);
				model.addAttribute("totalPageCount",pageSupport.getTotalPageCount());
				model.addAttribute("totalCount",totalCount);
		return "userlist";
	}
	//抛出异常时跳转的页面
	@RequestMapping("/syserror")
	public String sysError() {
		return "syserror";
	}
	@RequestMapping(value="/userAdd",method=RequestMethod.GET)
	public String userAdd(@ModelAttribute("user")User user) {
		return "useradd";
	}
	//增加用户的持久化操作，包括多文件上传，等
	@RequestMapping(value="/addSave",method=RequestMethod.POST)
	public String addSave(User user,
					     HttpSession session,
					     HttpServletRequest request,
					     @RequestParam(value="attachs",required=false) MultipartFile[] attachs) {
		logger.debug("=============================================="+user.getUserName());
		String idPicPath="";
		String workPicPath="";
		for(int i=0;i<attachs.length;i++) {
		if(!attachs[i].isEmpty()) {
			System.out.println("进入文件处理功能===================================");
			int fileSize=1000000;
			List<String> forMat=Arrays.asList("jpg","jpeg","png","pneg");
			String oldFileName=attachs[i].getOriginalFilename();
			String extension=FilenameUtils.getExtension(oldFileName);
			if(attachs[i].getSize()>fileSize) {
				request.setAttribute("uploadError","文件太大，只能上传小于1000000kb的文件");
				return "useradd";
			}else if(forMat.contains(extension)) {
				String path=request.getSession().getServletContext()
						    .getRealPath("statics"+File.separator+"uploadFile");
				String fileName=System.currentTimeMillis()+RandomUtils.nextInt(0,1000000)+"_idPicPath."+extension;
				File saveFile=new File(path,fileName);
				if(!saveFile.exists()) {
					saveFile.mkdirs();
				}
				try {
					attachs[i].transferTo(saveFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    request.setAttribute("uploadError", "上传失败");
				    return "useradd";
				}
				if(i==0) {
				idPicPath=path+File.separator+fileName;
				}else if(i==1) {
				 workPicPath=path+File.separator+fileName;
				}
			}else {
				request.setAttribute("uploadError","文件的 格式不正确，请上传，jpg,jpeg,png,pneg格式的图片");
				return "useradd";
			}
			
		}
	}
		user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setCreationDate(new Date());
		logger.debug("存入数据库中的值为============================================"+idPicPath);
		user.setIdPicPath(idPicPath);
		user.setWorkPicPath(workPicPath);
		
		System.out.println("================="+idPicPath);
		if(userService.add(user)) {
			return "redirect:/user/userList";
		}
		return "useradd";
	}
	
	//跳转到用户添加页面（使用Spring的form表单以及JSR 303 框架验证）
	 @RequestMapping(value="/add.html",method=RequestMethod.GET)
	public String addUser(@ModelAttribute("user")User user) {
		return  "user/useradd";
	}
	 
	 //添加用户的持久化操作（使用Spring的form标签以及JSR 303框架验证）
	@RequestMapping(value="/add.html",method=RequestMethod.POST)
	public String doAdd(@Valid User user,BindingResult bindingResult,HttpSession session) {
		if(bindingResult.hasErrors()) {
			logger.info("=====================add hasError");
			return "user/useradd";
		}
		user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setCreationDate(new Date());
		if(userService.add(user)) {
			return "redirect:/user/userList";
		}
		return "user/useradd";
	}
	//跳转到修改页面并查出要修改的用户的信息
	@RequestMapping(value="/usermodify",method=RequestMethod.GET)
	public String userModify(@RequestParam String uid,Model model) {
		logger.info("===========================进入userModify方法uid="+uid);
		User user=userService.getUserById(uid);
		 model.addAttribute(user);
		return "usermodify";
	}
	//修改的持久化方法
	@RequestMapping(value="/usermodifysave",method=RequestMethod.POST)
	public String userModifySave(User user,
			                     HttpSession session,
			                     HttpServletRequest request,
			                     @RequestParam(value="attachs",required=false)MultipartFile[] attachs) {
		logger.info("============================进入userModifysave方法");
		String idPicPath="";
		String workPicPath="";
		for(int i=0;i<attachs.length;i++) {
			if(!attachs[i].isEmpty()) {
				int fileSize=1000000;
				String oldFileName=attachs[i].getOriginalFilename();
				List<String> format=Arrays.asList("jpg","jpeg","png","pneg");
				String extension=FilenameUtils.getExtension(oldFileName);
				if(attachs[i].getSize()>fileSize) {
					request.setAttribute("uploadError","文件太大，只能上传小于1MB的文件");
					return "usermodify";
				}else if(format.contains(extension)){
				  String path=request.getSession().getServletContext()
						  .getRealPath("statics"+File.separator+"uploadFile");
				 String newFileName=System.currentTimeMillis()+RandomUtils.nextInt(0,1000000)+"_PicPath."+extension;
				 File saveFile=new File(path,newFileName);
				 if(!saveFile.exists()) {
					 saveFile.mkdirs();
				 }
				 try {
					attachs[i].transferTo(saveFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadError","上传失败");
					return "usermodify";
				}
				 if(i==0) {
					 idPicPath=path+File.separator+newFileName;
				 }else if(i==1) {
					 workPicPath=path+File.separator+newFileName;
				 }
			}else {
				request.setAttribute("uploadError", "文件的 格式不正确，请上传，jpg,jpeg,png,pneg格式的图片");
				return "usermodify";
			}
		  }
	     }
		user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setModifyDate(new Date());
		user.setIdPicPath(idPicPath);
		user.setWorkPicPath(workPicPath);
		logger.info("修改用户的id为"+user.getId());
		if(userService.modify(user)) {
			return "redirect:/user/userList";
		}
		return "usermodify";
	}
	
	//rest风格显示用户的详细信息
	@RequestMapping(value="/view/{id}",method=RequestMethod.GET)
	public String userView(@PathVariable String id,Model model) {
		logger.info("进入userView方法==============================");
		User user=userService.getUserById(id);
		model.addAttribute(user);
		return "userview";
	}
	//Ajax方式验证userCode是否已经存在
	@RequestMapping(value="/ucexits")
	@ResponseBody
	public Object userCodeExits(@RequestParam String userCode) {
		logger.info("进入userCodeExits方法====================================");
		HashMap<String,String> resultMap=new HashMap<String,String>();
		if(StringUtils.isNullOrEmpty(userCode)) {
			resultMap.put("userCode","exist");
			return JSONArray.toJSONString(resultMap);
		}
		User user=userService.selectUserCodeExist(userCode);
		if(null!=user) {
			resultMap.put("userCode","exist");
		}else {
			resultMap.put("userCode","noexist");
		}
		return JSONArray.toJSONString(resultMap);
	}
	//使用Ajax实现查看用户的详细信息
	@RequestMapping(value="viewhtml",method=RequestMethod.GET)
	@ResponseBody
	public User View(@RequestParam String id) {
		logger.info("进入View方法==========================");
		User user=null;
		try {
	    user=userService.getUserById(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	//使用Ajax实现用户修改密码以及旧密码的校验功能
	@RequestMapping(value="/pwdmodify",method=RequestMethod.GET)
	public String pwdModify() {
		logger.info("进入pwdmodify方法。===========================================");
		return "pwdmodify";
	}
	//使用Ajax验证输入的旧密码是否正确
	@RequestMapping(value="/getpwd",method=RequestMethod.GET)
	@ResponseBody
	public Object getpwd( @RequestParam String oldpassword,HttpSession session) {
		logger.info("进入getpwd方法============================================");
		logger.info("进入getpwd方法============================================"+oldpassword);
		HashMap<String,String> resultMap=new HashMap<String,String>();
		if(session.getAttribute(Constants.USER_SESSION)==null) {
			resultMap.put("result", "sessionerror");
			return resultMap;
		}
		if(oldpassword==null||"".equals(oldpassword)) {
			resultMap.put("result", "error");
			return resultMap;
		}
		String id=((User)session.getAttribute(Constants.USER_SESSION)).getId().toString();
		User user=userService.getUserById(id);
		if(user.getUserPassword().equals(oldpassword)) {
			resultMap.put("result", "true");
		}else {
			resultMap.put("result","false");
		}
		return resultMap;
	}
	//修改用户的密码，进行持久化操作
	@RequestMapping(value="/updatepwd",method=RequestMethod.POST)
	public String updatePwd(@RequestParam String newpassword,HttpSession session) {
		logger.info("进入updatepwd方法==============================================");
		int id=((User)session.getAttribute(Constants.USER_SESSION)).getId();
		boolean result=userService.updatePwd(id, newpassword);
		if(result) {
			return "redirect:/user/login";
		}else {
			return "pwdmodify";
		}
	}
     //Ajax异步获取角色列表
	@RequestMapping(value="/getuserrolelist",method=RequestMethod.GET)
	@ResponseBody
	public String getUserRoleList() {
		logger.info("进入getUserRoleList方法==================");
		String cjson="";
         List<Role> roleList = roleService.getRoleList();
         cjson=JSONArray.toJSONString(roleList);
         return cjson;
	}
	//Ajax异步删除用户功能
	@RequestMapping(value="/deluser",method=RequestMethod.GET)
	@ResponseBody
	public String delUser(@RequestParam String uid) {
		logger.info("进入delUser方法=============================");
		String delResult="";
		if(null==uid||"".equals(uid)) {
			delResult="{\"delResult\":\"notexist\"}";
			return delResult;
		}
	   if(userService.deleteUserById(Integer.parseInt(uid))) {
		   delResult="{\"delResult\":\"true\"}";
	   }else {
		   delResult="{\"delResult\":\"false\"}";
	   }
		return delResult;
	}
	
}