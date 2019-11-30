package cn.smbms.dao.bill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Bill;

public interface BillMapper { 
	public Bill getBillById(@Param("id")Integer id)throws Exception;
	/**删除订单信息
	 * @param id 要删除的订单的id
	 * @return
	 */
	public int delete(@Param("id")Integer id)throws Exception;
	/**更新订单信息
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	public int modify(Bill bill)throws Exception;
	/**增加订单信息
	 * @param bill 
	 * @return
	 */
	public int add(Bill bill)throws Exception;
	/**根据用户的条件分页获取账单
	 * @param productName 商品名称
	 * @param providerId 供应商id
	 * @param isPayment 是否支付
	 * @param pageNo (页码数-1）*页容量
	 * @param pageSize 页容量
	 * @return  每页的 账单
	 */
	public List<Bill> getBillList(@Param("productName")String productName,
								  @Param("providerId")Integer providerId,
								  @Param("isPayment")Integer isPayment,
								  @Param("pageNo")Integer pageNo,
								  @Param("pageSize")Integer pageSize)throws Exception;
	/**根据用户的 条件获取订单的数量
	 * @param productName
	 * @param providerId
	 * @param isPayment
	 * @return
	 */
	public int getBillCount(@Param("productName")String productName,
			  				@Param("providerId")Integer providerId,
			  				@Param("isPayment")Integer isPayment)throws Exception;
	/**根据供应商id查询订单数量
	 * @param id 供应商Id
	 * @return
	 */
	public int getBillCountByPId(@Param("id")String id)throws Exception;
	
}
