package com.example.pokedex.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pokedex.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CheckoutActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val bundle = intent.extras

        if (bundle != null) {
            val data = bundle.getString(TotalPrice)

            if (data != null) {
                Log.d("TotalPrice", data)
            }
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        val myPosition = LatLng(15.978804884694505, 108.24882632520233)

        googleMap.addMarker(MarkerOptions().position(myPosition).title("Marker in Shop"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 80f))
    }

    companion object {
        const val TotalPrice = "TotalPrice"
    }
}