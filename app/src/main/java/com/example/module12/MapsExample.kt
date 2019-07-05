package com.example.module12

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

class MapsExample : AppCompatActivity() ,OnMapReadyCallback {
    override fun onMapReady(p0: GoogleMap?) {
        Log.d(TAG ,"on Map Ready")
    }
    
    var TAG : String = this.javaClass.getSimpleName()
    var mapview :MapView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_example)

        mapview=findViewById(R.id.mapview)

//        savedInstanceState?.putBundle("key", "AIzaSyBuPidsYRNf6hkYbQeX9Po1TqioTvA_Wn4");

        mapview?.onCreate(savedInstanceState);
         mapview?.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mapview?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapview?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mapview?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapview?.onLowMemory()
    }


}
