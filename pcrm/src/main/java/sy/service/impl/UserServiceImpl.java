package sy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import sy.hbm.Syuser;
import sy.hbm.SyuserSyrole;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.Role;
import sy.model.User;
import sy.service.UserServiceI;
import sy.util.Encrypt;

@Service("userService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserServiceImpl extends BaseServiceImpl implements UserServiceI {

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public DataGridJson datagrid(DataGrid dg, User user) {
		DataGridJson j = new DataGridJson();
		String hql = " from Syuser t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		if (user != null) {// 添加查询条件
			if (user.getName() != null && !user.getName().trim().equals("")) {
				hql += " and t.name like '%%" + user.getName().trim() + "%%' ";
			}
			if (user.getSellernick() != null
					&& !user.getSellernick().trim().equals("")) {
				hql += " and t.sellernick = ?";
				values.add(user.getSellernick());
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime>=? ";
				values.add(user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime<=? ";
				values.add(user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime>=? ";
				values.add(user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime<=? ";
				values.add(user.getModifydatetimeEnd());
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(super.getBaseDao().count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<Syuser> syusers = super.getBaseDao().find(hql, dg.getPage(),
				dg.getRows(), values);// 查询分页

		List<User> users = new ArrayList<User>();
		if (syusers != null && syusers.size() > 0) {// 转换模型
			for (Syuser syuser : syusers) {
				if (syuser.getSellernick() != "tp_pcrm") {
					User u = new User();
					BeanUtils.copyProperties(syuser, u);
					users.add(u);
				}
			}
		}
		j.setRows(users);// 设置返回的行
		return j;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<User> combobox(String q) {
		if (q == null) {
			q = "";
		}
		String hql = " from Syuser t where name like '%%" + q.trim() + "%%'";
		List<Syuser> l = super.getBaseDao().find(hql, 1, 10);
		List<User> ul = new ArrayList<User>();
		if (l != null && l.size() > 0) {
			for (Syuser syuser : l) {
				User u = new User();
				BeanUtils.copyProperties(syuser, u);
				ul.add(u);
			}
		}
		return ul;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User add(User user) {
		user.setId(UUID.randomUUID().toString());
		user.setCreatedatetime(new Date());
		Syuser syuser = new Syuser();
		BeanUtils.copyProperties(user, syuser);

		syuser.setPassword(Encrypt.e(user.getPassword()));

		super.getBaseDao().save(syuser);

		if (user.getRoleIds() != null) {
			for (String roleId : user.getRoleIds().split(",")) {
				if (roleId != null && !roleId.equals("")) {
					SyuserSyrole syuserSyrole = new SyuserSyrole();
					syuserSyrole.setId(UUID.randomUUID().toString());
					syuserSyrole.setSyuser(syuser);
					syuserSyrole.setSyrole((Syrole) super.getBaseDao().load(
							Syrole.class, roleId));
					super.getBaseDao().save(syuserSyrole);
				}
			}
		}

		return user;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public User login(User user) {
		Syuser syuser = (Syuser) super.getBaseDao().get(
				"from Syuser t where t.name=? and t.password=?",
				user.getName(), Encrypt.e(user.getPassword()));
		if (syuser != null) {
			BeanUtils.copyProperties(syuser, user);
			return user;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User edit(User user) {
		Syuser syuser = (Syuser) super.getBaseDao().get(Syuser.class,
				user.getId());
		System.out.println(syuser.getPassword());
		syuser.setModifydatetime(new Date());
		syuser.setName(user.getName());

		if (user.getPassword() != null && !user.getPassword().trim().equals("")) {
			syuser.setPassword(Encrypt.e(user.getPassword()));
		}

		Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();
		for (SyuserSyrole syuserSyrole : syuserSyroles) {
			super.getBaseDao().delete(syuserSyrole);
		}

		for (String roleId : user.getRoleIds().split(",")) {
			if (roleId != null && !roleId.equals("")) {
				SyuserSyrole syuserSyrole = new SyuserSyrole();
				syuserSyrole.setId(UUID.randomUUID().toString());
				syuserSyrole.setSyuser(syuser);
				syuserSyrole.setSyrole((Syrole) super.getBaseDao().load(
						Syrole.class, roleId));
				super.getBaseDao().save(syuserSyrole);
			}
		}

		return user;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			if (id != null && !id.equals("") && !id.equals("0")) {
				Syuser syuser = (Syuser) super.getBaseDao().load(Syuser.class,
						id);

				Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();
				for (SyuserSyrole syuserSyrole : syuserSyroles) {
					super.getBaseDao().delete(syuserSyrole);
				}

				super.getBaseDao().delete(syuser);
			}
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public DataGridJson roleDatagrid(DataGrid dg) {
		DataGridJson j = new DataGridJson();
		String hql = " from Syrole t where 1=1 ";
		String totalHql = " select count(*) " + hql;
		j.setTotal(super.getBaseDao().count(totalHql));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<Syrole> ol = super.getBaseDao().find(hql);// 查询分页
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
	public User getUser(String id) {
		Syuser syuser = (Syuser) super.getBaseDao().get(Syuser.class, id);
		User u = new User();
		BeanUtils.copyProperties(syuser, u);
		String roleNames = "";
		Set<String> resourceSet = new HashSet<String>();
		Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();
		for (SyuserSyrole syuserSyrole : syuserSyroles) {// 循环角色
			Syrole syrole = syuserSyrole.getSyrole();
			roleNames += "[" + syrole.getName() + "]&nbsp;&nbsp;";
			Set<SyroleSyreource> syroleSyreources = syrole
					.getSyroleSyreources();
			for (SyroleSyreource syroleSyreource : syroleSyreources) {// 循环资源
				Syresource syresource = syroleSyreource.getSyresource();
				resourceSet.add(syresource.getName());
			}
		}
		u.setRoleNames(roleNames);
		String resourceNames = "";
		for (String resourceName : resourceSet) {
			resourceNames += "[" + resourceName + "]&nbsp;&nbsp;";
		}
		u.setResourceNames(resourceNames);
		return u;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Syuser getUser(String username, String sellernick) {
		List<String> parm = new ArrayList<String>();
		parm.add(username);
		parm.add(sellernick);
		List list = super.getBaseDao().find(
				"From Syuser user where user.name=? and user.sellernick=?  ",
				parm);
		if (list.size() > 0) {
			return (Syuser) list.get(0);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean modifyPassword(User user) {
		boolean b = false;
		Syuser syuser = (Syuser) super.getBaseDao().load(Syuser.class,
				user.getId());
		if (syuser.getPassword().equals(Encrypt.e(user.getOldPassword()))) {
			syuser.setPassword(Encrypt.e(user.getPassword()));
			b = true;
		}
		return b;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<String> getUserRoles(String userId) {
		List<String> roleIds = new ArrayList<String>();
		Syuser syuser = (Syuser) super.getBaseDao().load(Syuser.class, userId);
		Set<SyuserSyrole> syuserSyroles = syuser.getSyuserSyroles();
		for (SyuserSyrole syuserSyrole : syuserSyroles) {
			if(syuserSyrole.getSyuser().getSellernick()!="tp_pcrm"){
			roleIds.add(syuserSyrole.getSyrole().getId());
			}
			}
		return roleIds;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modifyUsersPassword(String ids, String password) {
		for (String id : ids.split(",")) {
			if (id != null && !id.equals("")) {
				Syuser syuser = (Syuser) super.getBaseDao().load(Syuser.class,
						id);
				syuser.setPassword(Encrypt.e(password));
			}
		}
	}

}
