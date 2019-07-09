package com.gdpass.data.mining.service;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import com.alibaba.fastjson.JSONArray;
import com.gdpass.data.mining.auth.CustomUserDetails;
import com.gdpass.data.mining.repository.DataMiningRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
public class DataMiningService {
    private static final Logger logger = LoggerFactory.getLogger(DataMiningRepository.class);

    @Autowired
    private DataMiningRepository repository;

    public Mono<ServerResponse> collectionYearReport(ServerRequest request) {
        return request.principal()
            .ofType(Authentication.class)
            .flatMap(auth -> {
                try {
                    CustomUserDetails user = CustomUserDetails.class.cast(auth.getPrincipal());
                    String year = getQueryParam(request, "year", " 00:00:00");
                    if("".equals(year)) {
                        return failResponse(400, "年份参数不能为空，参数格式为：yyyy-MM-dd");
                    }
                    return executeQuery(user.getBranch_id(), "\"data_mining\".CollectionRpt_year", year);
                } catch(Exception ex) {
                    logger.error("execute query data_mining.CollectionRpt_year fail:", ex);
                    return failResponse(500, ex.getMessage());
                }
            });
    }

    public Mono<ServerResponse> collectionPlaceYearReport(ServerRequest request) {
        return request.principal()
            .ofType(Authentication.class)
            .flatMap(auth -> {
                try {
                    CustomUserDetails user = CustomUserDetails.class.cast(auth.getPrincipal());
                    String year = getQueryParam(request, "year", " 00:00:00");
                    if("".equals(year)) {
                        return failResponse(400, "年份参数不能为空，参数格式为：yyyy-MM-dd");
                    }
                    return executeQuery(user.getBranch_id(), "\"data_mining\".CollectionRptByPlace_year", year);
                } catch(Exception ex) {
                    logger.error("execute query data_mining.CollectionRptByPlace_year fail:", ex);
                    return failResponse(500, ex.getMessage());
                }
            });
    }


    private String getQueryParam(ServerRequest request, String paramName, String suffix) {
        String value = request.queryParam(paramName).orElse("");
        if(!"".equals(value)) {
            return value + suffix;
        }
        return value;
    }

    private Mono<ServerResponse> executeQuery(String branch, String procedure, String... arguments) {
        JSONArray result = repository.executeQuery(branch, procedure, arguments);;
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(result));
    }

    public Mono<ServerResponse> failResponse(int status, String failMessage) {
        return ServerResponse.status(status).body(fromObject(failMessage));
    }
}