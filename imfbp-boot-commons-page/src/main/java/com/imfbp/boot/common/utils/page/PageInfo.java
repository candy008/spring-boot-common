package com.imfbp.boot.common.utils.page;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author quanjianjun
 *
 */
@JsonIgnoreProperties({"page", "pageSize", "sortname", "sortorder"})
public class PageInfo {
	
	/**
	 * 当前页
	 */
	private int page = 1;
	
	private int pageSize = 0;

	/**
	 * 排序名称
	 */
	private String sidx ;
	
	/**
	 * 排序类型
	 */
	private String sord;
	
	private int limit;
	
	private int start;
	
	/**
	 * 每页显示的数量
	 */
	private int rows;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}

