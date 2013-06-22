package service;

import org.osoa.sca.annotations.Service;


@Service(TBService.class)
public class TBServiceImpl implements TBService{

	@Override
	public String display(String name) {
		System.out.println("Server Side:"+this);
		System.out.println("Service:"+name);
		return name;
	}

}
