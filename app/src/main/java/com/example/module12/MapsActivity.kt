package com.example.module12

import android.annotation.SuppressLint
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener {
    override fun onMyLocationChange(p0: Location?) {
        Log.d(TAG ,"On My location Cahnged")
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mMap2: GoogleMap
    private lateinit var fusedLocationProvider :FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallbacks : LocationCallbacks
    private var marker : MarkerOptions?= null
    private var m :Marker? =null

    var TAG : String = this.javaClass.getSimpleName()
    @SuppressLint("RestrictedApi", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationCallbacks=LocationCallbacks()
        locationRequest= LocationRequest()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(2000)
        locationRequest.setFastestInterval(2000)

        fusedLocationProvider = FusedLocationProviderClient(this)
        fusedLocationProvider.requestLocationUpdates(locationRequest ,locationCallbacks , Looper.getMainLooper())
    }

    inner class LocationCallbacks : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            Log.d(TAG ,"On Location Result")
            var lat = p0?.lastLocation?.latitude as Double
            var long = p0?.lastLocation?.longitude as Double


            if(marker!=null && m!=null){
                m?.remove()
            }
            marker=MarkerOptions().position(LatLng(lat,long))
            m = mMap.addMarker(marker)

            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat , long)))

        }

        override fun onLocationAvailability(p0: LocationAvailability?) {
            super.onLocationAvailability(p0)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        Log.d(TAG ,"Google Map")
        mMap.isMyLocationEnabled = true
        mMap?.setOnMyLocationChangeListener(this)

    }

}
