package com.gdpass.data.mining.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class WebController {
    @Value("classpath:/static/index.html")
    private Resource indexHtml;
    @Value("classpath:/static/login-form.html")
    private Resource loginHtml;
    @Value("classpath:/static/bye.html")
    private Resource logoutHtml;

    @Bean
    public RouterFunction<?> iconResources() {
        return RouterFunctions
                .resources("/favicon.**", new ClassPathResource("static/assets/images/favicon.ico"));
    }

    @Bean
    RouterFunction<ServerResponse> staticResourceRouter(){
        return RouterFunctions.resources("/**", new ClassPathResource("static/"));
    }

    @Bean
    public RouterFunction<?> viewRoutes() {
        return RouterFunctions
                .route(RequestPredicates.GET("/login"),
                        req -> ServerResponse
                                .ok()
                                .syncBody(loginHtml)
                )
                .andRoute(RequestPredicates.GET("/bye"),
                        req -> ServerResponse.ok().syncBody(logoutHtml)
                )
                .andRoute(RequestPredicates.GET("/"),
                        req -> req.principal()
                                .ofType(Authentication.class)
                                .flatMap(auth -> {
                                    UserDetails user = UserDetails.class.cast(auth.getPrincipal());
                                    req.exchange()
                                            .getAttributes()
                                            .putAll(Collections.singletonMap("user", user));
                                    return ServerResponse.ok().syncBody(indexHtml);
                                })
                );
    }
    
}