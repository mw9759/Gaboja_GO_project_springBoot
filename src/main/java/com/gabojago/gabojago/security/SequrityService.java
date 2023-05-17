package com.gabojago.gabojago.security;


import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SequrityService {
	private static final String SECRET_KEY = "asdmkpasfmapsfnoaisfnewpivnepsadasdasdasdasifn";
	
	//로그인 서비스 던질때 같이 ~~
	public String createToken(String subject, long expTime) {
		if(expTime<0) {
			throw new RuntimeException("만료시간이 0보다 커야함");
		}
		
		SignatureAlgorithm signatureAlgorith = SignatureAlgorithm.HS256;
		
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorith.getJcaName());
		
		// 만든 키 리턴.
		// 보통은 서브젝트는 유저 아이디
		// 비밀번호는 시크릿 키를 만드는데 쓴다.
		return Jwts.builder()
				.setSubject(subject)
				.signWith(signingKey, signatureAlgorith)
				.setExpiration(new Date(System.currentTimeMillis()+expTime))
				.compact();
	}
	
	public String getSubject(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
}
