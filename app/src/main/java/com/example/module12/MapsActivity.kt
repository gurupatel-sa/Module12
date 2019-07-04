package com.example.module12

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener {
    override fun onMyLocationChange(p0: Location?) {
        Log.d(TAG ,"On My location Changed")
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
            else{
                marker=MarkerOptions().title("New Content").snippet("implemented")
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources , R.drawable.leak_canary_notification)))
                marker?.draggable(true)

            }
            marker?.position(LatLng(lat,long))
            m = mMap.addMarker(marker)
            m?.showInfoWindow()

            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat , long)))

        }

        override fun onLocationAvailability(p0: LocationAvailability?) {
            super.onLocationAvailability(p0)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.d(TAG ,"on Map Ready")
        mMap.setInfoWindowAdapter(object  : GoogleMap.InfoWindowAdapter{
            override fun getInfoContents(p0: Marker?): View {
                Log.d(TAG ,"getInfoContents")

                return View(this@MapsActivity)
            }

            override fun getInfoWindow(p0: Marker?): View {
                Log.d(TAG ,"getInfoWindow")
                var v:View = LayoutInflater.from(this@MapsActivity).inflate(R.layout.activity_maps_example ,null)
                v.findViewById<TextView>(R.id.title).setText(p0?.title)
                v.findViewById<TextView>(R.id.title2).setText(p0?.title)

                return v
            }
        })


        Log.d(TAG ,"Google Map")
        mMap.isMyLocationEnabled = true
        mMap?.setOnMyLocationChangeListener(this)
        mMap?.setOnMarkerDragListener(object  : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragEnd(p0: Marker?) {
            Log.d(TAG ,"on marker Drag end")
            }

            override fun onMarkerDragStart(p0: Marker?) {
                Log.d(TAG ,"on marker Drag start")

            }

            override fun onMarkerDrag(p0: Marker?) {
                Log.d(TAG ,"on marker Drag ")

            }
        })

    }

}
