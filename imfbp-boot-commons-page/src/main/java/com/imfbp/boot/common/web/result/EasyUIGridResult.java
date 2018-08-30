package com.imfbp.boot.common.web.result;

import java.util.List;

import com.imfbp.boot.common.utils.page.PaginatedList;

/**
 * EasyUI中表格的结果集
 * @author quanjianjun
 * @Date 2014年10月31日
 * @Time 下午12:08:35
 */
public class EasyUIGridResult<T> {
	
	private List<T> rows;
	private int total;
	private List<T> footer;
	
	public EasyUIGridResult(PaginatedList<T> rows){
		this.rows=rows;
		this.total=rows.getTotalItem();
		
	}
	
	public EasyUIGridResult(PaginatedList<T> rows,List<T> footer){
		this.rows=rows;
		this.footer = footer;
		this.total=rows.getTotalItem();
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}

}
