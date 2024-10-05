package com.example.cordi

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.HealthConnectClientFactory
import androidx.health.connect.client.records.HeartRate
import androidx.health.connect.client.request.InsertRecordsRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.response.ReadRecordsResponse
import androidx.health.connect.client.response.InsertRecordsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    private lateinit var healthConnectClient: HealthConnectClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // UI elements go here (using Jetpack Compose)
        }

        // Initialize Health Connect client
        healthConnectClient = HealthConnectClientFactory.create(this)

        // Request permission to access health data
        requestHealthDataPermissions()
    }

    private fun requestHealthDataPermissions() {
        val permissions = arrayOf(
            "android.permission.BIND_HEALTH_CONNECT_SERVICE",
            "android.permission.ACTIVITY_RECOGNITION",
            "android.permission.READ_HEALTH_DATA",
            "android.permission.WRITE_HEALTH_DATA"
        )

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, missingPermissions.toTypedArray(), PERMISSION_REQUEST_CODE)
        } else {
            // Permissions already granted, proceed with reading/writing data
            readHeartRateData()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Permissions granted, proceed with reading/writing data
                readHeartRateData()
            } else {
                Toast.makeText(this, "Permissions are required to access health data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun insertHeartRateData(heartRate: HeartRate) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = InsertRecordsRequest.Builder()
                    .addRecord(heartRate)
                    .build()
                val response: InsertRecordsResponse = healthConnectClient.insertRecords(request)

                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Inserted ${response.records.size} heart rate records", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error inserting heart rate data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun readHeartRateData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = ReadRecordsRequest(
                    recordType = HeartRate::class,
                    timeRange = null
                )
                val response: ReadRecordsResponse<HeartRate> = healthConnectClient.readRecords(request)

                // Handle the retrieved heart rate records
                response.records.forEach { record ->
                    // Process each heart rate record
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Retrieved heart rate: ${record.bpm}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error reading heart rate data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
