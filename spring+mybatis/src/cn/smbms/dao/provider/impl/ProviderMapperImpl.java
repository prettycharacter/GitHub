package cn.smbms.dao.provider.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Provider;

public class ProviderMapperImpl implements ProviderMapper {
	private SqlSessionTemplate sqlSession;
	@Override
	public List<Provider> getProviderList(Provider provider) {
		return sqlSession.selectList("cn.smbms.dao.provider.ProviderMapper.getProviderList",provider);
	}
    public SqlSessionTemplate getSqlSession() {
    	return sqlSession;
    }
    public void setSqlSession(SqlSessionTemplate sqlSession) {
    	this.sqlSession=sqlSession;
    }
}
