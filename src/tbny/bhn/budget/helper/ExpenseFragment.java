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

public class ExpenseFragment extends Fragment {
	
	private LinearLayout mExpenseListView;
	private View mExpenseItem;
	private List<Expense> mExpenseList;
	private List<LinearLayout> mExpenseListItemViews;
	private LayoutInflater mInflater;
	private View mView;
	private ViewGroup mContainer;
	private Button mAddExpense;
	private Button mDeleteExpense;
	private int mExpenseID;
	private LinearLayout expenseItem;
	private View mExpenseItemSelected;
	//private MyAdapter expenseAdapter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.expense_fragment, container, false);//findViewById(R.layout.overview_fragment);
        mView = view; 
        mContainer = container;
        mInflater = inflater;
        
        //set button click listeners
        mAddExpense = (Button) view.findViewById(R.id.addExpense);
        mAddExpense.setOnClickListener(new addDeleteClickListener());
        mDeleteExpense = (Button) view.findViewById(R.id.deleteExpense);
        mDeleteExpense.setOnClickListener(new addDeleteClickListener());
        mDeleteExpense.setEnabled(false);
        
        //set where the expenses will be added
        mExpenseListView = (LinearLayout) view.findViewById(R.id.expenseList);
        mExpenseItem = inflater.inflate(R.layout.expense_item, container, false);
        mExpenseList = new ArrayList<Expense>();
        mExpenseID = 99;
        mExpenseItemSelected = null;
        mExpenseListItemViews = new ArrayList<LinearLayout>();
       // expenseAdapter = new MyAdapter(view.getContext(), mExpenseList);
        
        return view;
    }
    
    public class addDeleteClickListener implements OnClickListener  {
    	public void onClick(View view) {
	    	switch (view.getId()) {
	    	case R.id.addExpense:
	    		addExpenseDialog();
	    		Toast.makeText(getActivity(), "Adding expense...", Toast.LENGTH_SHORT).show();
	    		break;
	    	case R.id.deleteExpense:
	    		deleteExpenseDialog();
	    		Toast.makeText(getActivity(), "Deleting expense...", Toast.LENGTH_SHORT).show();
	    		break;
	    	}
    	}
    }
    
	public void addExpenseDialog() {
	    Context context = mContainer.getContext();
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.add_expense_dialog);
		dialog.setTitle(R.string.addExpenseDialogText);
		// if button is clicked, close the custom dialog
		Button dialogButton = (Button) dialog.findViewById(R.id.addExpenseButton);// mRoot.findViewById(R.id.continueBudgetCreatorButton);
		dialogButton.setOnClickListener(new OnClickListener() {
		
			public void onClick(View v) { 
				switch (v.getId()) {
				case R.id.addExpenseButton:
					EditText expenseName = (EditText) dialog.findViewById(R.id.expenseName);
					EditText expenseValue = (EditText) dialog.findViewById(R.id.expenseValue);
					String sExpenseName = new String(expenseName.getText().toString());
					String sExpenseValue = new String(expenseValue.getText().toString());
					Spinner spinner = (Spinner) dialog.findViewById(R.id.expenseRecurrence);
					if (sExpenseName.length() > 0) {
						if (spinner.getSelectedItemPosition() > 0) {
							if (sExpenseValue.length() > 0) {
								dialog.dismiss();
								BigDecimal value = new BigDecimal(sExpenseValue);
								BudgetDuration duration = new BudgetDuration(spinner.getSelectedItemPosition());
								Expense expense = new Expense(value, sExpenseName, duration);
								addExpense(expense);
							} else {
								Toast.makeText(dialog.getContext(), "Please set expense value", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(dialog.getContext(), "Please set expense duration", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(dialog.getContext(), "Please enter a expense name", Toast.LENGTH_SHORT).show();
					}
					break;
				case R.id.addExpenseCancleButton:
					dialog.dismiss();
					break;
				}
			}
			
			public void addExpense(Expense expense) {
				mExpenseList.add(expense);//.getExpenseName());
				//mExpenseList.add(expense.getExpenseValue().toString());
				expenseItem = new LinearLayout(mContainer.getContext());
				expenseItem = (LinearLayout) mInflater.inflate(R.layout.expense_item, mContainer, false);
				TextView mExpenseName = (TextView) expenseItem.findViewById(R.id.expenseNameDisplay);
				mExpenseName.setText(expense.getExpenseName());
				TextView mExpenseValue = (TextView)  expenseItem.findViewById(R.id.expenseValueDisplay);
				mExpenseValue.setText(expense.getExpenseValue().toString());
				TextView mExpenseDuration = (TextView) expenseItem.findViewById(R.id.expenseDurationDisplay);
				mExpenseDuration.setText(expense.getExpenseDuration().getTimeSpan().getStringFromCode());
				
				expenseItem.setId(mExpenseID);
				mExpenseID++;
				
				expenseItem.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						if (mExpenseItemSelected != null) {
							mExpenseItemSelected.setBackgroundColor(getResources().getColor(R.color.incomeItemBarColorWhite));
						} 
						view.setBackgroundColor(getResources().getColor(R.color.welcomeTextColor));
						mDeleteExpense.setEnabled(true);
						mExpenseItemSelected = view;
					}
				});
				
				mExpenseListItemViews.add(expenseItem);
				mExpenseListView.post(new Runnable() {
					public void run() {
						mExpenseListView.addView(expenseItem);
					}
				});
			}
		});
		
		//cancel button
		Button cancelButton = (Button) dialog.findViewById(R.id.addExpenseCancleButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	public void deleteExpenseDialog() {
		Context context = getActivity();
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.delete_expense_dialog);
		dialog.setTitle(R.string.deleteExpenseDialogText);
		
		// if button is clicked, close the custom dialog
		Button dialogButton = (Button) dialog.findViewById(R.id.deleteExpenseOkButton);
		dialogButton.setOnClickListener(new OnClickListener() {
		
			public void onClick(View v) { 
				if (mExpenseItemSelected != null) {
					mExpenseListView.removeView(mExpenseItemSelected);
					int index = mExpenseListItemViews.indexOf(mExpenseItemSelected);
					mExpenseListItemViews.remove(mExpenseItemSelected);
					mExpenseList.remove(index);
					
					mExpenseItemSelected = null;
					dialog.dismiss();
					mDeleteExpense.setEnabled(false);
				} 
			}
		});
		
		//cancel button
		Button cancelButton = (Button) dialog.findViewById(R.id.deleteExpenseCancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		 
		dialog.show();
	}
	
	public List<Expense> getExpenseList() {
		return mExpenseList;
	}
}
