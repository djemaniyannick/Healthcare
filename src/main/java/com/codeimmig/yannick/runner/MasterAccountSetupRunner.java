package com.codeimmig.yannick.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.codeimmig.yannick.constants.UserRoles;
import com.codeimmig.yannick.model.User;
import com.codeimmig.yannick.service.IUserService;
import com.codeimmig.yannick.util.UserUtil;


@Component
public class MasterAccountSetupRunner implements CommandLineRunner{
	
	@Value("${master.user.name}")
	private String displayName;
	
	@Value("${master.user.email}")
	private String username;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private IUserService userService;

	@Override
	public void run(String... args) throws Exception {
		if(!userService.findByUsername(username).isPresent()) {
			User user=new User();
			user.setDisplayName(displayName);
			user.setUsername(username);
			user.setPassword(userUtil.genPwd());
			user.setRole(UserRoles.ADMIN.name());
			userService.saveUser(user);
		}
		
	}
	
}
