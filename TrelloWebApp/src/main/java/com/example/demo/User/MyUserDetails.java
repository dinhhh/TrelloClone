package com.example.demo.User;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails{
	
	private String userName;  
    private String password;  
    private int enable;
    private List<GrantedAuthority> authorities;  
    
	
	public MyUserDetails(String userName, String password, int enable, List<GrantedAuthority> authorities) {
		super();
		this.userName = userName;
		this.password = password;
		this.enable = enable;
		this.authorities = authorities;
	}
	
	public MyUserDetails(String userName, String password, List<GrantedAuthority> authorities) {
		super();
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}
	@Override  
    public Collection<? extends GrantedAuthority> getAuthorities() {  
         return authorities;  
    }  
    @Override  
    public String getPassword() {  
         return password;  
    }  
    @Override  
    public String getUsername() {  
         return userName;  
    }  
    @Override  
    public boolean isAccountNonExpired() {  
         return true;  
    }  
    @Override  
    public boolean isAccountNonLocked() {  
         return true;  
    }  
    @Override  
    public boolean isCredentialsNonExpired() {  
         return true;  
    }  
    @Override  
    public boolean isEnabled() {  
    	return this.enable == 1 ? true : false;
    }  
}
