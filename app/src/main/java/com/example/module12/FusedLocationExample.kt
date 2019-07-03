package com.example.module12

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import android.location.LocationListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import java.util.*

class FusedLocationExample : AppCompatActivity() ,LocationListener {
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d(TAG ,"on statu")
    }

    override fun onProviderEnabled(provider: String?) {
        Log.d(TAG ,"on providerEnabled")
    }

    override fun onProviderDisabled(provider: String?) {
        Log.d(TAG ,"onProviderDisabled")
    }

    override fun onLocationChanged(p0: Location?) {
        Toast.makeText(applicationContext, "on location changed", Toast.LENGTH_SHORT).show();
        Log.d(TAG ,"LOCATION CHANGED" )

        var lat = p0?.latitude as Double
        var long =p0?.longitude as Double

        var geocoder = Geocoder(this ,Locale.getDefault())
        var address:List<Address>

        address=geocoder.getFromLocation(lat ,long ,3)

        Log.d(TAG ,"Address" + address?.get(0)?.locality)
    }


    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    var callback :Callback? =null
    var TAG : String = this.javaClass.getSimpleName()

    @SuppressLint("RestrictedApi", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fused_location_example)

        fusedLocationProviderClient= FusedLocationProviderClient(this)

//        if(ContextCompat.checkSelfPermission(this , android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//            && ContextCompat.checkSelfPermission(this , android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//
//            requestPermissions( arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),5)
//            requestPermissions( arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),5)
//
//            Log.d(TAG ,"Allow")
//        }
//
//        else{
//            Log.d(TAG ,"ELSE")
//        }
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER , 10000 , 10000f , this )


        if(ContextCompat.checkSelfPermission(this , android.Manifest.permission.ACCESS_COARSE_LOCATION) == PermissionChecker.PERMISSION_GRANTED)
        {
            Log.d(TAG ,"Permission granted")

            locationRequest= LocationRequest()
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            locationRequest.setInterval(2000)
            locationRequest.setFastestInterval(4000)
        }
        else{
            Log.d(TAG ,"Permission not granted")
        }
        callback=Callback()

        fusedLocationProviderClient.requestLocationUpdates(locationRequest , callback , Looper.getMainLooper())
    }

    inner class Callback : LocationCallback() {
        override fun onLocationResult(locationresult: LocationResult?) {
            super.onLocationResult(locationresult)

            var lat = locationresult?.lastLocation?.latitude
            var long = locationresult?.lastLocation?.longitude

            Toast.makeText(applicationContext, "Lat : " + lat +" Long: " +long, Toast.LENGTH_SHORT).show()
        }

        override fun onLocationAvailability(p0: LocationAvailability?) {
            super.onLocationAvailability(p0)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(callback)
        callback=null
    }
}
