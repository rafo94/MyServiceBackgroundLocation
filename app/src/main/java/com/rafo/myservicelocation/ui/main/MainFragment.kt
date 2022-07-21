package com.rafo.myservicelocation.ui.main

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import com.rafo.myservicelocation.R
import com.rafo.myservicelocation.databinding.FragmentMainBinding
import com.rafo.myservicelocation.services.LocationService
import com.rafo.myservicelocation.ui.history.HistoryFragment
import com.rafo.myservicelocation.utils.*

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

    private val permissionRequest = registerForActivityResult(RequestPermission()) { isEnabled ->
        if (isEnabled) {
            if (isBackgroundLocationPermissionGranted()) {
                activity?.let {
                    if (shouldShowRationale(it)) {
                        showBackgroundLocationRationale()
                    }
                }
            }
        } else {
            openDialog()
        }
    }

    private val openSettings = registerForActivityResult(StartActivityForResult()) {
        permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = FragmentMainBinding.inflate(inflater, container, false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(requireContext(), LocationService::class.java)
        binding?.let {
            with(it) {
                startTracking.isEnabled =
                    context?.isServiceRunning(LocationService::class.java) != true

                startTracking.setOnClickListener { view ->
                    if (!requireContext().checkLocationPermission())
                        permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    else if (!requireContext().locationEnabled())
                        view.longSnackBar(getString(R.string.enable_gps)) {
                            action(getString(android.R.string.ok)) {
                                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                dismiss()
                            }
                        }
                    else {
                        startTracking.isEnabled = false
                        requireContext().startMyService(intent)
                    }
                }
                stopTracking.setOnClickListener {
                    startTracking.isEnabled = true
                    requireContext().stopService(intent)
                }
                showHistory.setOnClickListener { openHistoryFragment() }
            }
        }
    }

    private fun openHistoryFragment() {
        activity?.let { act ->
            act.supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, HistoryFragment.newInstance())
                addToBackStack(null)
            }.run { commit() }
        }
    }

    private fun openDialog() {
        requireContext().alert {
            setTitle(getString(R.string.dialog_title))
            setMessage(getString(R.string.dialog_msg))
            positiveButton(getString(android.R.string.ok)) {
                openSettings()
            }
        }
    }

    private fun showBackgroundLocationRationale() {
        requireContext().alert {
            setTitle(R.string.location_rationale_title)
            setMessage(R.string.location_rationale)
            positiveButton(getString(R.string.got_it)) {
                requestPermission(requireActivity())
            }
            negativeButton(getString(R.string.no_thanks))
        }
    }

    private fun openSettings() {
        openSettings.launch(
            Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", requireContext().packageName, null)
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}