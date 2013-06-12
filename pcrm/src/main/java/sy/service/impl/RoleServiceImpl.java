package sy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sy.hbm.Syresource;
import sy.hbm.Syrole;
import sy.hbm.SyroleSyreource;
import sy.hbm.SyuserSyrole;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.Role;
import sy.model.ZtreeNode;
import sy.service.RoleServiceI;

@Service("roleService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class RoleServiceImpl extends BaseServiceImpl implements RoleServiceI {

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public DataGridJson datagrid(DataGrid dg, Role role) {
		DataGridJson j = new DataGridJson();
		String hql = " from Syrole t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		if (role.getSellernick() != null) {
			hql += " and sellernick  like '%%" + role.getSellernick() + "%%' ";
		//	values.add(role.getSellernick());
		}
		if (role != null) {// 添加查询条件
			if (role.getName() != null) {
				hql += " and name like '%%" + role.getName() + "%%' ";
			}
			if (role.getComments() != null) {
				hql += " and name like '%%" + role.getComments() + "%%' ";
			}
			
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(super.getBaseDao().count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<Syrole> ol = super.getBaseDao().find(hql, dg.getPage(),
				dg.getRows(), values);// 查询分页
		List<Role> nl = new ArrayList<Role>();
		if (ol != null && ol.size() > 0) {// 转换模型
			for (Syrole o : ol) {
				if (o.getSellernick() != "tp_pcrm") {
					Role n = new Role();
					BeanUtils.copyProperties(o, n);
					nl.add(n);
				}
			}
		}
		j.setRows(nl);// 设置返回的行
		return j;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ZtreeNode> resourceTree() {
		List<ZtreeNode> tree = new ArrayList<ZtreeNode>();
		List<Syresource> syresources = super.getBaseDao().find(
				"from Syresource t");
		for (Syresource r : syresources) {
			ZtreeNode node = new ZtreeNode();
			node.setId(r.getId());
			node.setName(r.getName());
			node.setOpen(true);
			if (r.getSyresource() != null) {
				node.setPid(r.getSyresource().getId());
			}
			tree.add(node);
		}
		return tree;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<String> getRoleResources(String roleId) {
		List<String> ids = new ArrayList<String>();
		Syrole syrole = (Syrole) super.getBaseDao().get(Syrole.class, roleId);
		Set<SyroleSyreource> syroleSyreources = syrole.getSyroleSyreources();
		for (SyroleSyreource syroleSyreource : syroleSyreources) {
			ids.add(syroleSyreource.getSyresource().getId());
		}
		return ids;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<SyroleSyreource> findSyroleSyreourceByRoleId(String roleId) {
		return this.getBaseDao().find("FROM SyroleSyreource WHERE syrole.id=?",
				roleId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			if (id != null && !id.equals("") && !id.equals("0")) {
				Syrole syrole = (Syrole) super.getBaseDao().load(Syrole.class,
						id);

				Set<SyroleSyreource> syroleSyreources = syrole
						.getSyroleSyreources();
				for (SyroleSyreource syroleSyreource : syroleSyreources) {
					super.getBaseDao().delete(syroleSyreource);
				}
				Set<SyuserSyrole> syuserSyroles = syrole.getSyuserSyroles();
				for (SyuserSyrole syuserSyrole : syuserSyroles) {
					super.getBaseDao().delete(syuserSyrole);
				}

				super.getBaseDao().delete(syrole);
			}
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Role edit(Role role) {
		Syrole syrole = (Syrole) super.getBaseDao().get(Syrole.class,
				role.getId());
		syrole.setName(role.getName());
		syrole.setComments(role.getComments());

		Set<SyroleSyreource> syroleSyreources = syrole.getSyroleSyreources();
		for (SyroleSyreource syroleSyreource : syroleSyreources) {
			super.getBaseDao().delete(syroleSyreource);
		}

		// syrole.setSyroleSyreources(null);

		for (String resourceId : role.getResourceIds().split(",")) {
			if (resourceId != null && !resourceId.equals("")) {
				SyroleSyreource syroleSyreource = new SyroleSyreource();
				syroleSyreource.setId(UUID.randomUUID().toString());
				syroleSyreource.setSyresource((Syresource) super.getBaseDao()
						.load(Syresource.class, resourceId));
				syroleSyreource.setSyrole(syrole);
				super.getBaseDao().save(syroleSyreource);
			}
		}

		Role r = new Role();
		BeanUtils.copyProperties(syrole, r);
		return r;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Role add(Role role) {
		role.setId(UUID.randomUUID().toString());
		Syrole syrole = new Syrole();
		BeanUtils.copyProperties(role, syrole);
		super.getBaseDao().save(syrole);

		for (String resourceId : role.getResourceIds().split(",")) {
			if (resourceId != null && !resourceId.equals("")) {
				SyroleSyreource syroleSyreource = new SyroleSyreource();
				syroleSyreource.setId(UUID.randomUUID().toString());
				syroleSyreource.setSyresource((Syresource) super.getBaseDao()
						.load(Syresource.class, resourceId));
				syroleSyreource.setSyrole(syrole);
				super.getBaseDao().save(syroleSyreource);
			}
		}

		return role;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Syrole> findAll() {
		List<Syrole> roles = new ArrayList<Syrole>();

		roles = super.getBaseDao().find("From Syrole", new ArrayList());
		return roles;
	}

}
