/*
 * Copyright (C) 2013 Fairphone Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fairphone.updater;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fairphone.updater.fragments.MainFragment;
import com.fairphone.updater.tools.Utils;

public class BootBroadcastReceiver extends BroadcastReceiver
{
    private static final int SERVICE_START_DELAY = 30;
    private final static long NOTIFICATION_INTERVAL_MILLIS = 1000 * Utils.SECONDS_IN_MINUTE * Utils.MINUTES_IN_HOUR * 8;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        //
        // if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        // {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FairphoneUpdater.FAIRPHONE_UPDATER_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FairphoneUpdater.PREFERENCE_CURRENT_UPDATER_STATE, FairphoneUpdater.UpdaterState.NORMAL.name());
        editor.putInt(FairphoneUpdater.PREFERENCE_SELECTED_VERSION_NUMBER, 0);
        editor.putString(FairphoneUpdater.PREFERENCE_SELECTED_VERSION_TYPE, "");
        editor.putInt(FairphoneUpdater.PREFERENCE_SELECTED_STORE_NUMBER, -1);
        editor.remove(UpdaterService.LAST_CONFIG_DOWNLOAD_IN_MS);
        editor.remove(MainFragment.SHARED_PREFERENCES_ENABLE_GAPPS);
        editor.commit();

        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, UpdaterService.class);
        PendingIntent pending = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

        // start service for the first time
        context.startService(i);
        Calendar cal = Calendar.getInstance();
        // Start 30 seconds after boot completed
        cal.add(Calendar.SECOND, SERVICE_START_DELAY);

        // InexactRepeating allows Android to optimize the energy
        // consumption
        service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), NOTIFICATION_INTERVAL_MILLIS, pending);
        // }
    }

}
