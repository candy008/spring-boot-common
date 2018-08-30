package com.imfbp.boot.common.utils.page;

import java.util.ArrayList;

import com.imfbp.boot.common.utils.page.PaginatedList;
import com.imfbp.boot.common.utils.query.BaseQuery;

/**
 * 分页类
 * @author quanjianjun
 *
 * @param <T>
 */
public abstract class AbstractPaginatedList<T> extends ArrayList<T> implements PaginatedList<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//默认每页显示条数页数
	public static final int PAGESIZE_DEFAULT = 2;
	
	//每页显示条数
	protected int pageSize;
	//当前页
	protected int index;
	//总条数
	protected int totalItem;
	//总页数
	protected int totalPage;
	//开始行
	protected int startRow;
	//结束行
	protected int endRow;
	
	protected BaseQuery baseQuery;

	public AbstractPaginatedList() {
		//计算总页数，开始行结束行
		repaginate();
	}

	public AbstractPaginatedList(int index, int pageSize) {
		this.index = index;
		this.pageSize = pageSize;
		//计算总页数，开始行结束行
		repaginate();
	}
	
	public AbstractPaginatedList(int index, int pageSize,int totalItem) {
		this.index = index;
		this.pageSize = pageSize;
		this.totalItem = totalItem;
		//计算总页数，开始行结束行
		repaginate();
	}
	
	public AbstractPaginatedList(BaseQuery baseQuery,int totalItem) {
		this.baseQuery = baseQuery;
		this.index = baseQuery.getPage();
		this.pageSize = baseQuery.getRows();
		this.totalItem = totalItem;
		//计算总页数，开始行结束行
		repaginate();
	}
 

	public boolean isFirstPage() {
		return this.index <= 1;
	}

	public boolean isMiddlePage() {
		return (!isFirstPage()) && (!isLastPage());
	}

	public boolean isLastPage() {
		return this.index >= this.totalPage;
	}

	public boolean isNextPageAvailable() {
		return !isLastPage();
	}

	public boolean isPreviousPageAvailable() {
		return !isFirstPage();
	}

	public int getNextPage() {
		if (isLastPage()) {
			return this.totalItem;
		}
		return this.index + 1;
	}

	public int getPreviousPage() {
		if (isFirstPage()) {
			return 1;
		}
		return this.index - 1;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		repaginate();
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
		repaginate();
	}

	public int getTotalItem() {
		return this.totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
		repaginate();
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public int getEndRow() {
		return this.endRow;
	}

	
	/**
	 * 计算总页数，开始行结束行
	 */
	public abstract void repaginate();
	
	
}