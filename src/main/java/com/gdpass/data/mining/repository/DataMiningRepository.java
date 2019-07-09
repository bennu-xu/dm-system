package com.gdpass.data.mining.repository;

import com.alibaba.fastjson.JSONArray;

@FunctionalInterface
public interface DataMiningRepository {
    JSONArray executeQuery(String branch, String procedure, String... arguments);
}