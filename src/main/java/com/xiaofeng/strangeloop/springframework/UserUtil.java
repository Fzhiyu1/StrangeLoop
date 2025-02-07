package com.xiaofeng.strangeloop.springframework;

import com.xiaofeng.strangeloop.system.domain.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            SysUser customUserDetails = (SysUser) authentication.getPrincipal();
            // 假设 UserDetails 实现类有一个 getId() 方法返回用户ID
            return customUserDetails.getId();  // 或 userDetails.getUsername() 等
        }
        return null;
    }
}
