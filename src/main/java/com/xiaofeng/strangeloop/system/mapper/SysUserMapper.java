package com.xiaofeng.strangeloop.system.mapper;

import com.xiaofeng.strangeloop.system.domain.SysUser;

import java.util.Arrays;
import java.util.List;

public interface SysUserMapper {
    public List<SysUser> selectAllUser(SysUser sysUser);
}
