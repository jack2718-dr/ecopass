package com.apsol.ecopass.core.juso.gokr.model;

import lombok.Data;

import java.util.List;

@Data
public class JusoResult {

	private JusoCommon common;
	private List<Juso> juso;

}
