package mvd.pension;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

public class PCalcSplashActivity extends AppCompatActivity {
	/** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
           //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_pens);
        mContext = this;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
             /* Create an Intent that will start the Menu-Activity. */
                 Intent mainIntent = new Intent(PCalcSplashActivity.this,PCalcPensActivity.class);
                 PCalcSplashActivity.this.startActivity(mainIntent);
                 PCalcSplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

	}

}
