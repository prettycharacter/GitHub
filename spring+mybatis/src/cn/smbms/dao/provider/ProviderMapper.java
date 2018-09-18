package cn.smbms.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Provider;

public interface ProviderMapper {
	/**根据条件查询供应商列表
	 * @return
	 */
	public List<Provider> getProviderList(Provider provider);
}
