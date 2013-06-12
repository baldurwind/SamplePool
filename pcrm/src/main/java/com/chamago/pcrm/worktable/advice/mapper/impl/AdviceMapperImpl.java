/**
 * 
 */
package com.chamago.pcrm.worktable.advice.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import com.chamago.pcrm.worktable.advice.mapper.AdviceMapper;
import com.chamago.pcrm.worktable.advice.pojo.Advice;
import com.chamago.pcrm.worktable.advice.pojo.AdviceType;

/**
 * 建议实现类
 * @author gavin.peng
 *
 */
@SuppressWarnings("unchecked")
public class AdviceMapperImpl extends SqlSessionDaoSupport implements
		AdviceMapper {

	private static String ALL_TYPE = "all";
	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.advice.mapper.AdviceMapper#insertAdvice(com.chamago.pcrm.worktable.advice.pojo.Advice)
	 */
	public int insertAdvice(Advice advice) {
		Assert.notNull(advice);
		return getSqlSession().insert("AdviceMapper.insertAdvice", advice);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.advice.mapper.AdviceMapper#findGoodAdvicesByCreatorAndStauts(java.lang.String, int)
	 */
	public Object[] findGoodAdvicesByCreatorAndStauts(String creator, int status) {
		Assert.notNull(creator);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("creator", creator);
		map.put("status", status);
		List<Object[]> objList = getSqlSession().selectList("AdviceMapper.findGoodAdvicesCountByCreatorAndStatus", map);
		if(objList!=null&&objList.size()>0){
			return objList.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.advice.mapper.AdviceMapper#findAdvicesByType(int)
	 */
	public List<Advice> findAdvicesByOrientedTypeAndTypeAndStauts(int orientedType,String type,int status,String sellerNick) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sellerNick", sellerNick);
		map.put("orientedType", orientedType);
		if(!ALL_TYPE.equals(type)){
			map.put("type", type);
		}
		if(status>-1){
			map.put("status", status);
		}
		return getSqlSession().selectList("AdviceMapper.findAdvicesByOrientedAndTypeAndStatus", map);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.advice.mapper.AdviceMapper#findAdvicesByCreator(java.lang.String)
	 */
	public List<Advice> findAdvicesByCreator(String creator) {
		Assert.notNull(creator);
		return getSqlSession().selectList("AdviceMapper.findAdvicesByCreator", creator);
	}

	public List<Advice> findAdvicesByStauts(int orientedType,int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orientedType", orientedType);
		map.put("status", status);
		return getSqlSession().selectList("AdviceMapper.findAdvicesByOrientedAndStatus", map);
	}

	public int deleteAdvicesByIds(String ids) {
		Assert.notNull(ids);
		List<String> list = new ArrayList<String>();
		String[] idsArr = ids.split(",");
		for(String id:idsArr){
			list.add(id);
		}
		return getSqlSession().delete("AdviceMapper.deleteAdvicesByIds", list);
		 
	}

	public int updateAdviceById(String id,String type) {
		Assert.notNull(id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", type);
		map.put("id", id);
		return getSqlSession().update("AdviceMapper.updateAdviceById", map);
	}

	public int insertAdviceType(AdviceType adviceType) {
		Assert.notNull(adviceType);
		return getSqlSession().insert("AdviceMapper.insertAdviceType", adviceType);
	}

	public List<Object[]> getAdviceTypeList(int orientedtype) {
		
		return getSqlSession().selectList("AdviceMapper.findAdvicesTypeList",orientedtype);
	}

	
	public int updateAdviceStatusById(String id, int status,float score) {
		Assert.notNull(id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("id", id);
		map.put("score", score);
		return getSqlSession().update("AdviceMapper.updateAdviceStatusById", map);
	}

	public int findAdvicesTypeByName(String name,String seller) {
		Assert.notNull(name);
		Assert.notNull(seller);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("seller", seller);
		Object obj = getSqlSession().selectOne("AdviceMapper.findAdviceTypeByName", map);
		if(obj==null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}

	public List<Advice> findAdvicesByIds(String ids) {
		Assert.notNull(ids);
		List<String> list = new ArrayList<String>();
		String[] idsArr = ids.split(",");
		for(String id:idsArr){
			list.add(id);
		}
		return getSqlSession().selectList("AdviceMapper.findAdvicesByIds", list);
	}

	public List<Object[]> getAdviceTypeList(String sellerNick) {
		// TODO Auto-generated method stub
		Assert.notNull(sellerNick);
		return getSqlSession().selectList("AdviceMapper.findAdvicesTypeListBySeller", sellerNick);
	}

	@Override
	public int delAdviceType(String id) {
		Assert.notNull(id);
		return getSqlSession().delete("AdviceMapper.deleteAdviceTypeById", id);
	}

	@Override
	public int updateAdviceTypeNameById(String id, String name) {
		
		Assert.notNull(id);
		Assert.notNull(name);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("id", id);
		return getSqlSession().update("AdviceMapper.updateAdviceAdviceTypeById", map);
	}

}
