package com.xiaofeng.strangeloop.config;

import com.xiaofeng.strangeloop.springframework.JwtUtil;
import com.xiaofeng.strangeloop.system.domain.SysUser;
import com.xiaofeng.strangeloop.system.service.SysUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SysUserService sysUserService;

    @Autowired
    public JwtAuthenticationFilter(@Lazy SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        // 直接放行无需 token 的接口
        if (path.startsWith("/sys/user/login") || path.startsWith("/sys/user/create")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getTokenFromRequest(request);


        // 1. 如果 token 为空或过期，返回 401 错误信息
        if (token == null || token.isEmpty() || JwtUtil.isTokenExpired(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 设置 401 状态码
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Token is missing or expired\"}");  // 返回 JSON 错误信息
            return;
        }


        // 2. 解析用户名，并验证 token
        String username = JwtUtil.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SysUser user = sysUserService.findUserByName(username);

            // 3. 如果 token 校验成功，设置用户身份
            if (user != null && JwtUtil.validateToken(token, user.getAccount())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 设置 401 状态码
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Invalid token\"}");  // 返回 JSON 错误信息
                return;
            }
        }

        // 4. 继续过滤链
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 跳过登录、用户创建等无需认证的接口
        return path.startsWith("/sys/user/login") || path.startsWith("/sys/user/create");
    }
}
