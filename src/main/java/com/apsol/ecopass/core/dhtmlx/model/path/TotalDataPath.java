package com.apsol.ecopass.core.dhtmlx.model.path;

import com.querydsl.core.types.Expression;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
public class TotalDataPath {
	
	@Getter
	private List<String> frontIds;
	
	@Getter
	private List<String> backIds;
	
	@Getter
	private Integer	posStart;
	
	@Getter
	private Integer	count;
	
	@Getter
	private Map<String, Expression<?>> pathMap;
	
	public TotalDataPath(	List<String> 				frontIds,
							List<String> 				backIds,
							Integer						posStart,
							Integer						count,
							Map<String, Expression<?>>	pathMap		) {
		
		this.frontIds = frontIds;
		this.backIds = backIds;
		this.posStart = posStart;
		this.count = count;
		this.pathMap = pathMap;
	}
	
}
