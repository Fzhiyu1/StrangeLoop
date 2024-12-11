package com.xiaofeng.strangeloop.system.control;

import com.xiaofeng.strangeloop.springframework.JwtUtil;
import com.xiaofeng.strangeloop.system.domain.ApiResponse;
import com.xiaofeng.strangeloop.system.domain.LoginRequest;
import com.xiaofeng.strangeloop.system.domain.PageResult;
import com.xiaofeng.strangeloop.system.domain.SysUser;
import com.xiaofeng.strangeloop.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用户管理Controler
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserControl {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 查询所有用户
     * @return ApiResponse
     */
    @GetMapping("/all")
    public ApiResponse<PageResult> getSysUsersAll() {

        return ApiResponse.success(new PageResult<>(sysUserService.findAllUsers(new SysUser())));
    }

    /**
     * 登录用户
     * @param loginRequest
     * @return ApiResponse
     */
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest loginRequest) {
        //根据登录用户账号查询用户是否存在
        SysUser user = sysUserService.findUserByName(loginRequest.getAccount());
        //当用户不存在或者密码不正确的时候返回错误信息
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ApiResponse.errorLogin("用户名或密码错误");
        }
        //通过验证则获取token
        String token = JwtUtil.generateToken(user.getAccount());

        //返回验证成功信息和token
        return ApiResponse.success(token);

    }

    /**
     * 创建用户类
     * @param sysUser
     * @return ApiResponse
     */
    @PostMapping("/create")
    public ApiResponse<String> createOneUser(@RequestBody SysUser sysUser) {
//        当用户已存在的时候
        if(sysUserService.findUserByName(sysUser.getAccount()) !=null){
            return ApiResponse.error("创建失败,用户已存在，请重新命名");
        }
//        创建用户
        int i = sysUserService.insertUser(sysUser);

//        创建失败
        if ( i== -1) {
            return ApiResponse.error("创建失败");
        }
//        返回创建成功
        return ApiResponse.createdUserSuccess();
    }

}
