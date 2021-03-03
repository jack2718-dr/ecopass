package com.apsol.ecopass.core.tui;

import com.querydsl.core.types.Expression;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SearchPath {

    private static final String filterPrefix = "apsol_";
    private static final String sortPrefix = "apsol_";

    private Map<Expression<?>, String> filterMap = new HashMap<>();
    private Map<Expression<?>, String> sortMap = new HashMap<>();

    public SearchPath	( Map<String, String> 		  params,
                          Map<String, Expression<?>>  dataPaths   ) {

        for (Map.Entry<String, String> param : params.entrySet()) {
            String key = param.getKey();
            String val = param.getValue().trim();

            if (key.contains(filterPrefix))
                setSearchMap(key, val, dataPaths, filterMap);

            else if (key.contains(sortPrefix))
                setSearchMap(key, val, dataPaths, sortMap);
        }
    }

    private void setSearchMap(String key, String value, Map<String, Expression<?>> pathMap, Map<Expression<?>, String> requestMap) {
        key = key.substring(key.indexOf("_") + 1);
        Expression<?> exp = pathMap.get(key);
        if (exp == null)
            throw new RuntimeException("params error :: 찾을 수 없는 필드값입니다. " + key);

        requestMap.put(exp, value);
    }

}
