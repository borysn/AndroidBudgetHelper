package tbny.bhn.budget.helper;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater; 
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class AnalysisFragment extends Fragment {
	private Budget mBudget;
	private View mRoot;
	private GridView mCalendarView;
	private MyCalendar mCalendar;
	private ArrayList<RelativeLayout> mListOfDays;
	private ViewGroup mContainer;
	private LayoutInflater mInflater;
	private int mCalendarItemID;
	private MyCalendarAdapter mCalendarAdapter;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.analysis_fragment, container, false);//findViewById(R.layout.overview_fragment);
        
        mRoot = view;
        mCalendarView = (GridView) mRoot.findViewById(R.id.calendar);
        mCalendar = new MyCalendar();
        mListOfDays = new ArrayList<RelativeLayout>();
        mContainer = container;
        mInflater = inflater;
        mCalendarItemID = 99;
        mCalendarAdapter = new MyCalendarAdapter(mCalendarView.getContext(), mBudget.getStartDate().getDate(), mBudget.getBudgetDuration().getTimeSpan().getSpan());
        mCalendarView.setAdapter(mCalendarAdapter);
        
        //populateCalendar();
        
        return view;
    }
    
    /*@Override
    public void onResume() {
		populateCalendar();
    }*/
    
    public void updateTabValues(Budget budget) {
    	mBudget = budget;	
    }
    
    public void populateCalendar() {
    	mCalendar.populateCalendar();
    }
    
    public class MyCalendar {
    	private GridView mCalendarGrid;
    	
    	public MyCalendar() {
    		mCalendarGrid = mCalendarView;
    	}
    	
    	public void populateCalendar() {
    		int span = mBudget.getBudgetDuration().getTimeSpan().getSpan();
    		int date = mBudget.getStartDate().getDate();
    		String [] dayNumbers = new String[mBudget.getBudgetDuration().getTimeSpan().getSpan()];
    		
    		RelativeLayout tempCalendarItem = new RelativeLayout(mCalendarGrid.getContext());
    		
    		/*for (int i = 0; i < span; i ++) { 
    			tempCalendarItem = setCalendarItem(date);
    			mListOfDays.add(tempCalendarItem);
        		dayNumbers[i] = new String(String.valueOf(date));
        		
    			/*
    			 * 
    			 * TO DO:
    			 *  Adjust date for month roll overs. 
    			 *  Add a + operator for MyDate, or a new 
    			 *  function to give a new date from adding 
    			 *  or subtracting days/months/years
    			 *  
    			 *
        		date++;
    			if (date > 31) {
    				date = 1;
    			}
    		}
    				
    		mCalendarAdapter.addItems(mListOfDays);
    		mCalendarAdapter.addDayNumbers(dayNumbers);
    		mCalendarAdapter.notifyDataSetChanged(); */
    	}
    	
    	public RelativeLayout setCalendarItem(int day) {
    		RelativeLayout item = new RelativeLayout(mCalendarGrid.getContext());
    		LayoutInflater inflater = (LayoutInflater) mRoot.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		item = (RelativeLayout)	inflater.inflate(R.layout.calendar_item, null);
    		item.setId(mCalendarItemID);
    		mCalendarItemID++;
    		TextView dateDisplay = (TextView) item.findViewById(R.id.dateDisplay);
    		dateDisplay.setText(String.valueOf(day)); 
    		
    		return item;
    	}
    }
}
