package com.example.module12

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest

class GeoFencingExample : AppCompatActivity() {

    private var geofencingClient:GeofencingClient?=null
    private val requestId ="Azia"
    @SuppressLint("RestrictedApi", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geo_fancing_example)

        geofencingClient=GeofencingClient(this)

        var geofence= Geofence.Builder().setRequestId(requestId).setCircularRegion(57.0 , -145.0 ,10f)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()
        var geofencingRequest =GeofencingRequest.Builder().addGeofence(geofence).build()

        var intent = Intent(this , GeofenceTransitionsIntentService::class.java)
        var pendingIntent :PendingIntent = PendingIntent.getService(this , 0 , intent , 0)

        geofencingClient?.addGeofences(geofencingRequest ,pendingIntent)
    }
}
