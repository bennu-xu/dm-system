package com.gdpass.data.mining.controller;

import com.gdpass.data.mining.service.DataMiningService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class DataMiningController {
    private final DataMiningService service;

    DataMiningController(DataMiningService service) {
        this.service = service;
    }

    @Bean
    public RouterFunction<ServerResponse> routeReport() {
        return RouterFunctions
                .route(RequestPredicates.GET("/collection/year-rpt")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                service::collectionYearReport)
                .andRoute(RequestPredicates.GET("/collection/place-year-rpt")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                service::collectionPlaceYearReport);
    }
}