package com.xiaofeng.strangeloop.system.mapper;

import com.xiaofeng.strangeloop.system.domain.SysUser;

import java.util.Arrays;
import java.util.List;

/**
 *用户管理Mapper
 */
public interface SysUserMapper {
    public List<SysUser> selectAllUser(SysUser sysUser);

    public SysUser selectUserByName(String account);

    public int insertOneUser(SysUser sysUser);
}
