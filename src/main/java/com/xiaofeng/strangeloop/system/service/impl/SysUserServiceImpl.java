package com.xiaofeng.strangeloop.system.service.impl;

import com.xiaofeng.strangeloop.system.domain.SysUser;
import com.xiaofeng.strangeloop.system.mapper.SysUserMapper;
import com.xiaofeng.strangeloop.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<SysUser> findAllUsers(SysUser sysUser) {

        return sysUserMapper.selectAllUser(sysUser);
    }

    @Override
    public SysUser findUserByName(String account) {
        SysUser sysUser = sysUserMapper.selectUserByName(account);
        return sysUser;
    }

    @Override
    public int insertUser(SysUser sysUser) {
        try {
            //        密码加密
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
//        设置创建时间
            sysUser.setCreateTime(LocalDateTime.now());
            sysUserMapper.insertOneUser(sysUser);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
}
