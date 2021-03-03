package com.apsol.ecopass.core.dhtmlx.model.path;

import com.querydsl.core.types.Expression;

public class FrontPath implements DataPath {
	
	private Expression<?> exp;

	private String id;

	public FrontPath(Expression<?> exp, String id) {
		this.exp = exp;
		this.id = id;
	}

	@Override
	public Expression<?> getExp() {
		return this.exp;
	}

	@Override
	public void setExp(Expression<?> exp) {
		this.exp = exp;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
