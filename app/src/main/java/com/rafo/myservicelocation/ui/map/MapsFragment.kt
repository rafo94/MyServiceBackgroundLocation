package com.rafo.myservicelocation.ui.map

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.rafo.myservicelocation.R
import com.rafo.myservicelocation.utils.dateToString
import com.rafo.myservicelocation.utils.midPoint
import java.util.*

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    private val viewModel: MapsViewModel by viewModels()
    private var id: UUID? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arg ->
            id = arg.getSerializable(LOCATION_ID) as UUID?
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id?.let { viewModel.getLocationList(it) }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latLngList = mutableListOf<LatLng>()
        viewModel.currentLocation.observe(viewLifecycleOwner) { location ->
            if (location.locationList.isNotEmpty()) {
                location.locationList.forEach {
                    latLngList.add(LatLng(it.latitude, it.longitude))
                }

                googleMap.addPolyline(
                    PolylineOptions().addAll(latLngList)
                        .width(15f)
                        .color(Color.RED)
                        .geodesic(true)
                )

                val startInfo = location.locationList.first().date.dateToString("dd-MMM-yy hh:mm")
                val lastInfo = location.locationList.last().date.dateToString("dd-MMM-yy hh:mm")

                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLngList.first())
                        .title("Start $startInfo")
                )
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLngList.last())
                        .title("End $lastInfo")
                )

                val lat1 = latLngList.first().latitude
                val long1 = latLngList.first().longitude
                val lat2 = latLngList.last().latitude
                val long2 = latLngList.last().longitude

                val cameraPosition = CameraPosition.Builder()
                    .target(midPoint(lat1, long1, lat2, long2))
                    .zoom(17f)
                    .build()

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }
    }

    companion object {
        private const val LOCATION_ID = "LOCATION_ID"

        @JvmStatic
        fun newInstance(id: UUID): MapsFragment {
            val fragment = MapsFragment()
            fragment.arguments = bundleOf(LOCATION_ID to id)
            return fragment
        }
    }
}