package tbny.bhn.budget.helper;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;

public class BudgetHelper extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_helper);
    }

    public void createBudgetClickHandler(View view) {
    	switch (view.getId()) {
    	case R.id.startBudgetCreatorButton: 
    		Intent budgetCreatorIntent = new Intent(this, BudgetCreator.class);
    		startActivity(budgetCreatorIntent);
    		break;
    	}
    }
}
