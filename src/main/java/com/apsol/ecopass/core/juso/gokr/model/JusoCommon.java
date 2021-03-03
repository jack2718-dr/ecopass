package com.apsol.ecopass.core.juso.gokr.model;

import lombok.Data;

@Data
public class JusoCommon {
	 
	private int totalCount;
	private int currentPage;
	private int countPerPage;
	private String errorCode;
	private String errorMessage;
}
