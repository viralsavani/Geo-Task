package com.mobile.av.geotask;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.mobile.av.geotask.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VIRAL on 5/6/2015.
 */
public class GeoFence implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected static final String TAG = "GeoFences";

    private GoogleApiClient mGoogleApiClient;

    protected ArrayList<Geofence> geoFenceList;
    private PendingIntent geoFencePendingIntent;

    public static Task CURRENT_TASK;
    public static int CURRENT_TASK_POSITION;

    private Context context;
    private String geoFenceId;
    private ArrayList<LatLng> latLngList;
    private int range;

    public GeoFence(Context context, Task task, int positionInList){
        this.context = context;
        CURRENT_TASK = task;
        CURRENT_TASK_POSITION = positionInList;
        geoFenceId = String.valueOf(task.getTask_id());
        latLngList = task.getLocation();
        range = (int)task.getRange();

        buildGoogleApiClient();

        geoFenceList = new ArrayList<>();
        geoFencePendingIntent = null;
    }

    public void executeGeoFence(){
        Log.i(TAG, "GoogleApiClient.connect Called");
        mGoogleApiClient.connect();
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.i(TAG, "Building GoogleApiClient");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Connected to GoogleApiClient");

        // To add Geofence
        if (CURRENT_TASK.getStatus() == 1){
            populateGeoFenceList();
            addGeoFence();
        }else{
        // To remove Geofence
            removeGeoFence();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(context, "GoogleApiClient Not available", Toast.LENGTH_SHORT).show();
    }

    private void addGeoFence(){
        if (!mGoogleApiClient.isConnected()) {
            return;
        }

        Log.i(TAG, "addGeoFence Called");
        geoFencePendingIntent = getGeoFencePendingIntent();
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeoFencingRequest(),
                geoFencePendingIntent
        );
    }

    private GeofencingRequest getGeoFencingRequest(){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(geoFenceList);
        return builder.build();
    }

    private PendingIntent getGeoFencePendingIntent() {
        if (geoFencePendingIntent != null) {
            return geoFencePendingIntent;
        }

        Intent intent = new Intent(context, GeoFenceTransitionsIntentService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void populateGeoFenceList(){
        for (int i = 0; i < latLngList.size(); i++) {
            String uniqueId = geoFenceId + ":" + String.valueOf(i);
            SimpleGeoFence geoFence = new SimpleGeoFence(
                    uniqueId,
                    latLngList.get(i).latitude,
                    latLngList.get(i).longitude,
                    range,
                    Geofence.NEVER_EXPIRE,
                    Geofence.GEOFENCE_TRANSITION_ENTER,
                    1000 );
            geoFenceList.add(geoFence.getGeoFence());
        }
    }

    public void removeGeoFence(){
        List<String> geoFenceRequestId = new ArrayList<>();

        for (int i = 0; i < latLngList.size(); i++) {
            geoFenceRequestId.add(geoFenceId + String.valueOf(i));
        }

        if (mGoogleApiClient.isConnected()){
            LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, geoFenceRequestId);
        }
    }



    /**
     * A Geofence class used to create Geofence
     */
    public class SimpleGeoFence implements Geofence {

        private Geofence geofence;
        private String requestID;
        private double latitude;
        private double longitude;
        private float radius;
        private long expirationDuration;
        private int transitionType;
        private int loiteringDelay;

        public SimpleGeoFence(String requestID,
                              double latitude, double longitude,
                              float radius, long expirationDuration,
                              int transitionType, int loiteringDelay){

            this.requestID = requestID;
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.expirationDuration = expirationDuration;
            this.transitionType = transitionType;
            this.loiteringDelay = loiteringDelay;
        }

        public Geofence getGeoFence(){
            geofence = new Geofence.Builder()
                    .setRequestId(requestID)
                    .setCircularRegion(
                            latitude,
                            longitude,
                            radius)
                    .setExpirationDuration(expirationDuration)
                    .setTransitionTypes(transitionType)
                    .setLoiteringDelay(loiteringDelay)
                    .build();
            return geofence;
        }

        @Override
        public String getRequestId() {
            return requestID;
        }
    }

}
