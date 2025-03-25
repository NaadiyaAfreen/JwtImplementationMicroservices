package com.microservices.gateway.filter;

import com.microservices.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
       super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
           if(validator.isSecured.test(exchange.getRequest())) {
               //header contains token or not
               if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                   throw new RuntimeException("Authorization header not present");
               }
               String token=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
               if(token!=null && !token.isEmpty() && token.startsWith("Bearer ")) {
                   token=token.substring(7);
               }
               try {
                   jwtUtil.validateToken(token);
               }catch(Exception e) {
                   System.out.println("Invalid access token");
               }
           }
          return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
