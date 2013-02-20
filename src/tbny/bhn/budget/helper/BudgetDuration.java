package tbny.bhn.budget.helper;

import java.util.Date;

public class BudgetDuration {
	private timeSpan mTimeSpan;
	
	public BudgetDuration(int duration) {
		mTimeSpan = timeSpan.notSet;
		mTimeSpan.setCode(duration);
	}
	
	public timeSpan getTimeSpan() {
		return mTimeSpan;	
	}
	
	public enum timeSpan {
        notSet(0), days(1), weeks(2), months(3);
        
        private int code;
        private int mSpan;
        
        private timeSpan(int c) {
            code = c;
            mSpan = 0;
        }
        
        public int getCode() {
            return code;
        }
        
        public void setCode(int c) {
        	code = c;
        }
        
        public void setSpan(int duration) {
        	mSpan = duration;
        }
        
        public int getSpan() {
        	return mSpan;
        }
        
        public String getStringFromCode() {
        	switch (code) {
        	default: 
        	case 0:
        		return mSpan + " Not set";
        	case 1:
        		if (mSpan == 0) {
        			return "This day";
        		}
        		return mSpan + " Day(s)";
        	case 2:
        		return mSpan + " Week(s)";
        	case 3:
        		return mSpan + " Month(s)";
        }
     }
	}
}
