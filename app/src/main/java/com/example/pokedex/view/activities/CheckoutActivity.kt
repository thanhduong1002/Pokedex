package com.example.pokedex.view.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.pokedex.R
import com.example.pokedex.data.dao.CartDao
import com.example.pokedex.data.dao.TypeDao
import com.example.pokedex.data.database.AppDatabase
import com.example.pokedex.data.repository.CartRepository
import com.example.pokedex.data.repository.TypeRepository
import com.example.pokedex.view.fragments.CheckoutFragment
import com.example.pokedex.viewmodels.CartViewModel
import com.example.pokedex.viewmodels.TypeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class CheckoutActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var currentPolyline: Polyline
    private lateinit var cartDao: CartDao
    private lateinit var cartRepository: CartRepository
    private lateinit var cartViewModel: CartViewModel
    private val myShop = LatLng(15.978804884694505, 108.24882632520233)
    private val yourLocation = LatLng(16.019177482505285, 108.2290438252028)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val getButton: Button = findViewById(R.id.buttonGet)

        val bundle = intent.extras

        if (bundle != null) {
            val data = bundle.getString(TotalPrice)

            if (data != null) {
                Log.d("TotalPrice", data)
            }
        }

        // Construct a PlacesClient
        Places.initialize(applicationContext, getString(R.string.google_map_api_key))
        placesClient = Places.createClient(this)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        mapFragment.getMapAsync(this)

        getButton.setOnClickListener {
            val database = AppDatabase.getDatabase(this)

            cartDao = database.cartDao()
            cartRepository = CartRepository(cartDao, database)
            cartViewModel = CartViewModel(cartRepository)

            val strOrigin: String = "origin=" + myShop.latitude + "," + myShop.longitude
            val strDest: String = "destination=" + yourLocation.latitude + "," + yourLocation.longitude

            cartViewModel.getDirection(strOrigin, strDest, resources.getString(R.string.google_map_api_key))
        }

        getCurrentLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        googleMap.addMarker(MarkerOptions().position(myShop).title("Marker in Shop"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myShop, 50f))
    }

    companion object {
        const val TotalPrice = "TotalPrice"
    }

    private fun getParameters(origin: LatLng, destination: LatLng, directionMode: String): String {
        val strOrigin: String = "origin=" + origin.latitude + "," + origin.longitude
        val strDest: String = "destination=" + destination.latitude + "," + destination.longitude
        val mode = "mode=$directionMode"

        return "$strOrigin&$strDest&$mode"
    }

    private fun checkPermissions(): Boolean {
        if (this.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CheckoutFragment.PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show()
                Log.d("Grant", "Granted")
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show()
                Log.d("Grant", "Denied")
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            CheckoutFragment.PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions()
                    return
                }

                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result

                    if (location == null) {
                        Toast.makeText(this, "Null Received", Toast.LENGTH_SHORT).show()
                        val myPosition = LatLng(16.019177482505285, 108.2290438252028)

                        mGoogleMap?.addMarker(MarkerOptions().position(myPosition).title("Marker in Company"))
                        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 50f))
                    } else {
                        Toast.makeText(this, "Get Success", Toast.LENGTH_SHORT).show()
                        tvLatitude.text = "Latitude: ${location.latitude}"
                        tvLongitude.text = "Longitude: ${location.longitude}"
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show()

                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
}