package cn.smbms.service.bill;

import java.util.List;

import cn.smbms.pojo.Bill;

public interface BillService {
	/**根据id获得Bill对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Bill getBillById(Integer id)throws Exception;
	/**根据id删除订单信息
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id)throws Exception;
	/**跟新订单信息
	 * @param bill
	 * @return
	 */
	public boolean modify(Bill bill)throws Exception;
	/**添加订单信息
	 * @param bill
	 * @return
	 */
	public boolean add(Bill bill)throws Exception;
	/**根据用户的输入查询订单的数量
	 * @param productName
	 * @param providerId
	 * @param idPayment
	 * @return
	 */
	public int getBillCount(String productName,Integer providerId,Integer isPayment)throws Exception;
	/**根据用户的输入分页查询订单
	 * @param productName
	 * @param providerId
	 * @param isPayment
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Bill> getBillList(String productName,Integer providerId,Integer isPayment,Integer pageNo,Integer pageSize)throws Exception;
	/**根据供应商id查询账单
	 * @param id
	 * @return
	 */
	public int getBillCountByPId(String proderid)throws Exception;
}
