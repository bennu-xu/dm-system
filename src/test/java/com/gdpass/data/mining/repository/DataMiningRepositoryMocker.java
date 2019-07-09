package com.gdpass.data.mining.repository;

import com.alibaba.fastjson.JSONArray;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("test")
@Repository
public class DataMiningRepositoryMocker implements DataMiningRepository {

    @Override
    public JSONArray executeQuery(String branch, String procedure, String... arguments) {
        return new JSONArray();
    }
}