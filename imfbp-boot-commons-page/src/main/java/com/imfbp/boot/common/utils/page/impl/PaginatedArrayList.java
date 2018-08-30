package com.imfbp.boot.common.utils.page.impl;

import com.imfbp.boot.common.utils.page.AbstractPaginatedList;

/**
 * 分页类
 * @author quanjianjun
 * @Date 2014年10月31日
 * @Time 下午1:22:15
 * @param <T>
 */
public class PaginatedArrayList<T> extends AbstractPaginatedList<T> {

	public PaginatedArrayList(){
		super();
	}
	
	public PaginatedArrayList(int index, int pageSize){
		super(index,pageSize);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void repaginate() {
		//如果 pasesize 小于页使用默认 pasesize
		if (this.pageSize < 1) {
			this.pageSize = PAGESIZE_DEFAULT;
		}
		
		//如果当前页小于 1 将当前页设置为第一页
		if (this.index < 1) {
			this.index = 1;
		}
		
		
		if (this.totalItem > 0) {
			//计算总页数
			this.totalPage = (this.totalItem / this.pageSize + (this.totalItem % this.pageSize > 0 ? 1 : 0));
			if (this.index > this.totalPage) {
				this.index = this.totalPage;
			}
			//计算开始行
			this.endRow = (this.index * this.pageSize);
			//计算结束行
			this.startRow = (this.endRow - this.pageSize + 1);
			if (this.endRow > this.totalItem)
				this.endRow = this.totalItem;
		}
	}
}
