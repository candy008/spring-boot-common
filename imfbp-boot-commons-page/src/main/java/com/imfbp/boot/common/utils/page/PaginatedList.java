package com.imfbp.boot.common.utils.page;

import java.util.List;

/**
 * 分页接口
 * @author quanjianjun
 * @Date 2014年10月31日
 * @Time 下午1:21:35
 * @param <T>
 */
public abstract interface PaginatedList<T> extends List<T> {
	
	/**
	 * 是否不是第一页并且是否是最后一页
	 * @return
	 */
	public abstract boolean isMiddlePage();

	/**
	 * 是否为最后一页
	 * 
	 * @return
	 */
	public abstract boolean isLastPage();

	/**
	 * 是否可以进行下一页
	 * @return
	 */
	public abstract boolean isNextPageAvailable();

	/**
	 * 是否可以上一页
	 * @return
	 */
	public abstract boolean isPreviousPageAvailable();

	/**
	 * 每页显示条数
	 * 
	 * @return
	 */
	public abstract int getPageSize();
	
	/**
	 * 设置每页条数
	 * @param paramInt
	 */
	public abstract void setPageSize(int paramInt);

	/**
	 * 当前页
	 * @return
	 */
	public abstract int getIndex();

	/**
	 * 设置当前页
	 * @param paramInt
	 */
	public abstract void setIndex(int paramInt);

	/**
	 * 总条数
	 * @return
	 */
	public abstract int getTotalItem();

	/**
	 * 设置总条数
	 * @param paramInt
	 */
	public abstract void setTotalItem(int paramInt);

	/**
	 * 总共多少页
	 * @return
	 */
	public abstract int getTotalPage();

	/**
	 * 开始行
	 * @return
	 */
	public abstract int getStartRow();

	/**
	 * 结束行
	 * @return
	 */
	public abstract int getEndRow();

	/**
	 * 下一页
	 * @return
	 */
	public abstract int getNextPage();

	/**
	 * 上一页
	 * @return
	 */
	public abstract int getPreviousPage();

	/**
	 * 是否是第一页
	 * @return
	 */
	public abstract boolean isFirstPage();
}