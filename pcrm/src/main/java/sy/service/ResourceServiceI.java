package sy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import sy.hbm.Syresource;
import sy.model.DataGrid;
import sy.model.DataGridJson;
import sy.model.Resource;

public interface ResourceServiceI extends BaseServiceI {

	public List<Resource> offResourceList();

	public Resource getResourceByRequestPath(String requestPath);
	
	public List<Resource> getModuleResourceByRequestPath(String requestPath);
	
	public List<Syresource> getAccessResourceByRequestPath(String userid);

	//public boolean checkAuth(String userId, String requestPath);
	public Set<Syresource> checkAuth(String userId) ;
	public DataGridJson datagrid(DataGrid dg, Resource resource);

	public void delete(String id) throws Exception;
	
	public Resource add(Resource res) ;
	
	public Resource edit(Resource res) ;

	public List<Syresource> findAll();
	
	public boolean checkAuth(String userId, String requestPath) ;
	

}
