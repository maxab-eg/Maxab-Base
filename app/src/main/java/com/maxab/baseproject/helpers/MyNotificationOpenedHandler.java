package com.maxab.baseproject.helpers;

import android.app.Application;
import android.content.Intent;
import com.itsmart.baseproject.MainActivity;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

public class MyNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {

    private Application application;

    public MyNotificationOpenedHandler(Application application) {
        this.application = application;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        startApp();
    }

    private void startApp() {

        Intent intent = new Intent(application, MainActivity.class);
        application.startActivity(intent);
    }
}