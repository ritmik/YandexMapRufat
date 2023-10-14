package com.example.yandexmaprufat.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.yandexmaprufat.App
import com.example.yandexmaprufat.R
import com.example.yandexmaprufat.data.Bank
import com.example.yandexmaprufat.data.LatestNewsUiState
import com.example.yandexmaprufat.databinding.FragmentMainBinding
import com.example.yandexmaprufat.isPermissionGranted
import com.example.yandexmaprufat.onRequestPermissionsResultCheck
import com.example.yandexmaprufat.requestPermission
import com.example.yandexmaprufat.ui.vmfactories.MainViewModelFactory
import com.example.yandexmaprufat.util.SharedPreferencesProvider
import com.example.yandexmaprufat.util.showErrorDialog
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    val viewModel: MainViewModel by viewModels { MainViewModelFactory((requireActivity().application as App).mainRepository) }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: MapView
    private var currentPosition: CameraPosition? = null

    private var currentMapObjects: List<Point>? = null
    private var currentBankObjects: List<Point>? = null
    val list = mutableListOf<Bank>()
    private val placemarkTapListener = MapObjectTapListener { mapObject, point ->

        true
    }

    /////////////////////////////////////////
    private val drivingRouteListener = object : DrivingSession.DrivingRouteListener {
        override fun onDrivingRoutes(drivingRoutes: MutableList<DrivingRoute>) {
            routes = drivingRoutes
        }

        override fun onDrivingRoutesError(error: com.yandex.runtime.Error) {
            when (error) {
                is NetworkError -> Log.d("drfctgyjh", "onDrivingRoutesError: ")
                else -> Log.d("drfctgyjh", "onDrivingRoutesError: ")
            }
        }

    }

    private var routePoints = emptyList<Point>()
        set(value) {
            field = value
            onRoutePointsUpdated()
        }

    private var routes = emptyList<DrivingRoute>()
        set(value) {
            field = value
            onRoutesUpdated()
        }

    private lateinit var drivingRouter: DrivingRouter
    private var drivingSession: DrivingSession? = null
    private lateinit var placemarksCollection: MapObjectCollection
    private lateinit var routesCollection: MapObjectCollection







    private val cameraListener = CameraListener { _, pos, _, _ ->
//        Log.d(
//            "TAGt",
//            "cameraListener = ${pos.target.latitude} ${pos.target.longitude} ${pos.zoom} "
//        )
        currentPosition = pos
    }

    private val inputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            // Handle single tap ...
        }

        override fun onMapLongTap(map: Map, point: Point) {
            // Handle long tap ...
            setImageOnMap(point)
        }
    }


    private var locationListener: LocationListener? = null
    private var locationManager: LocationManager? = null
    private var isEnableMyPoint = true

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TestLogDb().apply { testLogDb() }

        if (activity?.isPermissionGranted() == true) {
//            locationManager =
//                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            locationListener = LocationListener { p0 ->
//                //Log.d("TAGt", "p0 = ${p0.latitude} ${p0.longitude}")
//
//                if (isEnableMyPoint) {
//                    //showToast("p0 = ${p0.latitude} ${p0.longitude}")
//                    isEnableMyPoint = false
//                    setImageOnMap(p0.latitude, p0.longitude)
//                    mapView.mapWindow.map.move(
//                        CameraPosition(
//                            Point(p0.latitude, p0.longitude),
//                            17.0f,
//                            0.0f,
//                            0.0f
//                        )
//                    )
//                    locationListener = null
//                    locationManager = null
//                }
//            }
//            locationListener?.let {
//                locationManager?.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER, 1000L, 0F, it
//                )
//            }
        } else {
            activity?.requestPermission()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val shPr = SharedPreferencesProvider.getInstance(it)
            val result = shPr.getPreferencesString("TOKEN", "empty_token")
            Log.d("TAGt", "result = $result")
            shPr.savePreferencesString("TOKEN", "$result token ")
        }


        mapView = binding.yandexMapView
        mapView.mapWindow.map.addCameraListener(cameraListener)

        val map = mapView.mapWindow.map
        currentPosition?.let {
            Log.d("TAGt", "currentPosition?.let")
            map.move(it)
        } ?: kotlin.run {
            map.move(POSITION)
        }
        if (currentMapObjects == null) {
            setImageOnMap(Point(55.90536986, 48.95701074))
        } else {
            currentMapObjects?.forEach {
                setImageOnMap(it)

            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bankList.flowWithLifecycle(
                viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED
            ).collect {
                it.forEach { uit ->
                    Log.d("otladkaLela", "onViewCreated: " + uit)
                    setBankImageOnMap(Point(uit.lat.toDouble(), uit.lon.toDouble()))
                }
            }
        }




        mapView.mapWindow.map.addInputListener(inputListener)

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is LatestNewsUiState.Success -> {
                            Log.d(
                                "TAGt",
                                "collect LatestNewsUiState.Success size = ${it.news.size}"
                            )
                        }

                        is LatestNewsUiState.Error -> {
                            Log.d(
                                "TAGt",
                                "collect111 LatestNewsUiState.Error message = ${it.exception.message}"
                            )
                            activity?.showErrorDialog(it.exception.message)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //Log.d("TAGt", "requestCode = $requestCode")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        activity?.onRequestPermissionsResultCheck(requestCode, grantResults)
    }

    private fun setImageOnMap(point: Point) {
        val imageProvider = ImageProvider.fromResource(context, R.drawable.here_icon)
        val placemarkObject = mapView.map.mapObjects.addPlacemark().apply {
            geometry = point
            setIcon(imageProvider)
        }
        placemarkObject.addTapListener(placemarkTapListener)
        currentMapObjects = currentMapObjects.orEmpty() + point
        //placemarkObject.removeTapListener(placemarkTapListener)
    }

    private fun setBankImageOnMap(point: Point) {
        val imageProvider = ImageProvider.fromResource(context, R.drawable.ic_dollar_pin)
        val placemarkObject = mapView.map.mapObjects.addPlacemark().apply {
            geometry = point
            setIcon(imageProvider)
        }
        placemarkObject.addTapListener(placemarkTapListener)
        currentBankObjects = currentBankObjects.orEmpty() + point
        //placemarkObject.removeTapListener(placemarkTapListener)
    }

    private fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    companion object {
        fun newInstance() = MainFragment()
        private val POINT = Point(55.90536986, 48.95701074)
        private val POSITION = CameraPosition(POINT, 17.0f, 0.0f, 0.0f)
    }
}