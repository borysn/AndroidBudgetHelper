package tbny.bhn.budget.helper;

import java.util.List;
import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater; 
import android.view.ViewGroup;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class OverviewFragment extends Fragment {
	private TextView budgetNameView;
	private TextView budgetDurationView;
	private TextView budgetStartDateView;
	//private TextView budgetEndDateView;
	private RelativeLayout mView;
	//private LayoutInflater mInflater;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //View view = inflater.inflate(R.layout.overview_fragment, container, false);//findViewById(R.layout.overview_fragment);
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.overview_fragment, container, false);
        mView = view;
        
        budgetNameView = (TextView) view.findViewById(R.id.budgetName);
        budgetDurationView = (TextView) view.findViewById(R.id.budgetDuration);
        budgetStartDateView = (TextView) view.findViewById(R.id.budgetStartDate);
        //budgetEndDateView = (TextView) view.findViewById(R.id.budgetEndDate);
        
        return view;
    }
   
    public void updateTabValues(String budgetName, String budgetDuration, String startDate) {	
    	budgetNameView.setText(budgetName);
    	budgetDurationView.setText(budgetDuration);
    	budgetStartDateView.setText(startDate);
    	//budgetEndDateView.setText(endDate);
    }
    
    public void updateIncomeTotal(List<Income> incomeList) {
    	if (incomeList != null) {
	    	if (incomeList.size() > 0) {
	    		BigDecimal total = new BigDecimal(0);
	    		for (int i = 0; i < incomeList.size(); i++) {
	    			total = total.add(incomeList.get(i).getIncomeValue());
	    		}
	    		String sTotal = total.toString();
	    		TextView incomeTotal = (TextView) mView.findViewById(R.id.budgetIncomeTotal);
	    		incomeTotal.setText("$" + sTotal);
	    	} else {
	    		TextView incomeTotal = (TextView) mView.findViewById(R.id.budgetIncomeTotal);
	    		incomeTotal.setText(mView.getResources().getString(R.string.addIncomeDialogText));
	    	}
    	}
    }
    
    public void updateExpenseTotal(List<Expense> expenseList) {
    	if (expenseList != null) {
    		if (expenseList.size() > 0) {
    			BigDecimal total = new BigDecimal(0);
	    		for (int i = 0; i < expenseList.size(); i++) {
	    			total = total.add(expenseList.get(i).getExpenseValue());
	    		}
	    		String sTotal = total.toString();
	    		TextView incomeTotal = (TextView) mView.findViewById(R.id.budgetExpenseTotal);
	    		incomeTotal.setText("$" + sTotal);
    		} else {
	    		TextView incomeTotal = (TextView) mView.findViewById(R.id.budgetExpenseTotal);
	    		incomeTotal.setText(mView.getResources().getString(R.string.addExpenseDialogText));
    		}
    	}
    }
}
