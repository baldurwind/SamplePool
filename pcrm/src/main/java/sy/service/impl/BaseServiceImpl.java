package sy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sy.dao.BaseDaoI;
import sy.service.BaseServiceI;

@Service("baseService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BaseServiceImpl implements BaseServiceI {

	private BaseDaoI baseDao;

	public BaseDaoI getBaseDao() {
		return baseDao;
	}

	@Autowired
	public void setBaseDao(BaseDaoI baseDao) {
		this.baseDao = baseDao;
	}

}
