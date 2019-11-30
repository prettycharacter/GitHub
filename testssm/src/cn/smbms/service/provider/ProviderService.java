package cn.smbms.service.provider;

import java.util.List;

import cn.smbms.pojo.Provider;

public interface ProviderService {
	/**获得所有的供应商列表
	 * @return
	 */
	public List<Provider> getAllProvider();
	/**根据用户条件查询供应商数量
	 * @param proCode
	 * @param proName
	 * @return
	 */
	public int getProCount(String proCode,String proName);
	/**
	 * 增加供应商
	 * @param provider
	 * @return
	 */
	public boolean add(Provider provider);
		

	/**
	 * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
	 * @param proName
	 * @return
	 */
	public List<Provider> getProviderList(String proName,String proCode,int pageNo,int pageSize);
	
	/**
	 * 通过proId删除Provider
	 * @param delId
	 * @return
	 */
	public boolean deleteProviderById(String delId);
	
	
	/**
	 * 通过proId获取Provider
	 * @param id
	 * @return
	 */
	public Provider getProviderById(String id);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public boolean modify(Provider provider);
	
}
