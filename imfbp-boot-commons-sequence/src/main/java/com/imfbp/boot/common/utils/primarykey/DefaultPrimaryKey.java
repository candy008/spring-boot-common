package com.imfbp.boot.common.utils.primarykey;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import com.imfbp.boot.common.utils.number.NumberCalculate;
import com.imfbp.boot.common.utils.sequence.SequenceUtil;

/**
 * 实现PrimaryKeyUtil接口，得到主键
 * 
 * @author server
 *
 */
public class DefaultPrimaryKey implements PrimaryKeyUtil {

	private SequenceUtil sequenceUtil;

	private String sysCode;
	private String tenantNum;

	private static final String YEAR_TYPE = "YEAR";
	private static final String MONTH_TYPE = "MONTH";
	private static final String DAY_TYPE = "DAY";

	/**
	 * 归零类型 按年自增 按月自增 按天自增
	 */
	private String zeroTimeType="MONTH";

	@Override
	/**
	 * 得到主键的方法，继承自接口PrimaryKeyUtil
	 */
	public String getPrimaryKey(String tenantNum,String sysCode) {
		// 主键规则是 当前租户id（6位）+系统编码（四位）+当前年月（如201602转换成36进制的四位字符）+流水号（每月清零）
		if(StringUtils.isNotBlank(tenantNum)){
			this.tenantNum = tenantNum;
		}
		
		if(StringUtils.isNotBlank(sysCode)){
			this.sysCode = sysCode;
		}
		
		String sequenceName = getKey();
		
		//主键规则是 系统编码（两位）+当前年月（如201602转换成36进制的四位字符）+流水号 + 随机数（每月清零）
		String curDate = getDateCode();
		String serialNum = "";
		String randomNum = "";

		try {
			// 因为以randomNum + sysCode + curDate为key取流水号，则当月份增加，key为新的，流水号自动从0开始
			serialNum = sequenceUtil.get(sequenceName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String primaryKey = sysCode + curDate + serialNum;
		if (primaryKey.length() < 20) {
			randomNum = NumberCalculate.getRandomString(20 - primaryKey.length());
			primaryKey = randomNum + primaryKey;
		}
		return primaryKey;
	}
	
	
	public static void main(String[] args) {
		NumberCalculate.ten2N("00002N", 36);
	}

	/**
	 * 当前年月日时分秒转换成36进制9位字符 param type
	 * 
	 * @return
	 */
	private String getKey() {

		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String monthStr = month < 10 ? "0" + month : month + "";
		String dayStr = day < 10 ? "0" + day : day + "";

		StringBuffer buffer = new StringBuffer();
		if (YEAR_TYPE.equals(this.zeroTimeType)) {
			buffer.append(year);
		} else if (MONTH_TYPE.equals(this.zeroTimeType)) {
			buffer.append(year);
			buffer.append(monthStr);
		} else if (DAY_TYPE.equals(this.zeroTimeType)) {
			buffer.append(year);
			buffer.append(monthStr);
			buffer.append(dayStr);
		}

		String dateCodes = NumberCalculate.ten2N(buffer.toString(), 36);
		return new String(this.tenantNum+this.sysCode+dateCodes);
	}

	/**
	 * 当前年月日时分秒转换成36进制9位字符
	 * 
	 * @return
	 */
	private String getDateCode() {

		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);// 24小时制
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		String monthStr = month < 10 ? "0" + month : month + "";
		String dayStr = day < 10 ? "0" + day : day + "";
		String hourOfDayStr = hourOfDay < 10 ? "0" + hourOfDay : hourOfDay + "";
		String minuteStr = minute < 10 ? "0" + minute : minute + "";
		String secondStr = second < 10 ? "0" + second : second + "";

		StringBuffer dateBuff = new StringBuffer();
		dateBuff.append(year);
		dateBuff.append(monthStr);
		dateBuff.append(dayStr);
		
		dateBuff.append(hourOfDayStr);
		dateBuff.append(minuteStr);
		dateBuff.append(secondStr);
		String dateCodes = NumberCalculate.ten2N(dateBuff.toString(), 36);
		return new String(dateCodes);
	}

	public void setSequenceUtil(SequenceUtil sequenceUtil) {
		this.sequenceUtil = sequenceUtil;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getTenantNum() {
		return tenantNum;
	}

	public void setTenantNum(String tenantNum) {
		this.tenantNum = tenantNum;
	}

	public String getZeroTimeType() {
		return zeroTimeType;
	}

	public void setZeroTimeType(String zeroTimeType) {
		this.zeroTimeType = zeroTimeType;
	}


	@Override
	public String getPrimaryKey() {
		return getPrimaryKey("000000","00");
		
	}
	
	@Override
	public String getPrimaryKey(String key) {
		return getPrimaryKey(key,"");
		
	}

}
