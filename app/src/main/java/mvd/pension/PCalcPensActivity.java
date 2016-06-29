package mvd.pension;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class PCalcPensActivity extends SingleFragmentActivity {

    protected Fragment createFragment() {
        return new PCalcPensTabPager();
    }
}
