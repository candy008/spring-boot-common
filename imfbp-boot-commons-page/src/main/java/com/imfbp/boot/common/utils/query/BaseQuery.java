package com.imfbp.boot.common.utils.query;

import com.imfbp.boot.common.utils.page.PaginatedList;

/**
 * 查询对象
 * @author quanjianjun
 * @Date 2014年10月31日
 * @Time 下午1:23:36
 */
public class BaseQuery implements Query {

	//值
	private String value;
	// 开始行
	private int startRow;
	// 结束行
	private int endRow;
	//排序
	private String sort;
	//当前页
	private int page;
	//每页的页数
	private int rows;
	//排序类型
	private String order;
	//批量的id，用来接收
	private String batchId;
	//每页显示的页数
	private int limit;
	//当前页
	private int offset;
	//数据权限参数
	private Object dataPermit;
	
	public BaseQuery() {
	}

	/**
	 * 初始化出查询对象时为开始行和结束行赋值
	 * @param paginatedList
	 */
	public BaseQuery(PaginatedList<Object> paginatedList) {
		this.startRow = paginatedList.getStartRow();
		this.endRow = paginatedList.getEndRow();
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return this.endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.rows=limit;
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.page=offset;
		this.offset = offset;
	}

	public Object getDataPermit() {
		return dataPermit;
	}

	public void setDataPermit(Object dataPermit) {
		this.dataPermit = dataPermit;
	}
	
	
}