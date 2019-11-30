package cn.smbms.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Provider;

public interface ProviderMapper {
	/**获得供应商列表
	 * @return
	 */
	public List<Provider> getAllProvider()throws Exception;
	/**根据用户输入的条件查询新闻数量
	 * @param procode
	 * @return
	 */
	public int getProCount(@Param("proCode")String proCode,@Param("proName")String proName)throws Exception;
	/**
	 * 增加供应商
	 * @param connection
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public int add(Provider provider)throws Exception;


	/**
	 * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
	 * @param proName
	 * @return
	 * @throws Exception
	 */
	public List<Provider> getProviderList(@Param("proName")String proName,@Param("proCode")String proCode,@Param("pageNo")int pageNo,@Param("pageSize")int pageSize)throws Exception;
	
	/**
	 * 通过proId删除Provider
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public int deleteProviderById(@Param("id")String delId)throws Exception; 
	
	
	/**
	 * 通过proId获取Provider
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Provider getProviderById(@Param("id")String id)throws Exception; 
	
	/**
	 * 修改用户信息
	 * @param connection
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int modify(Provider provider)throws Exception;
	
	
}