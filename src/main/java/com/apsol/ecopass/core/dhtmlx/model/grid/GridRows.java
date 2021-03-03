package com.apsol.ecopass.core.dhtmlx.model.grid;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rows")
public class GridRows {

	public GridRows(){}

	@XmlElement(name = "head")
	public GridHead getHead() {
		return head;
	}

	public void setHead(GridHead head) {
		this.head = head;
	}

	private GridHead head = new GridHead();
}
