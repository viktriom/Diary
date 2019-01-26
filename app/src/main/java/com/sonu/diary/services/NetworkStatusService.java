package com.sonu.diary.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sonu.diary.util.NetworkUtil;

public class NetworkStatusService extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);
        Log.e("NetworkReceiverService", "Network Status Receiver called.");
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                Log.i("NetworkReceiverService","Network Status Changed to not Connected.");
            } else {
                Log.i("NetworkStatusUtil","Network Status changed to connected.");
                SyncService.syncPendingData(context);
            }
        }
    }

}
