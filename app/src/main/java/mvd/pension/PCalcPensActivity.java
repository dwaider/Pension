package mvd.pension;

import android.support.v4.app.Fragment;

import mvd.pension.FragmentActivity.PCalcPayFragment;
import mvd.pension.FragmentActivity.PCalcPensTabPager;

public class PCalcPensActivity extends SingleFragmentActivity {

    protected Fragment createFragment() {
        return  new PCalcPensTabPager();
    }
}
