package com.uestc.controllerteam.chartservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.uestc.controllerteam.chartservice.exception.AuthException;
import com.uestc.controllerteam.chartservice.exception.AuthException2;
import com.uestc.controllerteam.chartservice.exception.AuthException3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Lehr
 * @create: 2020-02-04
 */
public class JwtUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     签发对象：这个用户的id
     签发时间：现在
     有效时间：30分钟
     载荷内容：暂时设计为：这个人的名字，这个人的昵称
     加密密钥：这个人的id加上一串字符串
     */
    public static String createToken(String username) {

//        Calendar nowTime = Calendar.getInstance();
//        nowTime.add(Calendar.MINUTE,6000);
//        Date expiresDate = nowTime.getTime();

        return JWT.create().withAudience(username)   //签发对象
//                .withIssuedAt(new Date())    //发行时间
//                .withExpiresAt(expiresDate)  //有效时间
                .sign(Algorithm.HMAC256(username+"hello"));   //加密
    }

    /**
     * 检验合法性，其中secret参数就应该传入的是用户的id
     * @param token
     */
    public static void verifyToken(String token, String secret) throws RuntimeException {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret+"hello")).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            logger.error("----",e);
            //效验失败
            throw new AuthException2("token校验失败");
        }
    }

    /**
     * 获取签发对象
     */
    public static String getAudience(String token) throws RuntimeException {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
            throw new AuthException3("token解析失败");
        }
        return audience;
    }


    /**
     * 通过载荷名字获取载荷的值
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }
}