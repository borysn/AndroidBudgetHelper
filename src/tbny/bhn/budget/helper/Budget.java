package tbny.bhn.budget.helper;

//import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

public class Budget {
	private List<Income> mIncomeList;
	private List<Expense> mExpenseList;
	private BudgetDuration mDuration;
	private String mName;
	private MyDate mStartDate;
	
	public Budget(String name, int duration, MyDate startDate) {
		mName = name;
		mDuration = new BudgetDuration(duration);
		mIncomeList = new ArrayList<Income>();
		mExpenseList = new ArrayList<Expense>();
		mStartDate = startDate;
	}
	
	public void addIncome(Income income) {
		mIncomeList.add(income);
	}
	
	public void removeIncome(int location) {
		mIncomeList.remove(location);
	}
	
	public List<Income> getIncomeList() {
		return mIncomeList;
	}
	
	public void addExpense(Expense expense) {
		mExpenseList.add(expense);
	}
	
	public void removeExpense(int location) {
		mExpenseList.remove(location);
	}
	
	public List<Expense> getExpenseList() {
		return mExpenseList;
	}
	
	public void changeDuration(int duration) {
		mDuration = new BudgetDuration(duration);
	}
	
	public BudgetDuration getBudgetDuration() {
		return mDuration;
	}
	
	public void changeName(String name) {
		mName = name;
	}
	
	public String getBudgetName() {
		return mName;
	}
	
	public MyDate getStartDate() {
		return mStartDate;
	}
}
