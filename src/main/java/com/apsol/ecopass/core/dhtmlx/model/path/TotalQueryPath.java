package com.apsol.ecopass.core.dhtmlx.model.path;

import com.querydsl.core.types.Expression;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
public class TotalQueryPath {
	
	@Getter
	Map<Expression<?>, String> filterMap;

	@Getter
	Map<Expression<?>, String> sortMap;
	
	public TotalQueryPath	(	Map<Expression<?>, String> filterMap,
							  	Map<Expression<?>, String> sortMap		) {
		this.filterMap = filterMap;
		this.sortMap = sortMap;
	}
	
}
