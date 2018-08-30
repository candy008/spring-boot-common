package com.imfbp.boot.common.utils.page.impl;

import com.imfbp.boot.common.utils.page.AbstractPaginatedList;
import com.imfbp.boot.common.utils.query.BaseQuery;


public class OraclePaginatedArrayList<T> extends AbstractPaginatedList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 
	public OraclePaginatedArrayList(){
		super();
	}
	
	public OraclePaginatedArrayList(int index, int pageSize) {
		super(index,pageSize);
	}
	
	public OraclePaginatedArrayList(BaseQuery baseQuery,int totalItem) {
		super(baseQuery, totalItem);
	}

	@Override
	public void repaginate() {
		/*
		 * SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (SELECT * FROM TABLE_NAME)
		 * A WHERE ROWNUM <= 40 ) WHERE RN >= 21
		 */
		if (pageSize < 1) { // 防止程序偷懒，list和分页的混合使用
			pageSize = PAGESIZE_DEFAULT;
		}
		if (index < 1) {
			index = 1;// 恢复到第一页
		}
		if (totalItem > 0) {
			totalPage = totalItem / pageSize
					+ (totalItem % pageSize > 0 ? 1 : 0);
			if (index > totalPage) {
				index = totalPage; // 最大页
			}
			endRow = index * pageSize;

			if (endRow > totalItem) {
				endRow = totalItem;
			}
			startRow = (index - 1) * pageSize + 1;
		}
		if (baseQuery != null) {
			baseQuery.setStartRow(startRow);
			baseQuery.setEndRow(endRow);
		}
	}
	
}
