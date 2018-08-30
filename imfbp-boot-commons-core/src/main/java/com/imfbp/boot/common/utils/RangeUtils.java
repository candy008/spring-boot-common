package com.imfbp.boot.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author caohonghua
 *
 * @description 获取一个区间段
 */
public class RangeUtils {

	/**
	 * 
	 * @description 通过两个数值，返回数值间的字段
	 *
	 * @param begin区间的开始值
	 * @param end区间的结束值
	 * 
	 * @return 返回一个区间段
	 */
	public static List<Map<String, Object>> getRange(int begin, int end) {
		List<Map<String, Object>> list = null;
		if (begin <= end) {
			list = new ArrayList<Map<String, Object>>();
			int temp = end;
			for (int i = 0; i <= end - begin; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("value", i);
				map.put("text", temp--);
				list.add(map);
			}
		}
		return list;
	}
}
