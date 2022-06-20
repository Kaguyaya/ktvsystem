package com.kaguya.ktvadmin;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static com.kaguya.ktvadmin.config.JwtDemo.generateJwtToken;
import static com.kaguya.ktvadmin.config.JwtDemo.getClaimsFromJwt;

public class JwtTest {
    @Test
    public void test(){
            String jwtToken = generateJwtToken();
            System.out.println("JWT Token "+ jwtToken);
            System.out.println("=======================================================");
            Claims claims = getClaimsFromJwt(jwtToken);
            System.out.println(claims);
            System.out.println(claims.get("userNickname"));
    }
}