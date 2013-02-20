/*
 * 
 * I got a lot of help on this thanks to 
 * http://android.codeandmagic.org/2011/07/android-tabs-with-fragments/
 * 
 * 
 */
package tbny.bhn.budget.helper;

import java.util.List;
/*
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;*/

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
/*import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.DatePicker;
import android.view.View.OnClickListener;
import android.app.Dialog;
import android.content.Context;*/

public class BudgetTabHost extends Fragment implements OnTabChangeListener {

    public static final String TAB_ANALYSIS = "Analysis";
    public static final String TAB_OVERVIEW = "Overview";
    public static final String TAB_INCOME = "Income";
    public static final String TAB_EXPENSE = "Expense";
    public static final String TAB_EDIT = "Edit";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;
	
	//tab fragment objects
	private OverviewFragment overviewTab;
	private IncomeFragment incomeTab;
	private ExpenseFragment expenseTab;
	private AnalysisFragment analysisTab;
	private EditFragment editTab;
	
	//budget values
	private Budget mBudget;
	private BudgetDuration mDuration;
	private String mName;
	private int mNumberOfDuration;
	
	//;dialog stuff
	private BudgetCreatorDialog mDialogs;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//first we are going to create the initial dialog 
		//this dialog will gather some initial information
		//to start building a budget
	
		mRoot = inflater.inflate(R.layout.activity_budget_tab_host, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		//mInitialFieldsSet = false;
		//mDuration = new BudgetDuration(0)
	
		setupTabs();
		
		return mRoot;
	}
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		
		//initialize tab fragment objects
		overviewTab = new OverviewFragment();
		incomeTab = new IncomeFragment();
		expenseTab = new ExpenseFragment();
		analysisTab = new AnalysisFragment();
		editTab = new EditFragment();
		
		//budget dialog stuff
		mDialogs = new BudgetCreatorDialog(mRoot, overviewTab, analysisTab, this);
		
		// manually start loading stuff in the first tab		
		Fragment newFragment = overviewTab;
		updateTab(newFragment, R.id.overviewTab);
	}
    
	private void setupTabs() {
		mTabHost.setup(); // important!
		mTabHost.addTab(newTab(TAB_OVERVIEW, R.string.tab_overview, R.id.overviewTab));
		mTabHost.addTab(newTab(TAB_INCOME, R.string.tab_income, R.id.incomeTab));
		mTabHost.addTab(newTab(TAB_EXPENSE, R.string.tab_expense, R.id.expenseTab));
		mTabHost.addTab(newTab(TAB_ANALYSIS, R.string.tab_analysis, R.id.analysisTab));
		mTabHost.addTab(newTab(TAB_EDIT, R.string.tab_edit, R.id.editTab));
	}

	private TabSpec newTab(String tag, int labelId, int tabContentId) {
		View indicator = LayoutInflater.from(getActivity()).inflate(
				R.layout.tab,
				(ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
		((TextView) indicator.findViewById(R.id.text)).setText(labelId);

		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}
    
	public void onTabChanged(String tabId) {
		Fragment newFragment;
    	if (TAB_OVERVIEW.equals(tabId)){
    		mCurrentTab = 0; 
    		newFragment = overviewTab;
    		List<Income> incomeList = incomeTab.getIncomeList();
    		List<Expense> expenseList = expenseTab.getExpenseList();
    		overviewTab.updateIncomeTotal(incomeList);
    		overviewTab.updateExpenseTotal(expenseList);
    		updateTab(newFragment, R.id.overviewTab);
    	} else if (TAB_INCOME.equals(tabId)) {
    		mCurrentTab = 1; 
    		newFragment = incomeTab;
    		updateTab(newFragment, R.id.incomeTab);
    	} else if (TAB_EXPENSE.equals(tabId)) {
    		mCurrentTab = 2;
    		newFragment = expenseTab;
    		updateTab(newFragment, R.id.expenseTab);
    	} else if (TAB_ANALYSIS.equals(tabId)) {
    		mCurrentTab = 3;
    		newFragment = analysisTab;
    		updateTab(newFragment, R.id.analysisTab);
    		analysisTab.updateTabValues(mBudget);
    	} else if (TAB_EDIT.equals(tabId)) {
    		mCurrentTab = 4; 
    		newFragment = editTab;
    		updateTab(newFragment, R.id.editTab);
    	}
	}

	private void updateTab(Fragment fragment, int id) {
		FragmentManager fm = getFragmentManager();
		if (fm.findFragmentById(id) == null) {
			fm.beginTransaction().add(id, fragment).commit();
		}
	}
	
	public void setBudget(Budget budget) {
		mBudget = budget;
	}
}
