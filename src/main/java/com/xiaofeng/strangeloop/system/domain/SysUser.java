package com.xiaofeng.strangeloop.system.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 用户类
 */
@Data
public class SysUser implements UserDetails {
    private Integer id;
    private String account;
    private String email;
    private String phone;
    private Integer disable;
    private Integer role;
    private String name;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 根据用户的角色返回对应的权限列表
        // 这里只是简单示例，假设角色是 "ROLE_USER"
        return List.of(() -> "ROLE_" + role);  // 如果角色是 "USER"，则返回 "ROLE_USER"
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
