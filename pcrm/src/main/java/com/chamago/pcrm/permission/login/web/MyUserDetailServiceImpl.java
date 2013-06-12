package com.chamago.pcrm.permission.login.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.common.utils.PCRMEncrypt;

import sy.hbm.Syrole;
import sy.hbm.SyroleSyreource;
import sy.hbm.Syuser;
import sy.hbm.SyuserSyrole;
import sy.service.UserServiceI;

@Service("userDetailsService")
public class MyUserDetailServiceImpl implements UserDetailsService {

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	private UserServiceI userService;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		String[] param = username.split(",");
		if(param.length>1){
//			try {
//				//param[1] = java.net.URLDecoder.decode(param[1], "gb2312");
//				param[1] = new String(param[1].getBytes("ISO-8859-1"),"UTF-8");
//				//param[1] = new String(param[1].getBytes("ISO-8859-1"),"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			Syuser dbUser = userService.getUser(param[0], param[1].split(":")[0]);
			// Users users = this.usersDao.findByName(username);
			if (dbUser == null) {
				throw new UsernameNotFoundException(username);
			}
			Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(dbUser);
	
			boolean enables = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
	
			User userdetail = new User(dbUser.getName(), dbUser.getPassword(),
					enables, accountNonExpired, credentialsNonExpired,
					accountNonLocked, grantedAuths);
			
			return userdetail;
		}else{
			throw new UsernameNotFoundException("没有卖家名称");
		}
	}

	// 取得用户的权限
	private Collection<GrantedAuthority> obtionGrantedAuthorities(
			Syuser user) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		Syuser syuser = new Syuser();
		BeanUtils.copyProperties(user, syuser);
		Set<SyuserSyrole> roles = syuser.getSyuserSyroles();

		for (SyuserSyrole ur : roles) {
				authList.add(new GrantedAuthorityImpl(ur.getSyrole().getId()));
		}
		return authList;
	}
}
