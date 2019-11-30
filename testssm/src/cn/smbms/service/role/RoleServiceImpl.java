package cn.smbms.service.role;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smbms.dao.role.RoleMapper;
import cn.smbms.pojo.Role;
@Transactional
@Service
public class RoleServiceImpl implements RoleService{
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		List<Role> roleList = null;
		try {
			roleList = roleMapper.getRoleList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roleList;
	}
	
}
