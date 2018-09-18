package cn.smbms.service.user.impl;

import java.util.List;

import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;

public class UserServiceImpl implements UserService{
	public UserMapper userMapper;
	@Override
	public  List<User> findUserWithConditions(User user) {
		try {
		return userMapper.getUserList(user);
		}catch(RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}
    public UserMapper getUserMapper() {
    	return userMapper;
    }
    public void setUserMapper(UserMapper userMapper) {
    	this.userMapper=userMapper;
    }
}

