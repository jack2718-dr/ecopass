package com.apsol.ecopass.core.dhtmlx.builder;


import com.apsol.ecopass.core.dhtmlx.model.path.TotalQueryPath;
import com.querydsl.core.types.Expression;

import java.util.HashMap;
import java.util.Map;

public class QueryPathBuilder {
	
	public TotalQueryPath build(Map<String, Expression<?>>	pathMap,
								Map<String, String> 		params,
								String 						filterPrefix,
								String 						sortPrefix 		) {
		
		Map<Expression<?>, String> filterMap = new HashMap<Expression<?>, String>();
		Map<Expression<?>, String> sortMap = new HashMap<Expression<?>, String>();
		
		for (Map.Entry<String, String> param : params.entrySet()) {
			String key = param.getKey();
			String val = param.getValue().trim();
			
			if (key.contains(filterPrefix))
				setReqestMap(key, val, pathMap, filterMap);
			
			else if (key.contains(sortPrefix))
				setReqestMap(key, val, pathMap, sortMap);
		}
		
		return new TotalQueryPath(filterMap, sortMap);
	}
	
	private void setReqestMap(String key, String value, Map<String, Expression<?>> pathMap, Map<Expression<?>, String> requestMap) {
		key = key.substring(key.indexOf("_") + 1);
		Expression<?> exp = pathMap.get(key);
		if (exp == null)
			throw new RuntimeException("params error :: 찾을 수 없는 필드값입니다. " + key);
		
		requestMap.put(exp, value);
	}
	
}
