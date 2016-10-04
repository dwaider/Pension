package mvd.pension;

import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import mvd.pension.FragmentActivity.PCalcPayFragment;

public class PCalcPensMenuActivity extends SingleFragmentActivity {

    protected Fragment createFragment() {
        return new PCalcPayFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                    this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
