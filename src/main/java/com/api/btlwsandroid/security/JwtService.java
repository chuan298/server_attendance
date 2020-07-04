package com.api.btlwsandroid.security;

import com.api.btlwsandroid.dao.entity.Student;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtService {

	public static final String USERNAME = "username";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String SECRET_KEY = "laithiduyenxinhgaidangyeu16022001";
	public static final int EXPIRE_TIME = 864000000;

	public String generateTokenLogin(Student student) {
		String token = null;
		try {
			// Create HMAC signer
			JWSSigner signer = new MACSigner(generateShareSecret());

			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(ID, student.getId());
			builder.claim(NAME, student.getName());
			builder.claim(USERNAME, student.getUsername());
			builder.expirationTime(generateExpirationDate());

			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

			// Apply the HMAC protection
			signedJWT.sign(signer);

			// Serialize to compact form, produces something like
			// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
			token = signedJWT.serialize();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	private JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claims;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRE_TIME);
	}

	private Date getExpirationDateFromToken(String token) {
		Date expiration = null;
		JWTClaimsSet claims = getClaimsFromToken(token);
		expiration = claims.getExpirationTime();
		return expiration;
	}

	public String getUsernameFromToken(String token) {
		String username = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			username = claims.getStringClaim(USERNAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}

	public String getNameFromToken(String token) {
		String name = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			name = claims.getStringClaim(NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	public Integer getIdFromToken(String token) {
		Integer id = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			id = claims.getIntegerClaim(ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	private byte[] generateShareSecret() {
		// Generate 256-bit (32-byte) shared secret
		byte[] sharedSecret = new byte[32];
		sharedSecret = SECRET_KEY.getBytes();
		return sharedSecret;
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateTokenLogin(String token) {
		if (token == null || token.trim().length() == 0) {
			return false;
		}
		String username = getUsernameFromToken(token);
		String name = getNameFromToken(token);
		Integer id = getIdFromToken(token);
		if (username == null || username.isEmpty() || name == null || name.isEmpty() || id == null) {
			return false;
		}
		if (isTokenExpired(token)) {
			return false;
		}
		return true;
	}
	public String getJwtFromHeader(HttpHeaders headers) {
		String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
