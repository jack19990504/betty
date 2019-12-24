package com.activity.util;

import java.util.List;

public class Result {
	private Integer total;
	private List<?> source;
	
	public Result(Integer total, List<?> source) {
		this.total = total;
		this.source = source;
	}
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getSource() {
		return source;
	}

	public void setSource(List<?> source) {
		this.source = source;
	}

}