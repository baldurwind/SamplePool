package sy.service;

import java.util.List;

import sy.hbm.Syuser;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.User;

public interface UserServiceI extends BaseServiceI {

	public DataGridJson datagrid(DataGrid dg, User user);

	public List<User> combobox(String q);

	public User add(User user);

	public User login(User user);

	public User edit(User user);

	public void delete(String ids);

	public DataGridJson roleDatagrid(DataGrid dg);

	public User getUser(String id);

	public Syuser getUser(String username,String sellernick);
	public boolean modifyPassword(User user);

	public List<String> getUserRoles(String userId);

	public void modifyUsersPassword(String ids, String password);

}
