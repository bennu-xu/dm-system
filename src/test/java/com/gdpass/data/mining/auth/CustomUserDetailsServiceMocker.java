package com.gdpass.data.mining.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import reactor.core.publisher.Mono;

/**
 * 
 * */
public class CustomUserDetailsServiceMocker implements ReactiveUserDetailsService {
    
    /**
     * 实现loadUserByUsername 方法获得 UserDetails 返回
     * CustomUserDetails实现UserDetails接口，自定义扩展
     * 
     */
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("GZ"));
        CustomUserDetails userDetails = new CustomUserDetails("test", "4QrcOUm6Wau+VuBX8g+IPg==", authorities);
        userDetails.setAccount_name("测试");
        userDetails.setBranch_id("GZ");
        userDetails.setBranch_name("广州血液中心");

        return Mono.just(userDetails);
    }
}