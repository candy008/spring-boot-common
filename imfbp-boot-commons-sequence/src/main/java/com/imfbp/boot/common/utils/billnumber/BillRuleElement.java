package com.imfbp.boot.common.utils.billnumber;


import java.io.Serializable;

public class BillRuleElement  implements Serializable{

	private static final long serialVersionUID = 1L;

	//主键
	private String id;
	//外键，标志元素属于哪个规则
	private String ruleCode;	
	//时间格式
	private String ruleFormat;
	//元素值
	private String elemValue;
	//元素长度
	private Integer elemLen;
	//元素类型
	private Integer listElemType;	
	//元素类型
	private Integer listReferType;
	//元素顺序
	private Integer elemOrder;
	

	public void setId(String id){
		this.id =  id;
	}
	
	public String getId(){
		return id;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	public String getRuleFormat() {
		return ruleFormat;
	}

	public void setRuleFormat(String ruleFormat) {
		this.ruleFormat = ruleFormat;
	}

	public void setElemValue(String elemValue){
		this.elemValue =  elemValue;
	}
	
	public String getElemValue(){
		return elemValue;
	}

	public void setElemLen(Integer elemLen){
		this.elemLen =  elemLen;
	}
	
	public Integer getElemLen(){
		return elemLen;
	}

	public void setListElemType(Integer listElemType){
		this.listElemType =  listElemType;
	}
	
	public Integer getListElemType(){
		return listElemType;
	}

	public Integer getListReferType() {
		return listReferType;
	}

	public void setListReferType(Integer listReferType) {
		this.listReferType = listReferType;
	}

	public void setElemOrder(Integer elemOrder){
		this.elemOrder =  elemOrder;
	}
	
	public Integer getElemOrder(){
		return elemOrder;
	}

}