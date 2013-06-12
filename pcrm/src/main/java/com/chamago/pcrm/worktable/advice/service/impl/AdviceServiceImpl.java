/**
 * 
 */
package com.chamago.pcrm.worktable.advice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.worktable.advice.mapper.AdviceMapper;
import com.chamago.pcrm.worktable.advice.pojo.Advice;
import com.chamago.pcrm.worktable.advice.pojo.AdviceType;
import com.chamago.pcrm.worktable.advice.service.AdviceService;
import com.mysql.jdbc.StringUtils;

/**
 * @author gavin.peng	
 *
 */
@Service
public class AdviceServiceImpl implements AdviceService {

	@Autowired
	private AdviceMapper adviceMapper;
	
	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.advice.service.AdviceService#insertAdvice(com.chamago.pcrm.worktable.advice.pojo.Advice)
	 */
	public int insertAdvice(Advice advice) throws Exception{
		// TODO Auto-generated method stub
		if(null==advice){
			throw new Exception("新增实例advice为空!");
		}
		return adviceMapper.insertAdvice(advice);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.advice.service.AdviceService#findGoodAdvicesByCreatorAndStauts(java.lang.String, int)
	 */
	public Object[] findGoodAdvicesByCreatorAndStauts(String creator, int status) throws Exception{
		if(StringUtils.isNullOrEmpty(creator)){
			throw new Exception("查询被采纳建议的参数创建人creator不能为空");
		}
		Object[] obj = adviceMapper.findGoodAdvicesByCreatorAndStauts(creator, status);
		if(obj!=null&&obj[0]!=null&&obj[1]!=null){
			return obj;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.advice.service.AdviceService#findAdvicesByType(int)
	 */
	public List<Advice> findAdvicesByOrientedTypeAndTypeAndStauts(int orientedType,String type,int status,String sellerNick) throws Exception{
		if(StringUtils.isNullOrEmpty(sellerNick)){
			throw new Exception("要查找店铺建议sellerNick不能为空");
		}
		return adviceMapper.findAdvicesByOrientedTypeAndTypeAndStauts(orientedType, type, status,sellerNick);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.advice.service.AdviceService#findAdvicesByCreator(java.lang.String)
	 */
	public List<Advice> findAdvicesByCreator(String creator) throws Exception {
		if(StringUtils.isNullOrEmpty(creator)){
			throw new Exception("查询建议的参数创建人creator不能为空");
		}
		return adviceMapper.findAdvicesByCreator(creator);
	}

	public List<Advice> findAdvicesByStauts(int orientedType, int status) throws Exception{
		return adviceMapper.findAdvicesByStauts(orientedType, status);
	}

	public int deleteAdvicesByIds(String ids) throws Exception{
		if(StringUtils.isNullOrEmpty(ids)){
			throw new Exception("要删除建议的参数ids不能为空");
		}
		return adviceMapper.deleteAdvicesByIds(ids);
	}

	public int updateAdviceById(String id, String type) throws Exception{
		if(StringUtils.isNullOrEmpty(id)){
			throw new Exception("要更新建议类型的参数id不能为空");
		}
		return adviceMapper.updateAdviceById(id, type);
	}

	public int insertAdviceType(AdviceType adviceType) throws Exception {
		if(null == adviceType){
			throw new Exception("新增的建议分类adviceType不能为空!");
		}
		return adviceMapper.insertAdviceType(adviceType);
	}

	public List<Object[]> getAdviceTypeList(int orientedtype) {
		
		return adviceMapper.getAdviceTypeList(orientedtype);
	}

	public int batchUpdateAdviceByIds(String ids, String type) throws Exception {
		if(StringUtils.isNullOrEmpty(ids)){
			throw new Exception("要更新建议的参数ids不能为空");
		}
		String[] idsArr = ids.split(",");
		for(String id:idsArr){
			if(id.equals("null")){
				id = null;
			}
			this.updateAdviceById(id, type);
		}
		return 1;
	}

	public int updateAdviceStatusById(String id, int status,float score)throws Exception {
		if(StringUtils.isNullOrEmpty(id)){
			throw new Exception("要更新建议状态的参数id不能为空");
		}
		return adviceMapper.updateAdviceStatusById(id, status, score);
	}

	public int findAdvicesTypeByName(String name,String seller) throws Exception{
		if(StringUtils.isNullOrEmpty(name)){
			throw new Exception("根据名称查找建议类型的参数name不能为空");
		}
		if(StringUtils.isNullOrEmpty(seller)){
			throw new Exception("根据卖家查找建议类型的参数seller不能为空");
		}
		return adviceMapper.findAdvicesTypeByName(name,seller);
	}

	public List<Advice> findAdvicesByIds(String ids) throws Exception {
		if(StringUtils.isNullOrEmpty(ids)){
			throw new Exception("要查找建议的参数ids不能为空");
		}
		return adviceMapper.findAdvicesByIds(ids);
	}

	public List<Object[]> getAdviceTypeList(String sellerNick) throws Exception {
		if(StringUtils.isNullOrEmpty(sellerNick)){
			throw new Exception("要查找店铺建议类型sellerNick不能为空");
		}
		return adviceMapper.getAdviceTypeList(sellerNick);
	}

	@Override
	public int delAdviceType(String id) {
		return adviceMapper.delAdviceType(id);
	}

	@Override
	public int updateAdviceTypeNameById(String id, String name)
			throws Exception {
		if(id ==null||name ==null){
			throw new Exception("要更新店铺建议类型id或者name为空");
		}
		return adviceMapper.updateAdviceTypeNameById(id, name);
	}

}
