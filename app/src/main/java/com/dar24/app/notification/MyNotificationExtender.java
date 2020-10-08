package com.dar24.app.notification;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dar24.app.R;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class MyNotificationExtender extends NotificationExtenderService {
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
        // Read Properties from result
        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                // Sets the background notification color to Red on Android 5.0+ devices.
                return builder.setSmallIcon(R.drawable.ic_stat_notification);
            }
        };

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        //Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);

        // Return true to stop the notification from displaying
        return false;
    }
}
