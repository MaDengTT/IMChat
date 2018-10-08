package com.mdshi.component_chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ProtectService extends Service {
    public ProtectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, IMChatService.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
