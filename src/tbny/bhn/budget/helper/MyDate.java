package tbny.bhn.budget.helper;

public class MyDate {
	private int mMonth;
	private int mDay;
	private int mYear;
	
	public MyDate(int month, int day, int year) {
		mMonth = month;
		mDay = day;
		mYear = year;
	}
	
	public String toString() {
		return new String(mMonth + "/" + mDay + "/" + mYear);
	}
	
	public int getDate() {
		return mDay;
	}
	
	public int getMonth() {
		return mMonth;
	}
	
	public int getYear() {
		return mYear;
	}
}
