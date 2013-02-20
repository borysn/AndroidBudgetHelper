package tbny.bhn.budget.helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater; 
import android.view.ViewGroup;
import android.view.View;

public class EditFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.edit_fragment, container, false);//findViewById(R.layout.overview_fragment);
        return view;
    	/*EditText v = new EditText(getActivity());
        v.setText("Overview!");
        return v;*/
    }
   
}
