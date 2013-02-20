package tbny.bhn.budget.helper;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.lang.Runnable;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater; 
import android.view.ViewGroup;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

public class IncomeFragment extends Fragment {
	
	private LinearLayout mIncomeListView;
	private View mIncomeItem;
	private List<Income> mIncomeList;
	private List<LinearLayout> mIncomeListItemViews;
	private LayoutInflater mInflater;
	private View mView;
	private ViewGroup mContainer;
	private Button mAddIncome;
	private Button mDeleteIncome;
	private int mIncomeID;
	private LinearLayout incomeItem;
	private View mIncomeItemSelected;
	private int mIncomeItemSelectedIndex;
	//private MyAdapter incomeAdapter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.income_fragment, container, false);//findViewById(R.layout.overview_fragment);
        mView = view; 
        mContainer = container;
        mInflater = inflater;
        
        //set button click listeners
        mAddIncome = (Button) view.findViewById(R.id.addIncome);
        mAddIncome.setOnClickListener(new addDeleteClickListener());
        mDeleteIncome = (Button) view.findViewById(R.id.deleteIncome);
        mDeleteIncome.setOnClickListener(new addDeleteClickListener());
        mDeleteIncome.setEnabled(false);
        
        //set where the incomes will be added
        mIncomeListView = (LinearLayout) view.findViewById(R.id.incomeList);
        mIncomeItem = inflater.inflate(R.layout.income_item, container, false);
        mIncomeList = new ArrayList<Income>();
        mIncomeID = 99;
        mIncomeItemSelected = null;
        mIncomeListItemViews = new ArrayList<LinearLayout>();
       // incomeAdapter = new MyAdapter(view.getContext(), mIncomeList);
        
        return view;
    }
    
    public class addDeleteClickListener implements OnClickListener  {
    	public void onClick(View view) {
	    	switch (view.getId()) {
	    	case R.id.addIncome:
	    		addIncomeDialog();
	    		Toast.makeText(getActivity(), "Adding income...", Toast.LENGTH_SHORT).show();
	    		break;
	    	case R.id.deleteIncome:
	    		deleteIncomeDialog();
	    		Toast.makeText(getActivity(), "Deleting income...", Toast.LENGTH_SHORT).show();
	    		break;
	    	}
    	}
    }
    
	public void addIncomeDialog() {
	    Context context = getActivity();
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.add_income_dialog);
		dialog.setTitle(R.string.addIncomeDialogText);
		// if button is clicked, close the custom dialog
		Button dialogButton = (Button) dialog.findViewById(R.id.addIncomeButton);
		dialogButton.setOnClickListener(new OnClickListener() {
		
			public void onClick(View v) { 
				EditText incomeName = (EditText) dialog.findViewById(R.id.incomeName);
				EditText incomeValue = (EditText) dialog.findViewById(R.id.incomeValue);
				String sIncomeName = new String(incomeName.getText().toString());
				String sIncomeValue = new String(incomeValue.getText().toString());
				Spinner spinner = (Spinner) dialog.findViewById(R.id.incomeRecurrence);
				if (sIncomeName.length() > 0) {
					if (spinner.getSelectedItemPosition() > 0) {
						if (sIncomeValue.length() > 0) {
							dialog.dismiss();
							BigDecimal value = new BigDecimal(sIncomeValue);
							BudgetDuration duration = new BudgetDuration(spinner.getSelectedItemPosition());
							Income income = new Income(value, sIncomeName, duration);
							addIncome(income);
						} else {
							Toast.makeText(dialog.getContext(), "Please set income value", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(dialog.getContext(), "Please set income duration", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(dialog.getContext(), "Please enter a income name", Toast.LENGTH_SHORT).show();
				}
			}
			
			public void addIncome(Income income) {
				mIncomeList.add(income);//.getIncomeName());
				//mIncomeList.add(income.getIncomeValue().toString());
				//mIncomeList.add(income.getIncomeDuration().toString());
				incomeItem = new LinearLayout(mContainer.getContext());
				incomeItem = (LinearLayout) mInflater.inflate(R.layout.income_item, mContainer, false);
				TextView mIncomeName = (TextView) incomeItem.findViewById(R.id.incomeNameDisplay);
				mIncomeName.setText(income.getIncomeName());
				TextView mIncomeValue = (TextView)  incomeItem.findViewById(R.id.incomeValueDisplay);
				mIncomeValue.setText(income.getIncomeValue().toString());
				TextView mIncomeDuration = (TextView) incomeItem.findViewById(R.id.incomeDurationDisplay);
				mIncomeDuration.setText(income.getIncomeDuration().getTimeSpan().getStringFromCode());
				
				incomeItem.setId(mIncomeID);
				mIncomeID++;
				
				incomeItem.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						if (mIncomeItemSelected != null) {
							mIncomeItemSelected.setBackgroundColor(getResources().getColor(R.color.incomeItemBarColorWhite));
						} 

						mDeleteIncome.setEnabled(true);
						view.setBackgroundColor(getResources().getColor(R.color.welcomeTextColor));
						mIncomeItemSelected = view;
					}
				});
				
				mIncomeListItemViews.add(incomeItem);
				mIncomeListView.post(new Runnable() {
					public void run() {
						mIncomeListView.addView(incomeItem);
					}
				});
			}
		});
		
		//cancel button
		Button cancelButton = (Button) dialog.findViewById(R.id.addIncomeCancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		 
		dialog.show();
	}
	
	public void deleteIncomeDialog() {
		Context context = getActivity();
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.delete_income_dialog);
		dialog.setTitle(R.string.deleteIncomeDialogText);
		
		// if button is clicked, close the custom dialog
		Button dialogButton = (Button) dialog.findViewById(R.id.deleteIncomeOkButton);
		dialogButton.setOnClickListener(new OnClickListener() {
		
			public void onClick(View v) { 
				if (mIncomeItemSelected != null) {
					mIncomeListView.removeView(mIncomeItemSelected);
					int index = mIncomeListItemViews.indexOf(mIncomeItemSelected);
					mIncomeListItemViews.remove(mIncomeItemSelected);
					mIncomeList.remove(index);
					
					mIncomeItemSelected = null;
					dialog.dismiss();
					mDeleteIncome.setEnabled(false);
				} 
			}
		});
		
		//cancel button
		Button cancelButton = (Button) dialog.findViewById(R.id.deleteIncomeCancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		 
		dialog.show();
		
	}
	
	public List<Income> getIncomeList() {
		return mIncomeList; 
	}
}
