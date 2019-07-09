package com.gdpass.data.mining.controller;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import com.gdpass.data.mining.auth.CustomUserDetails;
import com.gdpass.data.mining.domain.UserProfile;
import com.gdpass.data.mining.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;


/**
 *
 * */
@Component
public class SecurityController {
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    /**
     *  返回当前登录用户的信息
     *
     请求
     method=post, content-type=application/json
     返回
     {
       "branch_id": "FA",
       "account_name": "蔡萍",
       "branch_name": "南通血站",
       "username": "FA0402"
     }
     **/
    @Bean
    public RouterFunction<ServerResponse> routeSecurity(UserService service) {
        return RouterFunctions
                .route(RequestPredicates.GET("/userinfo")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                service::currentUser)
                .andRoute(RequestPredicates.POST("/userinfo")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                service::currentUser);
    }

}
