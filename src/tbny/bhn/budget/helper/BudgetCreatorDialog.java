package tbny.bhn.budget.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BudgetCreatorDialog {
	//dialog stuff
	private Dialog stepOneDialog;
	private Dialog stepTwoDialog;
	private BudgetDuration mDuration;
	private View mRoot;
	private String mName;
	//private MyDate [] mStartAndEndDates;
	private OverviewFragment mOverviewTab;
	private AnalysisFragment mAnalysisTab;
	private Budget mBudget;
	private BudgetTabHost mHost;
	
	public BudgetCreatorDialog(View root, OverviewFragment overviewTab, AnalysisFragment analysisTab, BudgetTabHost host) {
		mRoot = root;
		mName = new String();
		mOverviewTab = overviewTab;
		mAnalysisTab = analysisTab;
		mHost = host;
		
		//initial dialog stuff
		stepOneDialog = new Dialog(mRoot.getContext());
		stepOneDialog.setContentView(R.layout.budge_creator_step1_dialog);
		stepOneDialog.setTitle(R.string.budgetCreatorDialogText);
		stepOneDialog.setCancelable(true);
		
		stepTwoDialog = new Dialog(mRoot.getContext());
		stepTwoDialog.setContentView(R.layout.budget_creator_step2_dialog);
		stepTwoDialog.setTitle(R.string.budgetCreatorDialogText);
		stepTwoDialog.setCancelable(true);
		
		initialBudgetDialog();
		continueBudgetDialog();
		startInitialDialog();
	}
	
	private void startInitialDialog() {
		stepOneDialog.show();
	}
	
	private void initialBudgetDialog() {
		//Context context = getActivity();
		// if button is clicked, close the custom dialog
		Button dialogButton = (Button) stepOneDialog.findViewById(R.id.continueBudgetCreatorButton);// mRoot.findViewById(R.id.continueBudgetCreatorButton);
		
	/*	if (mInitialFieldsSet) {
			EditText budgetName = new EditText(stepOneDialog.getContext());
			EditText budgetDuration = new EditText(stepOneDialog.getContext());
			Spinner spinner = (Spinner) stepOneDialog.findViewById(R.id.budgetDuration);

			budgetName.setText(mName);
			budgetDuration.setText(mNumberOfDuration);
			spinner.setSelection(mDuration.getTimeSpan().getCode());
		} */
		
		dialogButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
//				else {
				EditText budgetName = (EditText) stepOneDialog.findViewById(R.id.budgetName);
				EditText budgetSpan = (EditText) stepOneDialog.findViewById(R.id.budgetSpan);
				Spinner spinner = (Spinner) stepOneDialog.findViewById(R.id.budgetType);
				//}	

				String sBudgetName = new String(budgetName.getText().toString());
				//String sBudgetDuration = new String(BudgetDuration.getText().toString());
				
				int span = Integer.parseInt(budgetSpan.getText().toString());
				mDuration = new BudgetDuration(spinner.getSelectedItemPosition());
				mDuration.getTimeSpan().setSpan(span);
				mName = new String(sBudgetName);
				//mNumberOfDuration = Integer.parseInt(sBudgetDuration);
				
				if (mName.length() > 0) {
					if (mDuration.getTimeSpan().getCode() > 0) {	
						//Toast.makeText(mRoot.getContext(), "BudgetDuration = " + spinner.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
						//stepOneDialog.dismiss();
						stepOneDialog.hide();
						//removeDialog()
						stepTwoDialog.show();
					} else {
						Toast.makeText(mRoot.getContext(), "Please set budget duration", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(mRoot.getContext(), "Please enter a budget name", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void continueBudgetDialog() {
		//Context context = getActivity();
		
		// if button is clicked, close the custom dialog
		Button dialogButton = (Button) stepTwoDialog.findViewById(R.id.continueBudgetCreatorButton);
		Button backDialogButton = (Button) stepTwoDialog.findViewById(R.id.backBudgetCreatorButton);
		
		backDialogButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				stepTwoDialog.hide();
				stepOneDialog.show();
			}
		});	
		
		dialogButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { 
				//mStartAndEndDates = new MyDate[2];
				DatePicker startDatePicker = (DatePicker) stepTwoDialog.findViewById(R.id.budgetStartDatePicker);
				//DatePicker endDatePicker = (DatePicker) stepTwoDialog.findViewById(R.id.budgetEndDatePicker);
				
				//SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
				
				//Date startDate = new Date(startDatePicker.getYear() - 1900, startDatePicker.getMonth(), startDatePicker.getDayOfMonth());
				MyDate startDate = new MyDate(startDatePicker.getMonth(), startDatePicker.getDayOfMonth(), startDatePicker.getYear());
				/*try {
					startDate = format.parse(startDatePicker.get);
				} catch (ParseException e) {
					throw new IllegalArgumentException();
				}*/
				//Date endDate = new Date(endDatePicker.getYear() - 1900, endDatePicker.getMonth(), endDatePicker.getDayOfMonth());
				/*MyDate endDate = new MyDate(endDatePicker.getMonth(), endDatePicker.getDayOfMonth(), endDatePicker.getYear());
				try {
					endDate = format.parse(endDatePicker.toString());
				} catch (ParseException e) {
					throw new IllegalArgumentException();
				}*/
				
				//set duration
				/*switch (mDuration.getTimeSpan().getCode()) {
				case 1:
					mDuration.getTimeSpan().setDuration(endDate.getDate() - startDate.getDate());
					break;
				case 2:
					mDuration.getTimeSpan().setDuration((endDate.getDate() - startDate.getDate()) / 7);
					break;
				case 3:
					mDuration.getTimeSpan().setDuration(endDate.getMonth() - startDate.getMonth());
					break;
				case 4:
					mDuration.getTimeSpan().setDuration((endDate.getYear() - startDate.getYear()) / 12);
					break;
				}
				
				if (validateDate(startDate, endDate)) {
					mStartAndEndDates[0] = startDate;
					mStartAndEndDates[1] = endDate;*/
					stepTwoDialog.dismiss();
					stepOneDialog.dismiss();
					mOverviewTab.updateTabValues(mName, mDuration.getTimeSpan().getStringFromCode(), 
							startDate.toString());
					//mAnalysisTab.populateCalendar();
					mBudget = new Budget(mName, mDuration.getTimeSpan().getCode(), startDate);
					mHost.setBudget(mBudget);
				/*} else {
					//Toast.makeText(mRoot.getContext(), "Please choose a different end date", Toast.LENGTH_SHORT).show();
				}*/
			}
		});
		 
	}
	
	/*public boolean validateDate(MyDate start, MyDate end) {
		switch (mDuration.getTimeSpan().getCode()) {
		case 1:
			//start date and end date must have a difference of 1-6 (same day == 0, + 6 days == 5)
			if ((end.getDate() - start.getDate() >= 0) && (end.getDate() - start.getDate() <= 6)) {
				return true;
			} else {
				Toast.makeText(mRoot.getContext(), "You chose to make a daily budget. Adjust your dates, or go back and adjust your budget duration.", Toast.LENGTH_LONG).show();
			}
			break;
		case 2:
			//start date and end date must have a difference of > 7
			if ((end.getDate() - start.getDate() >= 7) && (end.getDate() - start.getDate() <= 31)) {
				return true;
			} else {
				Toast.makeText(mRoot.getContext(), "You chose to make a Weekly budget. Adjust your dates, or go back and adjust your budget duration.", Toast.LENGTH_LONG).show();
			}
			break;
		case 3:
			//start date and end date must have a difference of > xdays in month
			if ((end.getMonth() - start.getMonth() >= 0) && (end.getMonth() - start.getMonth() <= 12)) {
				return true;
			} else {
				Toast.makeText(mRoot.getContext(), "You chose to make a Monthly budget. Adjust your dates, or go back and adjust your budget duration.", Toast.LENGTH_LONG).show();
			}
			break;
		case 4:
			//start date and end date must have a difference of > 365 days
			if ((end.getYear() - start.getYear()) > 0) {
				return true;
			} else {
				if ((end.getMonth() - start.getMonth()) >= 12) {
					return true;
				}
				Toast.makeText(mRoot.getContext(), "You chose to make a Yearly budget. Adjust your dates, or go back and adjust your budget duration.", Toast.LENGTH_LONG).show();
			}
			break;
		}
		return false;
	}
	
	public Budget getBudget() {
		return mBudget;
	}*/
}
