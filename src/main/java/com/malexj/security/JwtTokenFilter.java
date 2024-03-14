package com.malexj.security;

import com.malexj.exception.JwtAuthenticationFilterException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtTokenFilter implements WebFilter {

  @Value("${jwt.app.secret}")
  private String jwtSecret;

  @Value("${jwt.app.provide_security}")
  private boolean provideJwtSecurity;

  public static final String HEADER_PREFIX = "Bearer ";
  public static final String REST_API_PATH = "/v1";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    var request = exchange.getRequest();
    if (onlyForRestApi(request) && provideJwtSecurity) {
      resolveToken(exchange.getRequest()).ifPresent(this::verifyJwtToken);
    }
    return chain.filter(exchange);
  }

  private static boolean onlyForRestApi(ServerHttpRequest request) {
    return Optional.ofNullable(request.getURI().getPath()).stream()
        .anyMatch(path -> path.contains(REST_API_PATH));
  }

  private Optional<String> resolveToken(ServerHttpRequest request) {
    var token =
        Optional.of(request.getHeaders())
            .map(header -> header.getFirst(HttpHeaders.AUTHORIZATION))
            .filter(
                bearerToken ->
                    StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX))
            .orElseThrow(
                () ->
                    new JwtAuthenticationFilterException("JWT token not found in request headers"));
    return Optional.of(token.substring(7));
  }

  private void verifyJwtToken(String jwtToken) {
    try {
      var signedJWT = SignedJWT.parse(jwtToken);
      var verifier = new MACVerifier(jwtSecret);
      if (!signedJWT.verify(verifier)) {
        throw new JwtAuthenticationFilterException("JWT token verification failed");
      }
      // Token is valid
      // You can extract claims from the JWT token if needed
    } catch (ParseException | JOSEException e) {
      throw new JwtAuthenticationFilterException("Error parsing JWT token", e);
    }
  }
}
