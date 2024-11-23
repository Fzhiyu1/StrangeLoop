package com.xiaofeng.strangeloop.system.service.impl;

import com.xiaofeng.strangeloop.system.domain.SysUser;
import com.xiaofeng.strangeloop.system.mapper.SysUserMapper;
import com.xiaofeng.strangeloop.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> findAllUsers(SysUser sysUser) {

        return sysUserMapper.selectAllUser(sysUser);
    }
}
