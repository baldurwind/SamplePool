package sy.service.impl;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chamago.pcrm.common.utils.MemCached;

import sy.hbm.Syresource;
import sy.hbm.Syrole;
import sy.hbm.SyroleSyreource;
import sy.hbm.Syuser;
import sy.hbm.SyuserSyrole;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.Resource;
import sy.service.ResourceServiceI;
@Service("resourceService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ResourceServiceImpl extends BaseServiceImpl implements ResourceServiceI {

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@MemCached
	public List<Resource> offResourceList() {
		List<Resource> rl = new ArrayList<Resource>();
		List<Syresource> srl = super.getBaseDao().find("from Syresource t where t.onoff = '0'");
		if (srl != null && srl.size() > 0) {
			for (Syresource sr : srl) {
				Resource r = new Resource();
				BeanUtils.copyProperties(sr, r);
				rl.add(r);
			}
		}
		return rl;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Resource getResourceByRequestPath(String requestPath) {
		Syresource sr = (Syresource) super.getBaseDao().get("from Syresource t where t.url=?", requestPath);
		if (sr != null) {
			Resource r = new Resource();
			BeanUtils.copyProperties(sr, r);
			return r;
		} else {
			return null;
		}
	}

	//@Override
//	public boolean checkAuth(String userId, String requestPath) {
//		boolean b = false;
//		Syuser syuser = (Syuser) super.getBaseDao().get(Syuser.class, userId);
//		Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();// 用户和角色关系
//		for (SyuserSyrole syuserSyrole : syuserSyroles) {
//			Syrole syrole = syuserSyrole.getSyrole();
//			Set<SyroleSyreource> syroleSyreources = syrole.getSyroleSyreources();// 角色和资源关系
//			for (SyroleSyreource syroleSyreource : syroleSyreources) {
//				Syresource syresource = syroleSyreource.getSyresource();
//				if (requestPath.equals(syresource.getUrl())) {
//					return true;
//				}
//			}
//		}
//		return b;
//	}
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public boolean checkAuth(String userId, String requestPath) {
		boolean b = false;
		Syuser syuser = (Syuser) super.getBaseDao().get(Syuser.class, userId);
		Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();// 用户和角色关系
		for (SyuserSyrole syuserSyrole : syuserSyroles) {
			Syrole syrole = syuserSyrole.getSyrole();
			Set<SyroleSyreource> syroleSyreources = syrole.getSyroleSyreources();// 角色和资源关系
			for (SyroleSyreource syroleSyreource : syroleSyreources) {
				Syresource syresource = syroleSyreource.getSyresource();
				
				if (requestPath.equals(syresource.getUrl())) {
					return true;
				}
			}
		}
		return b;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<Syresource> checkAuth(String userId) {
		boolean b = false;
		Syuser syuser = (Syuser) super.getBaseDao().get(Syuser.class, userId);
		Set<Syresource> syresources = new HashSet<Syresource>();
		Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();// 用户和角色关系
		for (SyuserSyrole syuserSyrole : syuserSyroles) {
			Syrole syrole = syuserSyrole.getSyrole();
			
			Set<SyroleSyreource> syroleSyreources = syrole.getSyroleSyreources();// 角色和资源关系
			System.err.println(syrole.getName()+">>>>>"+syroleSyreources.size());
			for (SyroleSyreource syroleSyreource : syroleSyreources) {
				Syresource syresource = syroleSyreource.getSyresource();
				syresources.add(syresource);
			}
		}
		System.err.println(">>>>"+syresources.size());
		return syresources;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public DataGridJson datagrid(DataGrid dg, Resource resource) {
		DataGridJson j = new DataGridJson();
		String hql = " from Syresource t where 1=1 ";
		String totalHql = " select count(*) " + hql;
		j.setTotal(super.getBaseDao().count(totalHql));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<Syresource> ol = super.getBaseDao().find(hql);// 查询分页
		List<Resource> nl = new ArrayList<Resource>();
		if (ol != null && ol.size() > 0) {// 转换模型
			for (Syresource ot : ol) {
				Resource nt = new Resource();
				BeanUtils.copyProperties(ot, nt);
				if (ot.getSyresource() != null) {
					nt.set_parentId(ot.getSyresource().getId());
				}
				nl.add(nt);
			}
		}
		j.setRows(nl);// 设置返回的行
		return j;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(String id) throws Exception {
		Syresource syresource = (Syresource) super.getBaseDao().load(Syresource.class, id);
		Set<SyroleSyreource> syroleSyreources = syresource.getSyroleSyreources();
		for (SyroleSyreource syroleSyreource : syroleSyreources) {
			if(syroleSyreource.getSyrole()!=null){
				throw new Exception("资源以绑定角色，无法删除");
			}
		}
		
		for (SyroleSyreource syroleSyreource : syroleSyreources) {
			super.getBaseDao().delete(syroleSyreource);
		}
		super.getBaseDao().delete(syresource);
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Resource add(Resource res) {
		
		Syresource syr = (Syresource) super.getBaseDao().load(Syresource.class, res.get_parentId());
	    
		int lastNum = syr.getSyresources().size();
	   String parentId = res.get_parentId();
	   String nodeid = "";
			lastNum+=1;
				 nodeid = parentId.substring(0,parentId.length()) +"-"+lastNum;
			
			res.setId(nodeid);
		
		Syresource syresource = new Syresource();
		syresource.setSyresources(null);
		syresource.setSyresource(syr);
		
		BeanUtils.copyProperties(res, syresource);
		if(syr.getOnoff()!="1"){
			syr.setOnoff("1");
	    }
		super.getBaseDao().merge(syresource);  

		return res;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Resource edit(Resource res) {
	
		Syresource syresource = (Syresource)super.getBaseDao().load(Syresource.class, res.getId());
		BeanUtils.copyProperties(res, syresource);
		super.getBaseDao().update(syresource);
		return res;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Resource> getModuleResourceByRequestPath(String requestPath) {
		List<Resource> rList = new ArrayList<Resource>();
		Resource ss = getResourceByRequestPath(requestPath);
		List<Syresource> srList =  super.getBaseDao().find("from Syresource t where t.syresource=?", ss.getId());
		if(srList!=null&&srList.size()>0){
			for(Syresource sysR:srList){
				Resource r = new Resource();
				BeanUtils.copyProperties(sysR, r);
				rList.add(r);
			}
		}
		
		return rList;
		
	}


	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@MemCached
	public List<Syresource> getAccessResourceByRequestPath(String userid) {
		List<Syresource> rm = new ArrayList<Syresource>();
		
		Syuser syuser= (Syuser) super.getBaseDao().get(Syuser.class, userid);
		 //Syuser syuser =(Syuser) super.getBaseDao().find("From Syuser").get(0);
		 
		if(syuser!= null){
			Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();// 用户和角色关系
			for (SyuserSyrole syuserSyrole : syuserSyroles) {
				Syrole syrole = syuserSyrole.getSyrole();
				Set<SyroleSyreource> syroleSyreources = syrole.getSyroleSyreources();// 角色和资源关系
				for (SyroleSyreource syroleSyreource : syroleSyreources) {
					
					Syresource syresource = syroleSyreource.getSyresource();
					if(!rm.contains(syresource)){
						rm.add(syresource);
					}
				}
			}
		}
		return rm;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Syresource> findAll(){
		List<Syresource> resources = new ArrayList<Syresource>();
		
		resources = super.getBaseDao().find("From Syresource", new ArrayList());
		return resources;
	}
		
}
