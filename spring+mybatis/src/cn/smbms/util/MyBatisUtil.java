package cn.smbms.util;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	private  static SqlSessionFactory factory;
	private static  String config="mybatis-config.xml";
	static {
		try {
			InputStream is=Resources.getResourceAsStream(config);
			factory = new SqlSessionFactoryBuilder().build(is);
			//is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SqlSession getSqlSession() {
		return factory.openSession(false);
	}
	public static void closeSqlSession(SqlSession sqlSession) {
		if(sqlSession!=null) {
           sqlSession.close();
		}
	}
}
