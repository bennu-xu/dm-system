package com.gdpass.data.mining.repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

import com.alibaba.fastjson.JSONArray;
import com.gdpass.data.mining.utils.JsonResultsetHandler;

import org.davidmoten.rx.jdbc.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import dm.jdbc.driver.DmdbType;

@Configuration
@Profile("!test")
@Repository
public class ReactiveDataMiningRepository implements DataMiningRepository {
    private static final Logger logger = LoggerFactory.getLogger(DataMiningRepository.class);
    // branch 作为查询条件时开启
    @Value("${branch.as.first.param}")
    private boolean branchAsFirstParam;
    @Autowired
    private Database database;
    @Autowired
    private JsonResultsetHandler resultHandler;

    public JSONArray executeQuery(String branch, String procedure, String... arguments) {
        return database
            .apply(conn -> {
                try {
                    conn.setAutoCommit(false);
                    CallableStatement statement = conn.prepareCall(generateCallstatement(branch, procedure, arguments));
                    int argumentsLength = (arguments != null && arguments.length > 0) ? arguments.length + 1 : 1;
                    int start = 1;
                    if(branchAsFirstParam) {
                        argumentsLength += 1;
                        statement.setString(1, branch);
                        start = 2;
                    }
                    for(int i = start; i < argumentsLength; i++) {
                        statement.setTimestamp(i, Timestamp.valueOf(arguments[i - 1]));
                    }
                    statement.registerOutParameter(argumentsLength, DmdbType.ORACLE_CURSOR);

                    statement.execute();
                    ResultSet resultSet = (ResultSet)statement.getObject(argumentsLength);
                    return resultHandler.handle(resultSet);
                } catch(Exception ex) {
                    logger.error("execute query {} fail:", procedure, ex);
                    return new JSONArray();
                } finally {
                    if(conn != null) {
                        try {
                            conn.commit();
                        } catch (SQLException ex) {
                            logger.error("commit connection fail:", ex);
                        }
                    }
                }
            })
            .blockingGet();
    }

    private String generateCallstatement(String branch, String procedure, String... arguments) {
        StringBuilder sb = new StringBuilder("call ").append(procedure).append("(");
        if(branchAsFirstParam) {
            sb.append("?, ");
        }
        if(arguments != null && arguments.length > 0) {
            for(int i = 0; i < arguments.length; i++) {
                sb.append("?, ");
            }
        }
        sb.append("?)");
        logger.info("execute query " + sb.toString() + "with params: "
            + ((arguments != null && arguments.length > 0) ? Arrays.asList(arguments) : "null"));
        return sb.toString();
    }
}