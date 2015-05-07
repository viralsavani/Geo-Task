package com.mobile.av.geotask;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.mobile.av.geotask.adapters.TaskListArrayAdapter;
import com.mobile.av.geotask.db.TaskDataSource;
import com.mobile.av.geotask.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by VIRAL on 5/6/2015.
 */
public class GeoFenceTransitionsIntentService extends IntentService {

    protected static final String TAG = "Intent-service";
    TaskDataSource taskDataSource;

    Task currentTask;


    public GeoFenceTransitionsIntentService() {
        super(GeoFenceTransitionsIntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "errorMessage in onHandleIntent");
            return;
        }

        int geoFenceTransition = geofencingEvent.getGeofenceTransition();

        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeringGeoFences = geofencingEvent.getTriggeringGeofences();

            // If there are more than one geofence being triggered, NOT HANDLED.
            taskDataSource = new TaskDataSource(getApplicationContext());
            taskDataSource.open();
            String requestId = triggeringGeoFences.get(0).getRequestId();
            requestId = requestId.substring(0, requestId.lastIndexOf(":"));
            int taskId = Integer.parseInt(requestId);
            currentTask = taskDataSource.getTaskFromId(taskId);
            taskDataSource.close();

            String geoFenceTransitionDetails = getGeoFenceTransitionDetails(
                    geoFenceTransition,
                    triggeringGeoFences
            );

            // Remove GeoFence WARNING Intersection of two tasks is not handled
            taskDataSource = new TaskDataSource(getApplicationContext());
            taskDataSource.open();
            taskDataSource.setTaskStatus(currentTask.getTask_id(), 0);
            taskDataSource.close();
            GeoFence geoFence = new GeoFence(getApplicationContext(), currentTask, GeoFence.CURRENT_TASK_POSITION);
            geoFence.executeGeoFence();


            sendNotification(geoFenceTransitionDetails);
            Log.i(TAG, geoFenceTransitionDetails);
        }
    }


    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the TaskDetailActivity.
     */
    private void sendNotification(String notificationDetails) {

        Intent notificationIntent = new Intent(getApplicationContext(), TaskDetailActivity.class);
        notificationIntent.putExtra(TaskListArrayAdapter.POSITION, GeoFence.CURRENT_TASK_POSITION);
        notificationIntent.putExtra(".model.Task", currentTask);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setColor(Color.parseColor("#12CDC2"))
                .setContentTitle(currentTask.getTitle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);

        builder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(currentTask.getTask_id(), builder.build());
    }


    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param geoFenceTransition    The ID of the geofence transition.
     * @param triggeringGeofences   The geofence(s) triggered.
     * @return                      The transition details formatted as String.
     */
    private String getGeoFenceTransitionDetails(
            int geoFenceTransition,
            List<Geofence> triggeringGeofences) {

        String geoFenceTransitionString = getTransitionString(geoFenceTransition);

        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeoFencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geoFenceTransitionString + ": " + triggeringGeoFencesIdsString;
    }

    /**
     * Maps geoFence transition types to their human-readable equivalents.
     *
     * @param transitionType    A transition type constant defined in GeoFence
     * @return                  A String indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Entered";
            default:
                return "Unknown Status";
        }
    }
}
