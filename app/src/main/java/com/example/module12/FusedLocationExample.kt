package com.example.module12

import android.annotation.SuppressLint
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.widget.Toast
import com.google.android.gms.location.*
import java.util.jar.Manifest

class FusedLocationExample : AppCompatActivity() {

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fused_location_example)

        fusedLocationProviderClient= FusedLocationProviderClient(this)

        if(ContextCompat.checkSelfPermission(this , android.Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED)
        {
            locationRequest= LocationRequest()
            locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER)
            locationRequest.setInterval(5000)
        }


        fusedLocationProviderClient.requestLocationUpdates(locationRequest , object :LocationCallback(){
            override fun onLocationResult(locationresult: LocationResult?) {
                super.onLocationResult(locationresult)

                var lat = locationresult?.lastLocation?.latitude
                var long = locationresult?.lastLocation?.longitude

                Toast.makeText(applicationContext, "Lat : " + lat +" Long: " +long, Toast.LENGTH_SHORT).show()
            }

            override fun onLocationAvailability(p0: LocationAvailability?) {
                super.onLocationAvailability(p0)
            }
        } , Looper.getMainLooper())

    }
}
