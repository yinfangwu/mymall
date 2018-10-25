package com.mymall.common.Exception;

import com.mymall.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e){

        //打印异常日志
        log.error("{} Exception:",httpServletRequest.getRequestURI(), e);
        //
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        //返回与ServerResponse封装相同的格式
        modelAndView.addObject("status", ResponseCode.ERROR.getCode());
        modelAndView.addObject("msg", "接口发生异常，详情请查看服务器日志");
        modelAndView.addObject("data", e.toString());
        return modelAndView;
    }
}
