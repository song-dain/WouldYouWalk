package com.greedy.wouldyouwalk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.greedy.wouldyouwalk.databinding.FragmentRouterecordBinding

class RouteRecordFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mView: MapView

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentRouterecordBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRouterecordBinding.inflate(inflater, container, false)

        val googleMap: GoogleMap

        /* 화면에 지도 띄우기 */
        mView = binding.mapview as MapView
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)

        binding.testBtn1.setOnClickListener {Toast.makeText(mainActivity, "앗, 업데이트 준비 중인 코스입니다!", Toast.LENGTH_SHORT).show() }
        binding.testBtn2.setOnClickListener {Toast.makeText(mainActivity, "앗, 업데이트 준비 중인 코스입니다!", Toast.LENGTH_SHORT).show() }
        binding.testBtn3.setOnClickListener {Toast.makeText(mainActivity, "앗, 업데이트 준비 중인 코스입니다!", Toast.LENGTH_SHORT).show() }
        binding.testBtn4.setOnClickListener {Toast.makeText(mainActivity, "앗, 업데이트 준비 중인 코스입니다!", Toast.LENGTH_SHORT).show() }
        binding.testBtn5.setOnClickListener {Toast.makeText(mainActivity, "앗, 업데이트 준비 중인 코스입니다!", Toast.LENGTH_SHORT).show() }

        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        /* 현재 위치로 이동 설정(애뮬레이터는 위치 받을 수 없어서 구글 본사로 이동됨) */
        googleMap.isMyLocationEnabled = true

        /* 디폴트 위치 */
        val defaultMarker = LatLng(37.571919, 126.987316)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultMarker, 15f))

        /* 인사동길-열린녹지광장 마커+경로 */
        binding.walk1.setOnClickListener {

            val walk1s = LatLng(37.572148, 126.986763)
            googleMap.addMarker(MarkerOptions().position(walk1s).title("📍 인사동길")
                .snippet("인사동길 탐방 고고🧐").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon2)))

            val walk1a = LatLng(37.575646, 126.982810)
            googleMap.addMarker(MarkerOptions().position(walk1a).title("📍 열린녹지광장")
                .snippet("열린녹지광장에서 힐링! 🌻").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon3)))

            val walk1 = LatLng(37.574240, 126.984535)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(walk1, 15f))

            val polylineOption = PolylineOptions().color(Color.argb(117, 59, 100, 1))
                .add(LatLng(37.572630, 126.986485))
                .add(LatLng(37.572992, 126.986202))
                .add(LatLng(37.573398, 126.985760))
                .add(LatLng(37.575313, 126.983365))
                .add(LatLng(37.575513, 126.982920))
                .add(LatLng(37.575646, 126.982810))

            val polylineOptions = googleMap.addPolyline(polylineOption)

        }

        /* 덕수궁 돌담길 마커+경로 */
        binding.walk2.setOnClickListener {
            val walk3s = LatLng(37.564844, 126.975005)
            googleMap.addMarker(MarkerOptions().position(walk3s).title("📍 덕수궁돌담길")
                .snippet("커플 환영!!!!!!!!!!!").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon6)))

            val walk3 = LatLng(37.565029, 126.974926)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(walk3, 16f))

            val polylineOption = PolylineOptions().color(Color.argb(117, 59, 100, 1))
                .add(LatLng(37.564854, 126.976649))
                .add(LatLng(37.564944, 126.975918))
                .add(LatLng(37.564757, 126.974066))
                .add(LatLng(37.565225, 126.973548))

            val polylineOptions = googleMap.addPolyline(polylineOption)

        }

        /* 북촌 한옥마을 마커+경로 */
        binding.walk3.setOnClickListener {
            val walk2s = LatLng(37.581434, 126.985007)
            googleMap.addMarker(MarkerOptions().position(walk2s).title("📍 북촌한옥마을")
                .snippet("한복 입고 산책은 어때? ✨").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon4)))
            val walk2a = LatLng(37.582320, 126.983560)
            googleMap.addMarker(MarkerOptions().position(walk2a).title("📍 북촌한옥마을")
                .snippet("고즈넉한 한옥을 구경할 수 있어~🍀").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon5)))

            val walk2 = LatLng(37.581906, 126.984023)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(walk2, 17f))

            val polylineOption = PolylineOptions().color(Color.argb(117, 59, 100, 1))
                .add(LatLng(37.581434, 126.985007))
                .add(LatLng(37.581454, 126.984661))
                .add(LatLng(37.581648, 126.984484))
                .add(LatLng(37.581753, 126.984423))
                .add(LatLng(37.581993, 126.983747))
                .add(LatLng(37.582183, 126.983584))
                .add(LatLng(37.582320, 126.983560))

            val polylineOptions = googleMap.addPolyline(polylineOption)
        }

    }

    override fun onStart() {
        super.onStart()
        mView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }
    override fun onDestroy() {
        mView.onDestroy()
        super.onDestroy()
    }

}