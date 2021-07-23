package com.uestc.controllerteam.chartservice.utils;

import com.uestc.controllerteam.chartservice.exception.AuthException;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.exception.NonUserException;
import com.uestc.controllerteam.chartservice.exception.SplitException;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws RuntimeException {
        String authorization = request.getHeader("Authorization");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }else {
            String token = null;
            if (authorization == null) {
                throw new AuthException("无token，请重新登录");
            }else {
                try {
                    token = authorization.split(" ")[1];
                } catch (Exception e) {
                    throw new SplitException("无token，请重新登录");
                }
            }

            // 获取 token 中的 userName
            String username = JwtUtils.getAudience(token);
            UserDto user = userRepository.queryUser(username);
            if (user == null) {
                throw new NonUserException("用户不存在！");
            }

            // 验证 token
//            JwtUtils.verifyToken(token, username);
            //放入attribute以便后面调用
            request.setAttribute("username", username);
            return true;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws RuntimeException {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws RuntimeException {
    }
}
