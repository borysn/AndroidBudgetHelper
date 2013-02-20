package tbny.bhn.budget.helper;

import java.util.ArrayList;

import android.widget.RelativeLayout;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Context;

public class MyCalendarAdapter extends BaseAdapter {
	private ArrayList<RelativeLayout> mCalendarItems;
	private Context mContext;
	private String [] mDayNumbers;
	private int mStartDay;
	private int mSpan;
	private int mID;
	
	public MyCalendarAdapter(Context c, int startDay, int spanDays) {
		mCalendarItems = new ArrayList<RelativeLayout>();
		mContext = c;
		mID = 0x99c;
		mDayNumbers = new String[spanDays];
		mStartDay = startDay;
		mSpan = spanDays;

	}
	
	/*public void addItems(ArrayList<RelativeLayout> items) {
		for (int i = 0; i < mSpan; i++) {
			mCalendarItems.add(i, items.get(i));
		}
	}
	
	public void addDayNumbers(String [] items) {
		for (int i = 0; i < mSpan; i++) {
			mDayNumbers[i] = items[i];
		}
	}*/
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		TextView dayNumber;
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.calendar_item, null);
			view = convertView;
			view.setId(mID);
			mID++;
			
			dayNumber = (TextView) view.findViewById(R.id.dateDisplay);
			dayNumber.setText(String.valueOf(mStartDay));
			
			mStartDay++;
			
			if (mStartDay > 31) {
				mStartDay = 1;
			}
		} else {
			view = convertView;
		}
		
		//view.setBackgroundColor(view.getResources().getColor(R.color.incomeItemBarColor));
		
		return view;
	}
	
	public Object getItem(int postion) {
		return null;
	}
	
	public int getCount() {
		return mSpan;
	}
	
	public long getItemId(int position) {
		return 0;
	}
}