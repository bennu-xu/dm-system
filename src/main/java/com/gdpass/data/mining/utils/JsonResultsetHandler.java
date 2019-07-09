package com.gdpass.data.mining.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dm.jdbc.driver.DmdbType;

@Service
public class JsonResultsetHandler {
    private static final Logger logger = LoggerFactory.getLogger(JsonResultsetHandler.class);

    public JsonResultsetHandler() {

    }

    public JSONArray handle(ResultSet rs) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        ResultSetMetaData metaData = rs.getMetaData();
        while (rs.next()) {
            JSONObject obj = new JSONObject();
            getType(rs, metaData, obj);
            jsonArray.add(obj);
        }
        
        return jsonArray;
    }

    /**
     * 从数据库游标中获取数据，构建 JSONObject 
     * ps； 有递归调用
     * */
    private void getType(ResultSet rs, ResultSetMetaData metaData, JSONObject obj) throws SQLException {
        int total_rows = metaData.getColumnCount();
        for (int i = 0; i < total_rows; i++) {
            String columnName = metaData.getColumnLabel(i + 1);
            if (obj.containsKey(columnName)) {
                columnName += "1";
            }
            try {
                switch (metaData.getColumnType(i + 1)) {
                    case java.sql.Types.ARRAY:
                        obj.put(columnName, rs.getArray(columnName));
                        break;
                    case java.sql.Types.BIGINT:
                        obj.put(columnName, rs.getInt(columnName));
                        break;
                    case java.sql.Types.BOOLEAN:
                        obj.put(columnName, rs.getBoolean(columnName));
                        break;
                    case java.sql.Types.BLOB:
                        obj.put(columnName, rs.getBlob(columnName));
                        break;
                    case java.sql.Types.DOUBLE:
                        obj.put(columnName, rs.getDouble(columnName));
                        break;
                    case java.sql.Types.FLOAT:
                        obj.put(columnName, rs.getFloat(columnName));
                        break;
                    case java.sql.Types.INTEGER:
                        obj.put(columnName, rs.getInt(columnName));
                        break;
                    case java.sql.Types.NVARCHAR:
                        obj.put(columnName, rs.getNString(columnName));
                        break;
                    case java.sql.Types.VARCHAR:
                        obj.put(columnName, rs.getString(columnName));
                        break;
                    case java.sql.Types.TINYINT:
                        obj.put(columnName, rs.getInt(columnName));
                        break;
                    case java.sql.Types.SMALLINT:
                        obj.put(columnName, rs.getInt(columnName));
                        break;
                    case java.sql.Types.DATE:
                        obj.put(columnName, rs.getDate(columnName));
                        break;
                    case java.sql.Types.TIMESTAMP:
                        obj.put(columnName, rs.getTimestamp(columnName));
                        break;
                    case DmdbType.CURSOR:
                    case DmdbType.ORACLE_CURSOR:
                        obj.put(columnName, handle((ResultSet)rs.getObject(columnName)));
                        break;
                    default:
                        obj.put(columnName, rs.getObject(columnName));
                        break;
                }
            } catch (Exception e) {
                logger.error("transfer result set to json array fail with columnName:" + columnName, e);
            }
        }
    }
}