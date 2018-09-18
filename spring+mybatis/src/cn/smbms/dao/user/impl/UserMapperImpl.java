package cn.smbms.dao.user.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.User;

public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {
	//private SqlSessionTemplate sqlSession;

	@Override
	public List<User> getUserList(User user) {
		//使用SqlSessionDaoSuppport，必须继承SqlSessionDaoSupport类
		return this.getSqlSession().selectList("cn.smbms.dao.user.UserMapper.getUserList",user);
	}
	
	/*使用SqlSessionTemplate
	 * return sqlSession.selectList("cn.smbms.dao.user.UserMapper.getUserList",user);
	}
	public SqlSessionTemplate getSqlSession() {
	return sqlSession;
	}
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession=sqlSession;
	}*/
}
