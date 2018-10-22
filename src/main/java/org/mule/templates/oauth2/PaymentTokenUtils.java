/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */
package org.mule.templates.oauth2;

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
/**
 * Utility class for accessToken operations
 */
public class PaymentTokenUtils {

	/**
	 * Retrieve JWT claims from request
	 * 
	 * @param signingKey
	 * @param request
	 * @param audience
	 * @return iss
	 * @return aud
	 * @return iat
	 * @return exp
	 * @return client_id
	 * @return redirect_uri
	 * @return payment
	 */
	public static Map<String, Object> getJwtClaims(String signingKey, String request,String audience) {
		try {
			Key key = new HmacKey(signingKey.getBytes("UTF-8"));
			JwtConsumer jwtConsumer = new JwtConsumerBuilder()
					.setRequireExpirationTime()
					.setMaxFutureValidityInMinutes(5)
					.setJwsAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.WHITELIST, "HS256"))
					.setExpectedAudience(audience)
					.setVerificationKey(key)
					.build();
			JwtClaims jwtClaims = jwtConsumer.processToClaims(request);
			return jwtClaims.getClaimsMap();
		} catch ( InvalidJwtException | UnsupportedEncodingException e) {
			System.err.println("Error retrieving claims from token: " + e.getMessage());
			throw new RuntimeException("Error retrieving claims from token: " + e.getMessage());
		}

	}
}
