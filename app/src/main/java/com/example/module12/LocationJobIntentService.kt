package com.example.module12

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Looper
import android.support.v4.app.JobIntentService
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.android.gms.location.*

class LocationJobIntentService : JobIntentService() {
    var TAG : String = this.javaClass.getSimpleName()
    var fusedLocationProvider:FusedLocationProviderClient?=null
    var locationRequest:LocationRequest? =null
    @SuppressLint("RestrictedApi", "MissingPermission")
    override fun onHandleWork(p0: Intent) {
    Log.d(TAG ,"onHandleWork")

        if(isStopped)
            return


//        var i :Intent =Intent(this , LocationJobIntentService::class.java)
//        var intent = PendingIntent.getService(this,0, i,PendingIntent.FLAG_UPDATE_CURRENT)
        fusedLocationProvider?.requestLocationUpdates(locationRequest, object :LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                Log.d(TAG ,"on result")
            }

            override fun onLocationAvailability(p0: LocationAvailability?) {
                super.onLocationAvailability(p0)
            }
        } , Looper.getMainLooper())


    }


    @SuppressLint("RestrictedApi")
    override fun onCreate() {
        super.onCreate()

        Log.d(TAG ,"on create")
        fusedLocationProvider = FusedLocationProviderClient(this)

        locationRequest= LocationRequest()
        locationRequest?.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest?.setInterval(2000)
        locationRequest?.setFastestInterval(2000)

        Log.d(TAG ,"on startcommand")
        var notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        var NOTIFICATION = NotificationCompat.Builder(applicationContext, "location")
            .setContentTitle("Notification Action")
            .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
            .build()

        startForeground(1001 , NOTIFICATION)

    }

    companion object{
        fun enqueuwWork(c:Context ,work:Intent){
            enqueueWork(c,LocationJobIntentService::class.java,123,work)
        }
    }

    override fun onStopCurrentWork(): Boolean {
        Log.d(TAG ,"on stop current work")
        return super.onStopCurrentWork()
    }


}