package com.example.demo.Security.OAuth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User{

	private OAuth2User user;
	
	public CustomOAuth2User(OAuth2User user) {
		this.user = user;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return user.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return user.getAuthorities();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return user.getAttribute("name");
	}
	
	public String getFullName() {
//		[[${request.userPrincipal.principal.fullName}]] thymeleaf 
		return user.getAttribute("name");
	}

}