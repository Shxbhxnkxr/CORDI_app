package com.example.cordi

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.telephony.SmsManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startLocationUpdates()
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationTask: Task<Location> = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { location: Location? ->
            location?.let {
                sendLocationToObserver(it)
            }
        }
    }

    private fun sendLocationToObserver(location: Location) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            "observer_phone_number",  // Replace with the actual observer phone number
            null,
            "Patient location: ${location.latitude}, ${location.longitude}",
            null,
            null
        )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
