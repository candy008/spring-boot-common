package com.imfbp.boot.common.utils.query;

/**
 * 查询对象接口
 * @author quanjianjun
 * @Date 2014年10月31日
 * @Time 下午1:23:55
 */
public interface Query {

	
	public String getValue();

	/**
	 * 开始行
	 * @return
	 */
	public int getStartRow();

	/**
	 * 结束行
	 * @return
	 */
	public int getEndRow();
	
	/**
	 * 排序
	 * @return
	 */
	public String getSort();

	/**
	 * 排序类型
	 * @return
	 */
	public String getOrder();
}