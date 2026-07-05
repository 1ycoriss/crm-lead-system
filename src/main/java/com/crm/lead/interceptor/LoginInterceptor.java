package com.crm.lead.interceptor;

import com.crm.lead.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (token == null || token.trim().isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            Result<String> result = Result.fail("请先登录");
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            writer.close();
            return false;
        }

        String redisKey = "login:token:" + token;
        String username = stringRedisTemplate.opsForValue().get(redisKey);
        if (username == null || username.trim().isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            Result<String> result = Result.fail("登录已失效，请重新登录");
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            writer.close();
            return false;
        }

        return true;
    }
}