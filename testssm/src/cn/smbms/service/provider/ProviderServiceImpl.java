package cn.smbms.service.provider;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Provider;
@Transactional
@Service
public class ProviderServiceImpl implements ProviderService {
	@Resource
	private ProviderMapper providerMapper;
	
	@Override
	public int getProCount(String proCode, String proName) {
		int count=0;
		try {
			count=providerMapper.getProCount(proCode, proName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean add(Provider provider) {
		boolean result=false;
		try {
		  int row=providerMapper.add(provider);
		  if(row>0) {
			  result=true;
		  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Provider> getProviderList(String proName, String proCode, int pageNo, int pageSize) {
		System.out.println("proName========================="+proName);
		System.out.println("proCode========================="+proCode);
		System.out.println("pageNo========================="+pageNo);
		System.out.println("pageSize========================="+pageSize);
		 List<Provider> proList=null;
		 try {
			proList=providerMapper.getProviderList(proName, proCode, (pageNo-1)*pageSize, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return proList;
	}

	@Override
	public boolean deleteProviderById(String delId) {
		boolean result=false;
		int row=0;
		try {
			row = providerMapper.deleteProviderById(delId);
			if(row>0) {
				result=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Provider getProviderById(String id) {
		Provider provider=null;
		try {
			provider=providerMapper.getProviderById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return provider;
	}

	@Override
	public boolean modify(Provider provider) {
		boolean result = false;
		int row=0;
		try {
			row=providerMapper.modify(provider);
			if(row>0) {
				result=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Provider> getAllProvider() {
		List<Provider> providerList=null;
		try {
			providerList=providerMapper.getAllProvider();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return providerList;
	}

}
