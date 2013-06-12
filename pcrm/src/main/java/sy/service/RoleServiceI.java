package sy.service;

import java.util.List;

import sy.hbm.Syrole;
import sy.hbm.SyroleSyreource;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.Role;
import sy.model.ZtreeNode;

public interface RoleServiceI extends BaseServiceI {

	public DataGridJson datagrid(DataGrid dg, Role role);

	public List<ZtreeNode> resourceTree();

	public List<String> getRoleResources(String roleId);

	public void delete(String ids);

	public Role edit(Role role);

	public Role add(Role role);
	
	public List<Syrole> findAll();

	public List<SyroleSyreource> findSyroleSyreourceByRoleId(String roleId);
}
