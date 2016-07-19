package in.eweblabs.careeradvance.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.StaticData.StaticConstant;

/**
 * Created by Anwar on 7/13/2016.
 */
public class CaFmsService extends FirebaseMessagingService {

    private static final String TAG = "CaFmsService";
    public static  int notifyID = 900351;
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
        // message, here is where that should be initiated. See sendNotification method below.

       // Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage.getData());
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(Map<String,String> messageBody) {
        Intent intent = new Intent(this, BaseActivityScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;

        Bitmap notifiyIcon = BitmapFactory.decodeResource(getResources(), R.drawable.notification);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setLargeIcon(notifiyIcon)
                .setContentTitle(messageBody.get(StaticConstant.TITLE))
                .setContentText(messageBody.get(StaticConstant.BODY))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(defaults)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifyID = (int)((new Date().getTime() / 1000L) % Integer.MAX_VALUE) ;
       //  Log.d(TAG, "notifyID: " + notifyID);
        notificationManager.notify(notifyID/* ID of notification */, notificationBuilder.build());
    }
}