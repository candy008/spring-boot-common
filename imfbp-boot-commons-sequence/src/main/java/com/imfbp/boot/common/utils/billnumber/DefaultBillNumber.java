package com.imfbp.boot.common.utils.billnumber;

import java.util.Calendar;
import java.util.List;

import com.imfbp.boot.common.utils.sequence.SequenceUtil;

public class DefaultBillNumber implements BillNumber {
	
	private SequenceUtil sequenceUtil;
	
	public final static int ELEMTYPE_CONST = 1; //常量
	public final static int ELEMTYPE_TIME = 2;//时间类型
	public final static int ELEMTYPE_SERIAL = 3;//流水号
	
	public final static int REFERTYPE_DAY = 1;//按天流水
	public final static int REFERTYPE_MONTH = 2;//按月流水
	public final static int REFERTYPE_YEAR = 3;//按年流水
	

	/**
	 * 得到单据编号，参数为单据规则的元素List，List的顺序必须是elemOrder的顺序
	 */
	@Override
	public String getBillNumber(List<BillRuleElement> ruleElements) {
		String time = "";  //根据流转类型所的到的时间
		String ruleCode = "";//规则编码
		String billNum = "";//最后的单据编号
		Integer elemlen = 0;//元素长度，指的是流水号的元素长度，整个单据编号中有且只有一个流水号。
		
		for(BillRuleElement ruleElement : ruleElements){
			if(ruleCode == ""){
				ruleCode = ruleElement.getRuleCode();
			}
			if(ruleElement.getListElemType() == ELEMTYPE_TIME){
				String date = formatTime(ruleElement.getRuleFormat());
				ruleElement.setElemValue(date);
				time = getTimeByRefer(ruleElement.getListReferType());
			}
			if(ruleElement.getListElemType() == ELEMTYPE_SERIAL){
				elemlen = ruleElement.getElemLen();
			}
		}
		//因为不同的规则，流水号长度不同，所以要在取得seq之后formatNumber
		String seq = sequenceUtil.get(ruleCode + time);
		System.out.println("================业务流水号===========:"+seq+":===================================");
		String serialNum = formatNumber(seq, elemlen); 
		for(BillRuleElement ruleElement : ruleElements){
			if(ruleElement.getListElemType() == ELEMTYPE_SERIAL){
				ruleElement.setElemValue(serialNum);
			}
			billNum += ruleElement.getElemValue();
		}
		
		return billNum;
	}

	private String formatTime(String ruleFormat) {
		
		String result = "";
		String y, m, d;
		Calendar calendar = Calendar.getInstance();      
		int year = calendar.get(Calendar.YEAR);//获取年份
		int month = calendar.get(Calendar.MONTH) + 1;//获取月份，因为月份是0-11，所以要加1
		int day = calendar.get(Calendar.DATE);//获取天
		
		if(ruleFormat.equals("yy")){			
			y = Integer.toString(year); 
			result = y.substring(y.length()-2);
			
		}else if(ruleFormat.equals("yyyy")){
			result = Integer.toString(year); 
			
		}else if(ruleFormat.equals("yyMM")){
			y = Integer.toString(year); 
			m = Integer.toString(month); 
			result = y.substring(y.length()-2) + formatNumber(m, 2);
			
		}else if(ruleFormat.equals("yyyyMM")){
			
			y = Integer.toString(year); 
			m = Integer.toString(month); 
			result = y + formatNumber(m, 2);
			
		}else if(ruleFormat.equals("yyMMdd")){
			
			y = Integer.toString(year); 
			m = Integer.toString(month); 
			d = Integer.toString(day);
			result = y.substring(y.length()-2) + formatNumber(m, 2) + formatNumber(d, 2);
			
		}else if(ruleFormat.equals("yyyyMMdd")){
			
			y = Integer.toString(year); 
			m = Integer.toString(month); 
			d = Integer.toString(day);
			result = y + formatNumber(m, 2) + formatNumber(d, 2);
			
		}

		return result;
	}

	/**
	 * 将数字格式化成字符串
	 * @param seq
	 * @param elemlen
	 * @return
	 */
	private String formatNumber(String seq, Integer elemlen) {
		if(seq.length() == elemlen){
			return seq;
		}else if(seq.length() > elemlen){
			return "";
		}else{
			// 0 代表前面补充0 elemlen 代表长度为4 d 代表参数为正数型     
			String fomular = "%0" + elemlen.toString() + "d";
		    String result = String.format(fomular, Long.parseLong(seq)); 
		    return result;
		}
	}

	private String getTimeByRefer(Integer listReferType) {
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String result = "";
		
		if(listReferType == REFERTYPE_DAY){
			result = Integer.toString(year)+Integer.toString(month)+Integer.toString(day);
		}else if(listReferType == REFERTYPE_MONTH){
			result = Integer.toString(year)+Integer.toString(month);
		}else if(listReferType == REFERTYPE_YEAR){
			result = Integer.toString(year);
		}
		
		return result;
	}

	public void setSequenceUtil(SequenceUtil sequenceUtil) {
		this.sequenceUtil = sequenceUtil;
	}

}
