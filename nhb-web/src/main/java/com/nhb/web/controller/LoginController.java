package com.nhb.web.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nhb.config.SessionCache;
import com.nhb.config.WebSecurityContextRepository;
import com.nhb.domain.AuthorizationInfo;
import com.nhb.domain.user.Useracc;
import com.nhb.service.LoginService;
import com.nhb.service.impl.UseraccDaoMapper;
import com.nhb.web.exception.BadRequest;
import com.nhb.web.exception.CheckException;

/**
 * LoginServiceController is meant to serve all login related requests 
 * @author barnabas.peter
 *
 */
@Controller
public class LoginController extends AbstractController{
	
	
	private static final String SESSION_USERACC_IS_NOT_EMTPY = "SESSION useracc is not emtpy";

	public static final String[] USER_SCOPE = new String[]{"ROLE_USER"};

	public static final String[] REGISTERED_SCOPES = new String[]{"ROLE_USER","ROLE_REGISTERED"};

	private static final String USER = "user";

	final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private WebSecurityContextRepository securityContextRepository;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	UseraccDaoMapper userDaoMapper;	
	private static final String SPRING_PERMISSION_PREFIX = "ROLE_";
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginGet(HttpServletRequest request,HttpServletResponse response){
		return "login";
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "login") String login,
			@RequestParam(value = "password") String password, Model model){

		if(login!=null && password!=null){
			Useracc useracc=loginService.getUseraccByEmail(login.toLowerCase());
			boolean valid=false;
			if(useracc!=null){
				String encoded=userDaoMapper.checkPassword(password, useracc.getPassword());
				valid=useracc.getPassword().equals(encoded);
			}
			if(!valid){
				useracc=loginService.getUseraccByLogin(login);
				if(useracc!=null){
					String encoded=userDaoMapper.checkPassword(password, useracc.getPassword());
					valid=useracc.getPassword().equals(encoded);
				}
			}
			if(valid){
				// setup authorization Info and SessionCache
				setupSecurityContext(request, useracc);
				// end setup securityContext

				return "forward:/";
			}else{
				model.addAttribute("message", "LOGIN_ERROR");
				return "login";
			}
		}
		throw new BadRequest("login or password missing");
	}
	private void setupSecurityContext(HttpServletRequest request, Useracc useracc) {
		SessionCache cache=securityContextRepository.createSessionCache(useracc);
		AuthorizationInfo authInfo=getAuthorizationInfo(cache,new String[]{"permission1","permission2"});
		cache.setAuthorizationInfo(authInfo);
		logger.info("user: " + authInfo.getUser());
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (String permission : cache.getAuthorizationInfo().getPermissions()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(SPRING_PERMISSION_PREFIX + permission.toUpperCase()));
		}
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(cache, null, grantedAuthorities);
		cache.setAuthenticationToken(auth);
		securityContextRepository.addSessionCache(cache, request);
	}
	
	
	public AuthorizationInfo getAuthorizationInfo(SessionCache sessionCache, String[] permissions) {
		AuthorizationInfo authInfo = new AuthorizationInfo();
		authInfo.setUser(sessionCache.getUser().getLogin());
		authInfo.setPermissions(Arrays.asList(permissions));
		return authInfo;
	}

	@RequestMapping(value = "/api/changepassword", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object changePassword(HttpServletRequest request,@RequestParam(required=true,value="password") String password,
			@RequestParam(required=true,value="new_password") String newPassword,
			Authentication authentication) {
		SessionCache session=checkForAuth(authentication);
		
		Useracc checkUser=userDaoMapper.findOne(session.getUser().getId());
		if(checkUser==null){
			throw new CheckException("wrong old password was given");
		}
		String encoded=userDaoMapper.checkPassword(password, checkUser.getPassword());
		boolean valid=checkUser.getPassword().equals(encoded);
		if(!valid){
			throw new CheckException("PASSWORD_DOES_NOT_MATCH");
		}
		String encPassword=userDaoMapper.encodePassword(newPassword);
		checkUser.setPassword(encPassword);
		userDaoMapper.updateUser(checkUser);
		return wrapPayload("status", "ok");
	}	
	
}
