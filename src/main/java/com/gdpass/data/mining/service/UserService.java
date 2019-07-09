package com.gdpass.data.mining.service;

import com.gdpass.data.mining.auth.CustomUserDetails;
import com.gdpass.data.mining.domain.UserProfile;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import reactor.core.publisher.Mono;

@Component
public class UserService {
    public Mono<ServerResponse> currentUser(ServerRequest request) {
        return request.principal()
            .ofType(Authentication.class)
            .flatMap(auth -> {
                CustomUserDetails user = CustomUserDetails.class.cast(auth.getPrincipal());
                UserProfile profile = new UserProfile();
                profile.setUsername(user.getUsername());
                profile.setName(user.getAccount_name());
                profile.setBranch(user.getBranch_id());
                profile.setBranchName(user.getBranch_name());
                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(profile));
            });
    }
}