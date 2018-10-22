/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */
package org.mule.templates.oauth2;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.keys.HmacKey;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Map;

/**
 * Utility class for accessToken operations
 */
public class SignPaymentUtils {

	/**
	 * Retrieve JWT claims from accessToken
	 * 
	 * @param expectedAudience
	 * @param signingKey
	 * @param client_id
	 * @param status
	 * @param transaction_id
	 * @param paymentId
	 * @return response
	 */
	public static String signPayment(String expectedAudience, String signingKey,
			String client_id, String status,String transaction_id, String paymentId){
		try {
			Key key = new HmacKey(signingKey.getBytes("UTF-8"));


			JwtClaims claims = new JwtClaims();
			claims.setIssuer(expectedAudience);  
			claims.setAudience(client_id); 
			claims.setStringClaim("status", status);
			claims.setStringClaim("transactionId", transaction_id);
			claims.setStringClaim("paymentId", paymentId);
			claims.setIssuedAtToNow();  

			JsonWebSignature jws  = new JsonWebSignature();
			jws.setAlgorithmHeaderValue("HS256");
			jws.setPayload(claims.toJson());
			jws.setKey(key);

			return jws.getCompactSerialization();
		} catch (JoseException | UnsupportedEncodingException e) {
			throw new RuntimeException("Error retrieving claims from token: " + e.getMessage());
		}

	}
}
