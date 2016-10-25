package mvd.pension;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.util.IabHelper;
import com.util.IabResult;
import com.util.Inventory;
import com.util.Purchase;

import mvd.pension.Core.PCalc;
import mvd.pension.FragmentActivity.PCalcPayFragment;
import mvd.pension.FragmentActivity.PCalcPensHelpFragment;
import mvd.pension.FragmentActivity.PCalcPensMessageFragment;
import mvd.pension.FragmentActivity.PCalcPensTabPager;

public abstract class SingleFragmentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	protected abstract Fragment createFragment();
	private static final String TAG = "myLogsPayPension";

	private String title;
	//покупки в надбавках
	IabHelper mHelper;
	int RC_REQUEST = 10001;
	String id_pens_1 = "id_pens_1";
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
	// Слушатель для востановителя покупок.
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener;

	private FloatingActionButton fab1;
	private Animation fab_1_show;
	private PCalc pens;

	private void InstPay() {
		// TODO Auto-generated method stub
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA30VXRRfhvaGyg/ClKwLdaUAz5Umfd2xL2IA6vh0fCjSuIlIDcvOa7OfUjUGFK0lHnvUB2CGm6tBHM3JC9esCkUiFniC9VbGRAzz8X6ToEBQ97g0f2nfzYBM9byoANwvSEKB9xBBweBjKDbU2TbbUdtcpONdoPClyEQeDYRDeAvYYtO0xF/Cdkog7SHKWIz0/T8mHBEqd7t7km3eUz9YDcFlNrtY3JdAO5Mz/wOfDpNYaEBwIZMAdE2N92oqhdedNENFafe+3vuGLRU0seGuoe8msSbNUGTMqOWhHkh52bBmyNF4wPIAFnadWfg+Mh5JEMzT9WziDU7s5gwx3rIGYwQIDAQAB";
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		Log.d(TAG, "mHelper = new IabHelper(getApplicationContext(), base64EncodedPublicKey);");
		mHelper.enableDebugLogging(true);
		Log.d(TAG, "mHelper.enableDebugLogging(true);");
		mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {

			private static final String TAG = "QueryInventoryFinishedListener";

			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				// TODO Auto-generated method stub
				//LOG.d(TAG, "Query inventory finished.");
				if (result.isFailure()) {
					//LOG.d(TAG, "Failed to query inventory: " + result);
					return;
				}
						/*
						 * Проверяются покупки.
						 * Обратите внимание, что надо проверить каждую покупку, чтобы убедиться, что всё норм!
						 * см. verifyDeveloperPayload().
						 */
				if (result.isSuccess()) {
					Purchase purchase = inv.getPurchase(id_pens_1);
					//если  покупка была уже куплена
					if (purchase != null) {
						pens.setBay_save_and_nadbav(true);
						//pBay_save_and_nadbav = true;
						//RashetAll();
					}
				}
			}
		};
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {

			@Override
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess())
				{
					// Произошла ошибка авторизации библиотеки
					Log.d(TAG, "mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener()");
					return;
				}
				// чекаем уже купленное
				if (result.isSuccess())
				{
					mHelper.queryInventoryAsync(mGotInventoryListener);
				};
				Log.d(TAG, result.getMessage());
			}
		});

		mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
			@Override
			public void onIabPurchaseFinished(IabResult result,
											  Purchase purchase) {
				// TODO Auto-generated method stub
				Log.d(TAG, "IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener");
				if (mHelper == null) return;
				if (result.isFailure()) {
					return;
				}
				if (!verifyDeveloperPayload(purchase)) {
					return;
				}
				if (result.isSuccess()) {
					if (purchase.getSku().equals(id_pens_1)) {
						//pBay_save_and_nadbav = true; //покупка совершена
						pens.setBay_save_and_nadbav(true);
						return;
					}
				}
			}
		};
	}

	boolean verifyDeveloperPayload(Purchase p) {
		String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

		return true;
	}

	public void bayPay() {
		// TODO Auto-generated method stub
		// Объект Хелпера для взаимодействия с биллингом.
		try {
			mHelper.launchPurchaseFlow(this, id_pens_1 , RC_REQUEST, mPurchaseFinishedListener, "");
			Log.d(TAG, "mHelper.launchPurchaseFlow(this, ID_DAR_1 , RC_REQUEST, mPurchaseFinishedListener, );");

		} catch (Exception e) {
			// TODO: handle exception

			Log.d(TAG, this.getLocalClassName()+' '+e.getLocalizedMessage());
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		try
		{

			if (!mHelper.handleActivityResult(requestCode, resultCode, data))
			{
				super.onActivityResult(requestCode, resultCode, data);
			}
		} catch (Exception exception)
		{
			super.onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	protected void onDestroy() {
		if (mHelper != null) {
			mHelper.dispose();
			mHelper = null;
		}
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pcalc_splash);
		if (getIntent().getExtras() != null) {
			for (String key : getIntent().getExtras().keySet()) {
				String value = getIntent().getExtras().getString(key);
			}
		}
		pens = PCalc.get(this);
		InstPay();
		pens.setTestBay(new PCalc.testBay() {
			@Override
			public void onPensBay() {
				bayPay();
			}
		});

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

		fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
		fab_1_show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab1_show);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Snackbar.make(view, "ВАША ПЕНСИЯ "+String.format("%.2f",pens.getpItogSum()), Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
				if (getIntent().getBooleanExtra("bol_mes",false) == true) {//получаем getIntent
					fragment = new PCalcPensMessageFragment();
					title = getString(R.string.menu_message);
				    getSupportActionBar().setTitle(title);
				} else {
					fragment = createFragment();
					title = getString(R.string.menu_pensia_calc);
				    getSupportActionBar().setTitle(title);
				}
			fm.beginTransaction()
			.add(R.id.fragmentContainer, fragment)
			.commit();
		}
		else
		{
//			if (f)
		}
	}



	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pcalc_splash, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("name_title", title);
	//	Log.d(LOG_TAG, "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		title = savedInstanceState.getString("name_title");
		getSupportActionBar().setTitle(title);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		Fragment fragment = null;
		int id = item.getItemId();
			if (id == R.id.nav_calc_pens) {
				fragment = new PCalcPensTabPager();
				title = getString(R.string.menu_pensia_calc);
			} else if (id == R.id.nav_spr) {
				fragment = new PCalcPensHelpFragment();
				title = getString(R.string.menu_spr_pensia);
			} else if (id == R.id.nav_dopl) {
				fragment = new PCalcPayFragment();
				title = getString(R.string.menu_setting_dopol);
			} else if (id == R.id.nav_send) {
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = getResources().getString(R.string.mvd_pension_string_send_share_body);
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,getResources().getString(R.string.mvd_pension_string_send_share_subject));
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.mvd_pension_string_send_share_zago)));
			} else if (id == R.id.nav_rate) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.mvd_pension_string_send_share_body))));
			} else if (id == R.id.nav_messange)
			{
				fragment = new PCalcPensMessageFragment();
				title = getString(R.string.menu_message);
			}
			if (fragment != null) {
				FragmentManager fm = getSupportFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				transaction.replace(R.id.fragmentContainer, fragment);
				transaction.commit();
				getSupportActionBar().setTitle(title);
			}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
