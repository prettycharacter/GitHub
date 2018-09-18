package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;

class test1 {
	private Logger logger=Logger.getLogger(test1.class);
	@Test
	void test() {
		ApplicationContext ctx=new 
				    ClassPathXmlApplicationContext("applicationContext-mybatis.xml");
		UserService userService=(UserService)ctx.getBean("userService");
		//UserMapper userMapper=(UserMapper)ctx.getBean("userMapper");
		User userCondition=new User();
		userCondition.setUserName("赵");
		userCondition.setUserRole(3);
		List<User> userList=new ArrayList<User>();
		userList=userService.findUserWithConditions(userCondition);
		//userList=userMapper.getUserList(userCondition);
		for (User user : userList) {
			logger.debug("userCode="+user.getUserCode()+"userName="+user.getUserName()+
					      "userRoleName"+user.getUserRoleName());
			
		}
	}
	@Test
	void test2() {
		ApplicationContext ctx=new 
				ClassPathXmlApplicationContext("applicationContext-mybatis.xml");
		//UserService userService=(UserService)ctx.getBean("userService");
		ProviderMapper providerMapper=(ProviderMapper)ctx.getBean("providerMapper");
		/*User userCondition=new User();
		userCondition.setUserName("赵");
		userCondition.setUserRole(3);
		userList=userService.findUserWithConditions(userCondition);*/
		//userList=userMapper.getUserList(userCondition);
		Provider provider1=new Provider();
		provider1.setProName("北");
		List<Provider> list=new ArrayList<Provider>();
		list=providerMapper.getProviderList(provider1);
		for (Provider provider : list) {
			logger.info("proName="+provider.getProName()+"proCode="+provider.getProCode());
		}
	}

}
