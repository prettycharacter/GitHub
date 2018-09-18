package cn.smbms.service.user;

import java.util.List;

import cn.smbms.pojo.User;

public interface UserService {
	public List<User> findUserWithConditions(User user);
	
}
