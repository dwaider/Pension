package mvd.pension;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

//заставка
public class PCalcSplashActivity extends AppCompatActivity {
	/** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private Context mContext;
    private String mes = null; //проверка пришло ли новое сообшение от FireBase
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
           //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_pens);
        //обработка сообщений от Notification FireBase отправляется сообщение с параметром mess_1 в консоле FireBase
        //нужно отправлять через расширенные параметры сообщения используя mess_1
        mes = getIntent().getStringExtra("mess_1");
        if (mes != null) {//если не пустой записываем в БД
            PCalcMessageSQLite.get(this).insertSQLiteMessage("",mes);
        }
        mContext = this;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
             /* Create an Intent that will start the Menu-Activity. */
                 Intent mainIntent = new Intent(PCalcSplashActivity.this,PCalcPensActivity.class);
                 if (mes != null) {
                     mainIntent.putExtra("bol_mes",true); //передаем в PCalcPensActivity true при вызове интента что бы открыть сразу фрагмент с сообщениями
                 }
                 PCalcSplashActivity.this.startActivity(mainIntent);
                 PCalcSplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

	}
}
