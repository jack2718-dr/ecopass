package com.apsol.ecopass.core.dhtmlx.model.path;

import com.querydsl.core.types.Expression;

public interface DataPath {

	Expression<?> getExp();

	void setExp(Expression<?> exp);

	String getId();
	
	void setId(String id);

}
