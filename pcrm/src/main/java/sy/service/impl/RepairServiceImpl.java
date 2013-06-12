package sy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.Utils;

import sy.hbm.Syresource;
import sy.hbm.Syrole;
import sy.hbm.SyroleSyreource;
import sy.hbm.Syuser;
import sy.hbm.SyuserSyrole;
import sy.service.RepairServiceI;
import sy.util.Encrypt;

@Service("repairService")
@Transactional
public class RepairServiceImpl extends BaseServiceImpl implements RepairServiceI {

	
	public  static void main(String ags[]){
		ApplicationContext ac=Utils.getClassPathXMlApplication();
		RepairServiceImpl rs=(RepairServiceImpl)ac.getBean(RepairServiceImpl.class);
		rs.repair("良无限清洁收纳");
	}
	
	@Override
	public void repair(String  seller) {

		Syuser admin = new Syuser();// 修复超级管理员用户
		admin.setId( ObjectId.get().toString());
		admin.setName(seller);
		admin.setPassword(Encrypt.md5("admin"));
		admin.setSellernick(seller);
		admin.setCreatedatetime(new Date());
		admin.setModifydatetime(new Date());
		super.getBaseDao().merge(admin);

		Syrole adminRole = new Syrole();// 修复超级管理员角色
		adminRole.setId(ObjectId.get().toString());
		adminRole.setName("ROLE_ADMIN");
		adminRole.setSellernick(seller);
		adminRole.setComments("拥有系统所有权限");
		super.getBaseDao().merge(adminRole);
		// 修复超级管理员和超级管理员角色的关系
		SyuserSyrole ur = new SyuserSyrole();
		ur.setId(ObjectId.get().toString());
		ur.setSyuser(admin);
		ur.setSyrole(adminRole);
		super.getBaseDao().merge(ur);
		//repairResource();// 修复所有系统资源

		// 修复超级管理员角色和所有权限的关系
		List<Syresource> resources = super.getBaseDao().find("from Syresource where id not in ('0-3-3','0-3-2','0-3-4') ");
		for (Syresource resource : resources) {// 维护超级管理员角色和资源的关系
			SyroleSyreource rr = new SyroleSyreource();
			rr.setId(adminRole.getId() + resource.getId());
			rr.setSyrole(adminRole);
			rr.setSyresource(resource);
			
			System.out.println(adminRole);
			super.getBaseDao().merge(rr);
		}
	}

	private void repairResource() {
		Syresource node = c0();// 根资源
		c1(node, 1);// 首页功能
		c2(node, 2);// 用户管理
		c3(node, 3);// 资源管理
		c4(node, 4);// 角色管理
		c5(node, 5);// 首页管理
	}

	private void c5(Syresource node, int k) {
		int i = 0;
		Syresource t1 = new Syresource();
		t1.setId(node.getId() + "-" + k + "-" + i++);
		t1.setName("首页管理");
		t1.setOnoff("1");
		t1.setComments("首页管理页面");
		t1.setUrl("/homeController.do?home");
		t1.setSyresource(node);
		super.getBaseDao().merge(t1);
	}

	private void c4(Syresource node, int k) {
		int i = 0;
		Syresource t1 = new Syresource();
		t1.setId(node.getId() + "-" + k + "-" + i++);
		t1.setName("角色管理");
		t1.setOnoff("1");
		t1.setComments("角色管理页面");
		t1.setUrl("/roleController.do?role");
		t1.setSyresource(node);
		super.getBaseDao().merge(t1);

		Syresource t2 = new Syresource();
		t2.setId(node.getId() + "-" + k + "-" + i++);
		t2.setName("角色表格");
		t2.setOnoff("1");
		t2.setComments("角色管理datagrid");
		t2.setUrl("/roleController.do?datagrid");
		t2.setSyresource(t1);
		super.getBaseDao().merge(t2);

		Syresource t3 = new Syresource();
		t3.setId(node.getId() + "-" + k + "-" + i++);
		t3.setName("资源树");
		t3.setOnoff("0");
		t3.setComments("资源树");
		t3.setUrl("/roleController.do?resourceTree");
		t3.setSyresource(t1);
		super.getBaseDao().merge(t3);

		Syresource t4 = new Syresource();
		t4.setId(node.getId() + "-" + k + "-" + i++);
		t4.setName("角色资源预览");
		t4.setOnoff("1");
		t4.setComments("角色资源预览");
		t4.setUrl("/roleController.do?getRoleResources");
		t4.setSyresource(t1);
		super.getBaseDao().merge(t4);

		Syresource t5 = new Syresource();
		t5.setId(node.getId() + "-" + k + "-" + i++);
		t5.setName("添加角色");
		t5.setOnoff("1");
		t5.setComments("添加角色");
		t5.setUrl("/roleController.do?add");
		t5.setSyresource(t1);
		super.getBaseDao().merge(t5);

		Syresource t6 = new Syresource();
		t6.setId(node.getId() + "-" + k + "-" + i++);
		t6.setName("编辑角色");
		t6.setOnoff("1");
		t6.setComments("编辑角色");
		t6.setUrl("/roleController.do?edit");
		t6.setSyresource(t1);
		super.getBaseDao().merge(t6);

		Syresource t7 = new Syresource();
		t7.setId(node.getId() + "-" + k + "-" + i++);
		t7.setName("删除角色");
		t7.setOnoff("1");
		t7.setComments("删除角色");
		t7.setUrl("/roleController.do?delete");
		t7.setSyresource(t1);
		super.getBaseDao().merge(t7);

	}

	private Syresource c0() {// 根
		Syresource t = new Syresource();
		t.setId("0");
		t.setName("sypro");
		t.setOnoff("0");
		t.setComments("sypro系统");
		t.setUrl("/sypro");
		super.getBaseDao().merge(t);

		return t;
	}

	private void c1(Syresource node, int k) {
		int i = 0;
		Syresource t0 = new Syresource();
		t0.setId(node.getId() + "-" + k + "-" + i++);
		t0.setName("公共资源");
		t0.setOnoff("0");
		t0.setComments("所有用户都可以访问的资源");
		t0.setUrl("/公共资源");
		t0.setSyresource(node);
		super.getBaseDao().merge(t0);

		Syresource t1 = new Syresource();
		t1.setId(node.getId() + "-" + k + "-" + i++);
		t1.setName("补全方式登录");
		t1.setOnoff("0");
		t1.setComments("用户登录页面，下拉列表框");
		t1.setUrl("/userController.do?loginCombobox");
		t1.setSyresource(t0);
		super.getBaseDao().merge(t1);

		Syresource t2 = new Syresource();
		t2.setId(node.getId() + "-" + k + "-" + i++);
		t2.setName("表格方式登录");
		t2.setOnoff("0");
		t2.setComments("用户登录页面，datagrid");
		t2.setUrl("/userController.do?loginDatagrid");
		t2.setSyresource(t0);
		super.getBaseDao().merge(t2);

		Syresource t3 = new Syresource();
		t3.setId(node.getId() + "-" + k + "-" + i++);
		t3.setName("用户登录");
		t3.setOnoff("0");
		t3.setComments("用户登录");
		t3.setUrl("/userController.do?login");
		t3.setSyresource(t0);
		super.getBaseDao().merge(t3);

		Syresource t4 = new Syresource();
		t4.setId(node.getId() + "-" + k + "-" + i++);
		t4.setName("注销登录");
		t4.setOnoff("0");
		t4.setComments("退出系统");
		t4.setUrl("/userController.do?logout");
		t4.setSyresource(t0);
		super.getBaseDao().merge(t4);

		Syresource t5 = new Syresource();
		t5.setId(node.getId() + "-" + k + "-" + i++);
		t5.setName("用户注册");
		t5.setOnoff("0");
		t5.setComments("注册");
		t5.setUrl("/userController.do?reg");
		t5.setSyresource(t0);
		super.getBaseDao().merge(t5);

		Syresource t6 = new Syresource();
		t6.setId(node.getId() + "-" + k + "-" + i++);
		t6.setName("修复数据");
		t6.setOnoff("0");
		t6.setComments("修复数据库");
		t6.setUrl("/repairController.do?repair");
		t6.setSyresource(t0);
		super.getBaseDao().merge(t6);

		Syresource t7 = new Syresource();
		t7.setId(node.getId() + "-" + k + "-" + i++);
		t7.setName("个人信息");
		t7.setOnoff("0");
		t7.setComments("个人信息");
		t7.setUrl("/userController.do?info");
		t7.setSyresource(t0);
		super.getBaseDao().merge(t7);

		Syresource t8 = new Syresource();
		t8.setId(node.getId() + "-" + k + "-" + i++);
		t8.setName("修改个人密码");
		t8.setOnoff("0");
		t8.setComments("修改个人密码");
		t8.setUrl("/userController.do?modifyPassword");
		t8.setSyresource(t0);
		super.getBaseDao().merge(t8);

		Syresource t9 = new Syresource();
		t9.setId(node.getId() + "-" + k + "-" + i++);
		t9.setName("查看个人信息");
		t9.setOnoff("0");
		t9.setComments("查看个人信息");
		t9.setUrl("/userController.do?getInfo");
		t9.setSyresource(t0);
		super.getBaseDao().merge(t9);

		Syresource t10 = new Syresource();
		t10.setId(node.getId() + "-" + k + "-" + i++);
		t10.setName("首页");
		t10.setOnoff("0");
		t10.setComments("显示首页信息");
		t10.setUrl("/homeController.do?portal");
		t10.setSyresource(t0);
		super.getBaseDao().merge(t10);

	}

	private void c2(Syresource node, int k) {// 用户管理
		int i = 0;
		Syresource t1 = new Syresource();
		t1.setId(node.getId() + "-" + k + "-" + i++);
		t1.setName("用户管理");
		t1.setOnoff("1");
		t1.setComments("用户管理");
		t1.setUrl("/userController.do?user");
		t1.setSyresource(node);
		super.getBaseDao().merge(t1);

		Syresource t2 = new Syresource();
		t2.setId(node.getId() + "-" + k + "-" + i++);
		t2.setName("用户表格");
		t2.setOnoff("1");
		t2.setComments("用户datagrid");
		t2.setUrl("/userController.do?datagrid");
		t2.setSyresource(t1);
		super.getBaseDao().merge(t2);

		Syresource t3 = new Syresource();
		t3.setId(node.getId() + "-" + k + "-" + i++);
		t3.setName("添加用户");
		t3.setOnoff("1");
		t3.setComments("添加用户");
		t3.setUrl("/userController.do?add");
		t3.setSyresource(t1);
		super.getBaseDao().merge(t3);

		Syresource t4 = new Syresource();
		t4.setId(node.getId() + "-" + k + "-" + i++);
		t4.setName("编辑用户");
		t4.setOnoff("1");
		t4.setComments("编辑用户");
		t4.setUrl("/userController.do?edit");
		t4.setSyresource(t1);
		super.getBaseDao().merge(t4);

		Syresource t5 = new Syresource();
		t5.setId(node.getId() + "-" + k + "-" + i++);
		t5.setName("删除用户");
		t5.setOnoff("1");
		t5.setComments("删除用户");
		t5.setUrl("/userController.do?delete");
		t5.setSyresource(t1);
		super.getBaseDao().merge(t5);

		Syresource t6 = new Syresource();
		t6.setId(node.getId() + "-" + k + "-" + i++);
		t6.setName("用户角色表格");
		t6.setOnoff("0");
		t6.setComments("用户角色表格");
		t6.setUrl("/userController.do?roleDatagrid");
		t6.setSyresource(t1);
		super.getBaseDao().merge(t6);

		Syresource t7 = new Syresource();
		t7.setId(node.getId() + "-" + k + "-" + i++);
		t7.setName("用户角色预览");
		t7.setOnoff("1");
		t7.setComments("用户角色预览");
		t7.setUrl("/userController.do?getUserRoles");
		t7.setSyresource(t1);
		super.getBaseDao().merge(t7);

		Syresource t8 = new Syresource();
		t8.setId(node.getId() + "-" + k + "-" + i++);
		t8.setName("修改用户密码");
		t8.setOnoff("1");
		t8.setComments("修改用户密码");
		t8.setUrl("/userController.do?modifyUsersPassword");
		t8.setSyresource(t1);
		super.getBaseDao().merge(t8);

	}

	private void c3(Syresource node, int k) {// 资源管理
		int i = 0;
		Syresource t1 = new Syresource();
		t1.setId(node.getId() + "-" + k + "-" + i++);
		t1.setName("资源管理");
		t1.setOnoff("1");
		t1.setComments("资源管理");
		t1.setUrl("/resourceController.do?resource");
		t1.setSyresource(node);
		super.getBaseDao().merge(t1);

		Syresource t2 = new Syresource();
		t2.setId(node.getId() + "-" + k + "-" + i++);
		t2.setName("资源表格");
		t2.setOnoff("1");
		t2.setComments("资源管理datagrid");
		t2.setUrl("/resourceController.do?datagrid");
		t2.setSyresource(t1);
		super.getBaseDao().merge(t2);

		Syresource t3 = new Syresource();
		t3.setId(node.getId() + "-" + k + "-" + i++);
		t3.setName("删除资源");
		t3.setOnoff("1");
		t3.setComments("删除资源");
		t3.setUrl("/resourceController.do?delete");
		t3.setSyresource(t1);
		super.getBaseDao().merge(t3);

	}

}
