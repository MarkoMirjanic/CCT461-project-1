package com.example.hydrobuddy.service;

import static com.example.hydrobuddy.util.WaterIntakePreferences.saveGoalCompletion;

import android.app.Service;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Build;
import android.widget.Toast;
import android.media.MediaPlayer;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.hydrobuddy.MainActivity;
import com.example.hydrobuddy.R;
import com.example.hydrobuddy.util.Constants;
import com.example.hydrobuddy.util.SharedPreferencesUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WaterReminderService extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private Handler handler;
    private Runnable alarmRunnable;
    private int notificationId = 1;
    int goal = 0;
    int reminder = 0;
    int glassesDrank = 0;

    String currentDate = "";

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        goal = SharedPreferencesUtil.getInt(Constants.GLASS_GOAL,15);
        reminder = SharedPreferencesUtil.getInt(Constants.REMINDER_GOAL,15);
        glassesDrank = SharedPreferencesUtil.getInt(Constants.GLASS_GOAL,0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        startForegroundWithNotification();
        scheduleAlarmAndToast();
        return START_STICKY;
    }

    private void startForegroundWithNotification() {
        String input = "HydroBuddy is Running";
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("HydroBuddy is Running")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        startForeground(notificationId, notification);
    }
    private long calculateInterval() {
        // Calculate the time between reminders based on the goal and reminder
        int goal = this.goal;
        int reminder = this.reminder;
        long currentTime = System.currentTimeMillis();

        // Calculate the time in milliseconds between reminders
        return (goal - glassesDrank) * (24 * 60 * 60 * 1000) / reminder;
    }
    private void scheduleAlarmAndToast() {
        long interval = calculateInterval();
//        while (glassesDrank < goal) {
            alarmRunnable = new Runnable() {
                @Override
                public void run() {
                    // Play alarm sound
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDate = dateFormat.format(date);
                    if (SharedPreferencesUtil.getString(Constants.DATE,currentDate).equals(currentDate)){
                        SharedPreferencesUtil.putInt(Constants.GLASS_GOAL,15);
                        SharedPreferencesUtil.putInt(Constants.REMINDER_GOAL,15);
                        SharedPreferencesUtil.putInt(Constants.WATER_DRANK,0);
                        SharedPreferencesUtil.putString(Constants.DATE,currentDate);
                    }
                    playAlarm();
                    goal = SharedPreferencesUtil.getInt(Constants.GLASS_GOAL,15);
                    reminder = SharedPreferencesUtil.getInt(Constants.REMINDER_GOAL,15);
                    glassesDrank = SharedPreferencesUtil.getInt(Constants.GLASS_GOAL,0);

                    saveGoalCompletion(WaterReminderService.this, currentDate, goal, glassesDrank);

                    // Show a toast
                    showToast("Reminder: It's time to check your water!");

                    // Schedule the next alarm and toast after 10 seconds
                    handler.postDelayed(this, interval); // 10 seconds
                }
            };
            handler.postDelayed(alarmRunnable, interval); // Start the initial alarm and toast after 10 seconds
//        }
    }

    private void playAlarm() {
        // Create a MediaPlayer and play the alarm sound
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.start();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the alarm and toast callback when the service is stopped
        if (handler != null && alarmRunnable != null) {
            handler.removeCallbacks(alarmRunnable);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
