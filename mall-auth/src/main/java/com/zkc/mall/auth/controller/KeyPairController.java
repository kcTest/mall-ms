package com.zkc.mall.auth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@CrossOrigin
@RestController
public class KeyPairController {
	
	@Autowired
	private KeyPair keyPair;
	
	@RequestMapping("/rsa/publicKey")
	public Map<String, Object> getKey() {
		
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAKey rsaKey = new RSAKey.Builder(publicKey).build();
//		rsaKey.toJSONObject();
		return new JWKSet(rsaKey).toJSONObject();
	}
}
