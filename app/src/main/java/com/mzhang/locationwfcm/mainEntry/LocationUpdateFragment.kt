package com.mzhang.locationwfcm.mainEntry

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.sample.locationupdatesbackgroundkotlin.hasPermission
import com.mzhang.locationwfcm.R
import com.mzhang.locationwfcm.databinding.FragmentLocationUpdateBinding
import com.mzhang.locationwfcm.locationservice.MyLocationManager
import com.mzhang.locationwfcm.locationservice.permissions.PermissionRequestType

private const val TAG = "LocationUpdateFragment"

/**
 * Displays location information via PendingIntent after permissions are approved.
 *
 * Will suggest "enhanced feature" to enable background location requests if not approved.
 */
class LocationUpdateFragment : Fragment() {


    private lateinit var binding: FragmentLocationUpdateBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)

//        if (context is Callbacks) {
//            activityListener = context
//
//            // If fine location permission isn't approved, instructs the parent Activity to replace
//            // this fragment with the permission request fragment.
//            if (!context.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
//
////                activityListener?.requestFineLocationPermission()
//            }
//        } else {
//            throw RuntimeException("$context must implement LocationUpdateFragment.Callbacks")
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLocationUpdateBinding.inflate(inflater, container, false)

        binding.enableBackgroundLocationButton.setOnClickListener {
            requestLocationPermission(PermissionRequestType.BACKGROUND_LOCATION)

//            val navDirections = LocationUpdateFragmentDirections.actionLocationUpdateFragmentToPermissionRequestFragment(
//                type = PermissionRequestType.BACKGROUND_LOCATION
//            )
//            findNavController().navigate(navDirections)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If fine location permission isn't approved, instructs the parent Activity to replace
        // this fragment with the permission request fragment.
        if (!requireContext().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestLocationPermission(PermissionRequestType.FINE_LOCATION)
        }

        binding.startLocationUpdatesButton.apply {
            val lactionManager = MyLocationManager.getInstance(requireContext())
            setOnClickListener {
                lactionManager.startLocationUpdates()
            }
        }

        binding.stopLocationUpdatesButton.apply {
            val lactionManager = MyLocationManager.getInstance(requireContext())
            setOnClickListener {
                lactionManager.stopLocationUpdates()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        updateBackgroundButtonState()
    }

    override fun onPause() {
        super.onPause()

        // Stops location updates if background permissions aren't approved. The FusedLocationClient
        // won't trigger any PendingIntents with location updates anyway if you don't have the
        // background permission approved, but it's best practice to unsubscribing anyway.
        // To simplify the sample, we are unsubscribing from updates here in the Fragment, but you
        // could do it at the Activity level if you want to continue receiving location updates
        // while the user is moving between Fragments.
//        if ((locationUpdateViewModel.receivingLocationUpdates.value == true) &&
//            (!requireContext().hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION))) {
//            locationUpdateViewModel.stopLocationUpdates()
//        }
    }

    override fun onDetach() {
        super.onDetach()

    }

    private fun requestLocationPermission(type: PermissionRequestType) {
        val navDirections = LocationUpdateFragmentDirections.actionLocationUpdateFragmentToPermissionRequestFragment(
            type = type
        )

        findNavController().navigate(navDirections)
    }

    private fun showBackgroundButton(): Boolean {
        return !requireContext().hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }

    private fun updateBackgroundButtonState() {
        if (showBackgroundButton()) {
            binding.enableBackgroundLocationButton.visibility = View.VISIBLE
        } else {
            binding.enableBackgroundLocationButton.visibility = View.GONE
        }
    }

    private fun updateStartOrStopButtonState(receivingLocation: Boolean) {
//        if (receivingLocation) {
//            binding.startOrStopLocationUpdatesButton.apply {
//                text = getString(R.string.stop_receiving_location)
//                setOnClickListener {
//                    locationUpdateViewModel.stopLocationUpdates()
//                }
//            }
//        } else {
//            binding.startOrStopLocationUpdatesButton.apply {
//                text = getString(R.string.start_receiving_location)
//                setOnClickListener {
//                    locationUpdateViewModel.startLocationUpdates()
//                }
//            }
//        }
    }

    companion object {
        fun newInstance() = LocationUpdateFragment()
    }
}
