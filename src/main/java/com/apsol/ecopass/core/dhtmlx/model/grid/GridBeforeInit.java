package com.apsol.ecopass.core.dhtmlx.model.grid;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "beforeInit")
public class GridBeforeInit {

	public void setCalls(List<GridCall> calls) {
		this.calls = calls;
	}

	@XmlElement(name = "call")
	public List<GridCall> getCalls() {
		return calls;
	}

	public GridBeforeInit(){}

	public GridCall addCall(GridCall call){
		if( calls == null )
			calls = new ArrayList<>();

		calls.add(call);

		return call;
	}

	private List<GridCall> calls;
}

