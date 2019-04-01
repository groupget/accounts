package com.agh.groupget.accounts.infrastructure.rest;

import com.agh.groupget.accounts.domain.UserBasicInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Class responsible for extracting information from jwt. There is no need to do any validation or information checking
 * because gateway guarantees that token is valid
 */
@Configuration
class JwtFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFactory.class);

    @Bean
    @RequestScope
    UserBasicInfo userJwtInfo(HttpServletRequest request) {
        try {
            String jwt = getJwtFromRequest(request);
            DecodedJWT decodedJWT = JWT.decode(jwt);
            return getUserJwtInfoFromJwt(decodedJWT);
        } catch (Exception ex) {
            LOGGER.error("Error in JWT decoding, reason: {}", ex.getLocalizedMessage());
            throw ex;
        }
    }

    private UserBasicInfo getUserJwtInfoFromJwt(DecodedJWT decodedJWT) {
        String username = decodedJWT.getClaim("cognito:username")
                .asString();
        Set<String> groups = ImmutableSet.copyOf(decodedJWT.getClaim("cognito:groups")
                .asList(String.class));
        return new UserBasicInfo(username, groups);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorizationHeader.substring(7);
    }
}
