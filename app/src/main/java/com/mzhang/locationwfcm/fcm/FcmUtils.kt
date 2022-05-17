package com.mzhang.locationwfcm.fcm

import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.mzhang.locationwfcm.MainActivity
import com.mzhang.locationwfcm.R
import com.google.android.gms.tasks.OnCompleteListener

object FcmUtils {
    private const val TAG = "FirebaseCloudMessaging_Utils"

    fun addFcmTokenCompleteListener() {
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = String.format("FCM registration Token: %s", token)
            Log.d(TAG, msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
        })
    }

}