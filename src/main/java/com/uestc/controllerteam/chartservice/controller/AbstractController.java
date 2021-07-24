package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.exception.*;
import com.uestc.controllerteam.chartservice.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleException(HttpServletRequest request,
                                  Exception ex,
                                  HttpServletResponse response) {
        logger.error("未处理异常", ex);
        return createJsonResponse(null);
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleAuthException(HttpServletRequest request,
                                  Exception ex,
                                  HttpServletResponse response) {
        logger.error("未处理异常", ex);
        return createJsonResponse(null);
    }

    @ExceptionHandler(NonUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleNonUserException(HttpServletRequest request,
                                      Exception ex,
                                      HttpServletResponse response) {
        logger.error("未处理异常", ex);
        return createJsonResponse(null);
    }

    @ExceptionHandler(SplitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleSplitException(HttpServletRequest request,
                                         Exception ex,
                                         HttpServletResponse response) {
        logger.error("未处理异常", ex);
        return createJsonResponse(null);
    }

    @ExceptionHandler(AuthException2.class)
    @ResponseStatus(HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
    @ResponseBody
    public String handleAuth2Exception(HttpServletRequest request,
                                       Exception ex,
                                       HttpServletResponse response) {
        logger.error("未处理异常", ex);
        return createJsonResponse(null);
    }

    @ExceptionHandler(AuthException3.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleAuth3Exception(HttpServletRequest request,
                                       Exception ex,
                                       HttpServletResponse response) {
        logger.error("未处理异常", ex);
        return createJsonResponse(null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegalArgumentException(HttpServletRequest request,
                                                 IllegalArgumentException ex,
                                                 HttpServletResponse response) {
        return createJsonResponse(null);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegaStatException(HttpServletRequest request,
                                            IllegalStateException ex,
                                            HttpServletResponse response) {
        return createJsonResponse(null);
    }

    public String createJsonResponse(Object data) {
        return GsonUtils.toJsonString(data);
    }


}
