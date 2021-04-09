package com.example.demo.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails{
	private String userName;  
    private String password;  
    private List<GrantedAuthority> authorities;  
    public MyUserDetails(String userName, String password, List<GrantedAuthority> authorities){  
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
    	return true;
    }  

}
