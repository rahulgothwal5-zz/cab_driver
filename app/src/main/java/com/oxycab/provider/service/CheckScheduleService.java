package com.oxycab.provider.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.oxycab.provider.common.SharedHelper;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CheckScheduleService extends JobService {

    public static final int CHECK_SCHEDULE_JOB_ID = 123;
    public static final String approved = "approved";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        String accountStatus = SharedHelper.getKey(getApplicationContext(), "accountStatus");
        String serviceStatus = SharedHelper.getKey(getApplicationContext(), "serviceStatus");
        if (!isServiceRunning() && approved.equalsIgnoreCase(accountStatus) && !serviceStatus.equals("offline")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                getApplicationContext().startService(new Intent(getApplicationContext(), LocationShareService.class));
            } else {
                Intent serviceIntent = new Intent(getApplicationContext(), LocationShareService.class);
                ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
            }
        } else {
            stopLocationService();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    private boolean isServiceRunning() {
        ActivityManager manager =
                (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE)) {
            if (LocationShareService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void stopLocationService() {
        if (isServiceRunning())
            stopService(new Intent(getApplicationContext(), LocationShareService.class));
    }
}
