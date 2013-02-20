package tbny.bhn.budget.helper;

import java.math.BigDecimal;

public class Expense {
	private BigDecimal mExpenseValue;
	private String mExpenseName;
	private BudgetDuration mDuration; //total evaluation duration
	
	public Expense(BigDecimal val, String name, BudgetDuration duration) {
		mExpenseValue = new BigDecimal(0);
		mExpenseValue = val;
		mExpenseName = new String(name);
		mDuration = duration;
	}
	
	public BigDecimal getExpenseValue() {
		return mExpenseValue;
	}
	
	public String getExpenseName() {
		return mExpenseName;
	}
	
	public BudgetDuration getExpenseDuration() {
		return mDuration;
	}
}
