package com.imfbp.boot.common.web.result;

import java.util.List;

import com.imfbp.boot.common.utils.page.PageInfo;
import com.imfbp.boot.common.utils.query.BaseQuery;

public class ExtGridResult<E> {
	
	private int start;
	private int limit;
	private int pageSize;
	private int total;
	private List<E> rows;

	public ExtGridResult(int start, int limit,int pageSize) {
		this.start=start;
		this.limit=limit;
		this.pageSize=pageSize;
	}
	
	public ExtGridResult() {
		
	}
	
	public ExtGridResult(int start, int limit,int pageSize,int total,List<E> rows) {
		this.start=start;
		this.limit=limit;
		this.pageSize=pageSize;
		this.total=total;
		this.rows=rows;
	}
	public ExtGridResult(PageInfo pageInfo,BaseQuery baseQuery) {
		this.start=pageInfo.getStart();
		this.limit=pageInfo.getLimit();
		this.pageSize=pageInfo.getPageSize();
		baseQuery.setStartRow(pageInfo.getStart());
		baseQuery.setEndRow(pageInfo.getLimit());
		baseQuery.setSort(pageInfo.getSidx());
		baseQuery.setOrder(pageInfo.getSord());
	}
	
	public void addAllList(List<E> dataList){
		this.rows.addAll(dataList);
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStart() {
		return start;
	}

	public int getLimit() {
		return limit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotal() {
		return total;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

}
