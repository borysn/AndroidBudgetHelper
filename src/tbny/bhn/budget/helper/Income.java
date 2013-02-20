package tbny.bhn.budget.helper;

import java.math.BigDecimal;

public class Income {
	private BigDecimal mIncomeValue;
	private String mIncomeName;
	private BudgetDuration mDuration; //total evaluation duration
	
	public Income(BigDecimal val, String name, BudgetDuration duration) {
		mIncomeValue = new BigDecimal(0);
		mIncomeValue = val;
		mIncomeName = new String(name);
		mDuration = duration;
	}
	
	public BigDecimal getIncomeValue() {
		return mIncomeValue;
	}
	
	public String getIncomeName() {
		return mIncomeName;
	}
	
	public BudgetDuration getIncomeDuration() {
		return mDuration;
	}
}
