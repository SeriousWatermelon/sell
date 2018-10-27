package com.yy.aspect;

import com.yy.constant.CookieConstant;
import com.yy.constant.RedisConstant;
import com.yy.exception.SellException;
import com.yy.exception.SellerAuthorizeException;
import com.yy.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * 卖家身份授权AOP
 * Created by 稻草人 on 2018/10/27.
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * AOP拦截器
     * @Pointcut： 拦截切入点
     * com.yy.controller： com下yy下的controller
     * Seller*：以Seller开头
     * *(..)： 里面的所有方法
     * &&: 并且
     * !execution：不包括
     * 拦截器拦截的方法不应包括用户登录方法和登出方法，因此需要将其排除
     */
    @Pointcut("execution(public * com.yy.controller.Seller*.*(..))" +
    "&& !execution(public * com.yy.controller.SellerUserController.*(..))")
    public void vertify(){}

    /**
     * 在切入点之前
     */
    @Before("vertify()")
    public void doVertify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookid
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie == null){ // 该没有登录
            log.warn("【登录校验】Cookie中无tooken");
            throw new SellerAuthorizeException();
        }

        //查询redis
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】redis中无tooken");
            throw new SellerAuthorizeException();
        }
    }

}
