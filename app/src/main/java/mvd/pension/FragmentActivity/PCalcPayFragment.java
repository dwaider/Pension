package mvd.pension.FragmentActivity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mvd.pension.Core.PCalc;
import mvd.pension.R;

public class PCalcPayFragment extends Fragment {
	private PCalc pens;
	private Button btPayAutoSaveNadbavk;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setHasOptionsMenu(true);
      pens = PCalc.get(getActivity());
	}
	
 
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_pcalc_pay, parent, false);
		//getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		btPayAutoSaveNadbavk = (Button) v.findViewById(R.id.btPayAutoSaveNadbavki);
		if (!pens.ispBay_save_and_nadbav()) {
			btPayAutoSaveNadbavk.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					pens.notifyListernBay();
				}
			});
		}
		else
			{
				btPayAutoSaveNadbavk.setCompoundDrawablesWithIntrinsicBounds(R.drawable.unlock,0, 0, 0);
				btPayAutoSaveNadbavk.setOnClickListener(null);
			}
/*		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			ActionBar actionBar = getActivity().getActionBar();
			actionBar.setSubtitle(getResources().getString(R.string.pcalc_pod_zago_pay));
		}*/
		return v;
	}

}
