package mvd.pension;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitry on 09.09.2016.
 */
public class PCalcMessageSQLite extends SQLiteOpenHelper {
    private static PCalcMessageSQLite pCalcMessageSQLite;
    private static String DB_NAME = "pension_bd.sqlite";
    private static String DB_PATH = "";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private static final int VERSION = 1;
    private static final String TABLE_MESSAGE = "message";
    private static final String COLUMN_RUN_MESS_1 = "mess_1";
    private static final String COLUMN_RUN_MESS_2 = "mess_2";

    private OnInsertListern onInsertListern;

    public static PCalcMessageSQLite get(Context context) {
        if (pCalcMessageSQLite == null) {
            pCalcMessageSQLite = new PCalcMessageSQLite(context);
            pCalcMessageSQLite.checkAndCopyDatabase();
            pCalcMessageSQLite.openDataBase();
        }
        return  pCalcMessageSQLite;
    }

    private PCalcMessageSQLite(Context context) {
        super(context, DB_NAME, null, VERSION);
        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = Environment.getDataDirectory() +"/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    private void openDataBase(){
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void checkAndCopyDatabase(){
        boolean dbExist = checkDatbase();
        if (dbExist) {
            Log.d("TAG", "databases alreade exists");
        } else {
            this.getReadableDatabase();
            try {
                copyDatabases();
            } catch (IOException e) {
                Log.d("TAG", "error copy data databases");
            }
        }
    }

    public void copyDatabases() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileNew = DB_PATH + DB_NAME;
        OutputStream myOutputStream = new FileOutputStream(outFileNew);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutputStream.write(buffer,0,length);
        }
        myOutputStream.flush();
        myOutputStream.close();
        myInput.close();
    }

    public void insertSQLiteMessage(String mes_1,String mes_2) {
        try {
            ContentValues values = new ContentValues();
            values.put("mess_1", getDateNow());//подставляем дату
            values.put("mess_2", mes_2);
            pCalcMessageSQLite.Insert(values);
            Cursor cursor = QueryData("select * from message");
            cursor.moveToLast();
            if (onInsertListern != null)  onInsertListern.OnInsertData(cursor);
        }
        catch(SQLiteException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private String getDateNow() {
        String dt;
        Date cal = Calendar.getInstance().getTime();
        dt = cal.toLocaleString();
        return  dt.toString();
    }

    public int deleteId(int id) {
        return myDataBase.delete("message", "_id = " + id, null);
    }

    public Cursor QueryData(String query) {
        return myDataBase.rawQuery(query,null);
    }

    public void Insert(ContentValues values) {
        myDataBase.insert(TABLE_MESSAGE,null,values);
    }

    public boolean checkDatbase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }
        catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    public void setOnInsertListern(OnInsertListern onInsertListern) {
        this.onInsertListern = onInsertListern;
    }
}
