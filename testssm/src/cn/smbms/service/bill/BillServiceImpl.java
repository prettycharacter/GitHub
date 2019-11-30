package cn.smbms.service.bill;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.pojo.Bill;

@Transactional
@Service
public class BillServiceImpl implements BillService {
    @Resource
	private BillMapper billMapper;
    //根据供应商id获取账单
	@Override
	public int getBillCountByPId(String providerid)throws Exception {
		int count = 0;
		try {
			count=billMapper.getBillCountByPId(providerid);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}
    //根据id删除订单信息
	@Override
	public boolean delete(Integer id)throws Exception {
		boolean result=false;
		try {
			int row=billMapper.delete(id);
			if(row>0) {
				result=true;
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
   //根据id修改订单信息
	@Override
	public boolean modify(Bill bill)throws Exception {
		boolean result=false;
		try {
			int row = billMapper.modify(bill);
			if(row>0) {
				result=true;
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
   //增加订单信息
	@Override
	public boolean add(Bill bill)throws Exception {
		boolean result=false;
		try {
			int row = billMapper.add(bill);
			if(row>0) {
				result=true;
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
    //根据用户的输入获得账单的数量
	@Override
	public int getBillCount(String productName, Integer providerId, Integer isPayment)throws Exception {
            int count=0;
           try {
			count=billMapper.getBillCount(productName, providerId, isPayment);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}
    //根据用户的输入分页获取账单
	@Override
	public List<Bill> getBillList(String productName, Integer providerId, Integer isPayment, Integer pageNo,
			Integer pageSize)throws Exception {
		System.out.println("productName============================="+productName);
		System.out.println("providerId============================="+providerId);
		System.out.println("isPayment============================="+isPayment);
		System.out.println("pageNo============================="+pageNo);
		System.out.println("pageSize============================="+pageSize);
	    List<Bill> billList=null;
		try {
			billList=billMapper.getBillList(productName, providerId, isPayment, (pageNo-1)*pageSize, pageSize);
		} catch (Exception e) {
			throw e;
		}
		return billList;
	}
	@Override
	public Bill getBillById(Integer id) throws Exception {
		Bill bill=null;
		try {
		bill=billMapper.getBillById(id);
		}catch(Exception e) {
			throw e;
		}
		return bill;
	}

}
