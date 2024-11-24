package com.xiaofeng.strangeloop.system.service;

import com.xiaofeng.strangeloop.system.domain.SysUser;

import java.util.List;

public interface SysUserService {

    public List<SysUser> findAllUsers(SysUser sysUser);

    public SysUser findUserByName(String account);

    public int insertUser(SysUser sysUser);
}
