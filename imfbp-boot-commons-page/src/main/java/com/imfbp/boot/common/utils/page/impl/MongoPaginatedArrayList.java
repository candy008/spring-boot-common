package com.imfbp.boot.common.utils.page.impl;

import com.imfbp.boot.common.utils.page.AbstractPaginatedList;
import com.imfbp.boot.common.utils.query.BaseQuery;


/**
 * mysql 分页类
 * @author quanjianjun
 * @Date 2014年10月31日
 * @Time 下午1:21:58
 * @param <T>
 */
public class MongoPaginatedArrayList<T> extends AbstractPaginatedList<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MongoPaginatedArrayList(){
		super();
	}
	
	public MongoPaginatedArrayList(int index, int pageSize) {
		super(index,pageSize);
	}
	
	public MongoPaginatedArrayList(BaseQuery baseQuery,int totalItem) {
		super(baseQuery, totalItem);
	}

	
	@Override
	public void repaginate() {
		if (pageSize < 1) { //防止程序偷懒，list和分页的混合使用
			pageSize = PAGESIZE_DEFAULT;
		}
		if (index < 1) {
			index = 1;//恢复到第一页
		}
		if (totalItem > 0) {
			totalPage = totalItem / pageSize + (totalItem % pageSize > 0 ? 1 : 0);
			if (index > totalPage) {
				index = totalPage; //最大页
			}

			endRow = index * pageSize;
			startRow = endRow - pageSize;
			endRow = pageSize;

			if (endRow > totalItem) {
				endRow = totalItem;
			}
		}
		if(baseQuery!=null){
			baseQuery.setStartRow(startRow);
			baseQuery.setEndRow(endRow);
		}
	}
	
	public static void main(String[] args) {
//		MysqlPaginatedArrayList<PlatformManagedObject> p = new MysqlPaginatedArrayList<PlatformManagedObject>();
//		
//		int index = 2019;
//		
//		int pageSize = 10;
//		
//		int totalItem = 209;
//		
//		System.out.println(p.repaginate(index,pageSize,totalItem));
	}
	
	
	
}
