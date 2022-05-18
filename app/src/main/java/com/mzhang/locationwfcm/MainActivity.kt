package com.mzhang.locationwfcm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mzhang.locationwfcm.databinding.ActivityMainBinding
import com.mzhang.locationwfcm.fcm.FcmUtils
import com.mzhang.locationwfcm.locationservice.MyLocationManager
import com.mzhang.locationwfcm.locationservice.permissions.PermissionsRequestor
import com.mzhang.locationwfcm.locationservice.permissions.PermissionsRequestor.ResultListener


class MainActivity : AppCompatActivity() {
    private lateinit var permissionsRequestor: PermissionsRequestor
    private var startLocation = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var myLocationManager: MyLocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        myLocationManager = MyLocationManager.getInstance(this)
        // setup FCM push notification
        FcmUtils.addFcmTokenCompleteListener()

        handleAndroidPermissions()
        myLocationManager.receivingLocationUpdates.observe(
            this
        ) { receiving ->
            updateStartOrStopButtonState(receiving)
        }
    }

    private fun updateStartOrStopButtonState(receivingLocation: Boolean) {
        if (receivingLocation) {
            binding.buttonStartLocation.apply {
                text = getString(R.string.stop_location_request)
                setOnClickListener {
                    myLocationManager.stopLocationUpdates()
                }
            }
        } else {
            binding.buttonStartLocation.apply {
                text = getString(R.string.start_location_request)
                setOnClickListener {
                    myLocationManager.startLocationUpdates()
                }
            }
        }

    }

    private fun handleAndroidPermissions() {
        permissionsRequestor = PermissionsRequestor(this)
        permissionsRequestor.request(object : ResultListener {
            override fun permissionsGranted() {
                updateStartOrStopButtonState(startLocation)
            }

            override fun permissionsDenied() {
                Log.e(TAG, "Permissions denied by user.")
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsRequestor.onRequestPermissionsResult(requestCode, grantResults)
    }

    companion object {

        private const val TAG = "MainActivity"
    }}