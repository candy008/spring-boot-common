package com.imfbp.boot.common.utils.billnumber;

import java.util.List;

public interface BillNumber {
	
	public String getBillNumber(List<BillRuleElement> ruleElements);
}
