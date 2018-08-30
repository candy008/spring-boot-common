package com.imfbp.boot.common.utils;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * StringUtil工具类
 *
 */
public class StringUtil extends StringUtils {

	/**
	 * @Description:Object对象转String
	 * @param object
	 * @return
	 * String
	 * @throws 
	 * @author lihao
	 */
	public static String objectToString(Object object) {
		String str = "";
		if (!isEmpty(object)) {
			str = String.valueOf(object);
		}
		return str;
	}
	
	/**
	 * @Description:判断Object是否为空
	 * @param object
	 * @return
	 * boolean
	 * @throws 
	 * @author lihao
	 */
	public static boolean isEmpty(Object object) {
		if (object == null || object.equals("") || object.equals("null"))
			return true;
		return false;
	}
	
	/**
	 * @Description:判断Object是否非空
	 * @param object
	 * @return
	 * boolean
	 * @throws 
	 * @author lihao
	 */
	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}
	
	/**
	 * @Description::判断字符串是否为空
	 * @param str
	 * @return
	 * boolean
	 * @throws 
	 * @author lihao
	 */
	public static boolean isEmpty(String str){
		return isEmpty((Object)str);
	}
	
	/**
	 * @Description::判断字符串是否非空
	 * @param str
	 * @return
	 * boolean
	 * @throws 
	 * @author lihao
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty((Object)str);
	}
	
	
	/**
	 * @Description:根据pattern把double数据转换成String
	 * @param d
	 * @param pattern 例：pattern = "0.000"，把double保留三位小位且四舍五入
	 * @return
	 * String
	 * @throws 
	 * @author lihao
	 */
	public static String doubleToString(double d, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(d);
	}
}
