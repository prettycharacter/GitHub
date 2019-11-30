package cn.smbms.service.user;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.User;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserMapper userMapper;
    //SSM登录功能
	@Override
	public User login(String userCode, String userPassword)throws Exception {
		User user = null;
		user = userMapper.getLoginUser(userCode);
		//匹配密码
		if(null != user){
			if(!user.getUserPassword().equals(userPassword))
				user = null;
		}
		return user;
	}
	//ssm分页查询用户
	@Override
	public List<User> getUserList(String queryUserName, Integer queryUserRole, Integer currentPageNo, Integer pageSize)throws Exception {
		System.out.println("UserName"+queryUserName);
		System.out.println("UserRole"+queryUserRole);
		System.out.println("currentPageNo"+currentPageNo);
		System.out.println("pageSize"+pageSize);
		 List<User> userList=null;
		try {
			userList = userMapper.getUserList(queryUserName, queryUserRole, (currentPageNo-1)*pageSize, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return userList;
	}
	//ssm获得用户数量
	@Override
	public int getUserCount(String queryUserName, int queryUserRole)throws Exception{
		int count=0;
		try {
			count=userMapper.getUserCount(queryUserName, queryUserRole);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return count;
	}
	//ssm增加用户
	@Override
	public boolean add(User user){
		boolean result=false;
		try {
			int row=userMapper.add(user);
			if(row>0) {
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//ssm判断用户编码是否已经存在
	@Override
	public User selectUserCodeExist(String userCode) {
		User user=null;
		try {
			user=userMapper.getLoginUser(userCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public boolean deleteUserById(Integer delId) {
		boolean result=false;
		try {
			User user=userMapper.getUserById(delId.toString());
			if(user.getIdPicPath()!=null&&user.getIdPicPath()!="") {
				File file=new File(user.getIdPicPath());
				if(file.exists()) {
					file.delete();
				}
			}
			if(user.getWorkPicPath()!=null&&user.getWorkPicPath()!="") {
				File file=new File(user.getWorkPicPath());
				if(file.exists()) {
					file.delete();
				}
			}
			int row=userMapper.deleteUserById(delId);
			if(row>0) {
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	//ssm根据id获得用户信息
	@Override
	public User getUserById(String id) {
		User user=null;
		try {
			user=userMapper.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public boolean modify(User user) {
		boolean result=false;
		int row=0;;
		try {
			row = userMapper.modify(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(row>0) {
			result=true;
		}
		return result;
	}
	@Override
	public boolean updatePwd(int id, String pwd) {
		boolean result=false;
		int row=0;
		try {
			row = userMapper.updatePwd(id, pwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(row>0) {
			result=true;
		}
		return result;
	}
}
