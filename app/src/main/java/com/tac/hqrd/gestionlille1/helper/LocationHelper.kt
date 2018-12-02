package com.tac.hqrd.gestionlille1.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class LocationHelper {
    companion object {
        private lateinit var geocoder: Geocoder

        private const val TAG: String = "LocationHelper"

        private fun getAdresses(context: Context, latitude: Float, longitude: Float): List<Address> {
            geocoder = Geocoder(context, Locale.getDefault())

            val errorMessage: String
            var addresses: List<Address> = emptyList()
            try {
                addresses = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
            } catch (ioException: IOException) {
                errorMessage = "service_not_available"
                Log.e(TAG, errorMessage, ioException)
            } catch (illegalArgumentException: IllegalArgumentException) {
                errorMessage = "invalid_lat_long_used"
                Log.e(
                    TAG, "$errorMessage. Latitude = $latitude , " +
                            "Longitude =  $longitude", illegalArgumentException
                )
            }
            return addresses
        }

        /**
         * Return a list containing 1 adress for the current location, after 1 second in a callback method
         * List may be empty if Geocoder couldn't find an adress
         */
        suspend fun getLastLoc(activity: FragmentActivity, around: Boolean, callback: (List<Address>) -> Unit) {
            var adresses: List<Address> = emptyList()
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

            val mLocationRequest = LocationRequest()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.interval = 200
            mLocationRequest.fastestInterval = 100

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                }
            }
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                if (Looper.myLooper() == null) {
                    Looper.prepare()
                }
                fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper())
                GlobalScope.launch {
                    delay(1000)
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            location?.let {
                                var lat = location.latitude
                                var long = location.longitude
                                if (around) {
                                    lat += ThreadLocalRandom.current().nextDouble(-0.004, 0.004);
                                    long += ThreadLocalRandom.current().nextDouble(-0.004, 0.004);
                                }
                                adresses = LocationHelper.getAdresses(
                                    activity, lat.toFloat(), long.toFloat()
                                )
                                callback.invoke(adresses)
                            }
                        }
                }
            } else {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
                callback.invoke(adresses)
            }
        }

        /**
         * Calculate distance between two points in latitude and longitude.
         * @returns Distance in Meters
         */
        fun distance(
            lat1: Double, lat2: Double, lon1: Double,
            lon2: Double
        ): Double {
            val radiusEarth = 6371

            val latDistance = Math.toRadians(lat2 - lat1)
            val lonDistance = Math.toRadians(lon2 - lon1)
            val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(lat1)) * Math.cos(
                Math.toRadians(lat2)
            )
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2))
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

            return radiusEarth.toDouble() * c * 1000.0
        }

    }


}