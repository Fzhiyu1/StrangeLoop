package com.xiaofeng.strangeloop.control;

import com.xiaofeng.strangeloop.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/user")
public class SysUserControl {
    @Autowired
    private SysUserService sysUserService;


}
