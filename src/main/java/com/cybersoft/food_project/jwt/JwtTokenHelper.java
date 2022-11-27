package com.cybersoft.food_project.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper {
    private final String strKey = "xJHDonkgbMOgIGNodeG7kWkgYuG6o28gbeG6rXQgxJHhuqd5IMSR4bunIDI1NiBiaXQ="; //Chuỗi base 64
    private Gson gson = new Gson();
    public String generateToken(String data,String type,long expiredDate){
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + expiredDate);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));

        Map<String,Object> subJectData = new HashMap<>();
        subJectData.put("username",data);
        subJectData.put("type",type);

        String json = gson.toJson(subJectData);//{"type":"refesh","username":"nguyenvana@gmail.com"}

        return Jwts.builder()
                .setSubject(json) //Lưu trữ dữ liệu vào trong token kiểu String
                .setIssuedAt(now) //Thời gian tạo ra token
                .setExpiration(dateExpired) //Thời gian hết hạn của token
                .signWith(secretKey,SignatureAlgorithm.HS256) // Thuật toán mã hoá và secrect key
                .compact(); //Trả ra token đã được mã hoá
    }

    public String decodeToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validaToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        boolean isSuccess = false;
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            isSuccess = true;
        }catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return isSuccess;

    }

}
