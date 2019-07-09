package com.gdpass.data.mining.auth;

import java.util.HashSet;
import java.util.Set;

import org.davidmoten.rx.jdbc.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import io.reactivex.Flowable;
import reactor.core.publisher.Mono;

/**
 * 
 * */
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private Database database;

    /**
     * 实现loadUserByUsername 方法获得 UserDetails 返回
     * CustomUserDetails实现UserDetails接口，自定义扩展
     * 
     */
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Flowable<CustomUserDetails> userDetails = database
            .select(" select o.account as account, o.password as password, o.name as account_name, o.branch as branch_id, b.shortname as branch_name "
            + " from bos.d_operator o, bos.d_branch b "
            + " where o.available = 1 and o.branch = b.id and o.account=?")
            .parameter(username)
            .get( rs -> {
                Set<GrantedAuthority> authorities = new HashSet<>();
                authorities.add(new SimpleGrantedAuthority(rs.getString("BRANCH_ID")));
                CustomUserDetails customUserDetails = new CustomUserDetails(rs.getString("ACCOUNT"), rs.getString("PASSWORD"), authorities);
                customUserDetails.setAccount_name(rs.getString("ACCOUNT_NAME"));
                customUserDetails.setBranch_id(rs.getString("BRANCH_ID"));
                customUserDetails.setBranch_name(rs.getString("BRANCH_NAME"));

                return customUserDetails;
            });
        
        return Mono.from(userDetails);
    }
}