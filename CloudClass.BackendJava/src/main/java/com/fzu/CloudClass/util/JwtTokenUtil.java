package com.fzu.CloudClass.util;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
public class JwtTokenUtil {
    // 用于JWT进行签名加密的秘钥
    private static String SECRET = "code-duck-*%#@*!&";

    /**
     * @Param: 传入需要设置的payload信息
     * @return: 返回token
     */
    public static String generateToken(String Id) {
        JWTCreator.Builder builder = JWT.create();

        // 将map内的信息传入JWT的payload中
        builder.withClaim("Id", Id);

        // 设置JWT令牌的过期时间为60
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 3600);
        builder.withExpiresAt(instance.getTime());

        // 设置签名并返回token
        return builder.sign(Algorithm.HMAC256(SECRET)).toString();
    }

    /**
     * @Param: 传入token
     * @return:
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * @Param: 传入token
     * @return: 解密的token信息
     */
    public static String getTokenInfo(String token) {
        return JWT.decode(token).getClaims().get("Id").asString();
        //return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }




}

