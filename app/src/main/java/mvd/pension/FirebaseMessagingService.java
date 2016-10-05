package mvd.pension;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Dmitry on 07.09.2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    private PCalcMessFireBase MessBase;
    private PCalcMessageSQLite pCalcMessageSQLite;


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method be
      //  Mess = new PCalcMessageSQLite(getApplication());
      //  MessBase = new PCalcMessFireBase();
      //  MessBase.setMess1(remoteMessage.getFrom());
      //  MessBase.setMess2(remoteMessage.getNotification().getBody());
       // Mess.insertRun(MessBase);
        insertSQLiteMessage(remoteMessage.getFrom(),remoteMessage.getNotification().getBody());
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }
    // [END receive_message]
    public void insertSQLiteMessage(String mes_1,String mes_2) {
        pCalcMessageSQLite = new PCalcMessageSQLite(this);
        try {
            pCalcMessageSQLite.checkAndCopyDatabase();
            pCalcMessageSQLite.openDataBase();
            ContentValues values = new ContentValues();
            values.put("mess_1", mes_1);
            values.put("mess_2", mes_2);
            pCalcMessageSQLite.Insert(values);
        }
        catch(SQLiteException e) {
            e.printStackTrace();
        }
    }
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, SingleFragmentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.square_inc_cash)
                .setContentTitle("ПенсияСиловиков уведомление")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
