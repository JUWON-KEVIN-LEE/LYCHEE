package com.lychee.ui.main.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lychee.R
import com.lychee.databinding.FragmentMapBinding
import com.lychee.extensions.update
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.main.ActionBarProvider
import kotlinx.android.synthetic.main.fragment_map.*

/**
 *
 */
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(R.layout.fragment_map),
        OnMapReadyCallback, TouchEventListener {

    override val viewModelClass: Class<MapViewModel>
        get() = MapViewModel::class.java

    lateinit var map : GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
        = super.onCreateView(inflater, container, savedInstanceState)


    @SuppressLint("ClickableViewAccessibility")
    override fun init() {
        binding.apply {
            // MAP
            mapView.getMapAsync(this@MapFragment)

            // DETAIL
            val handler = TouchEventHandler(mContext, this@MapFragment)
            itemLayout.setOnTouchListener(handler)
        }

        (mContext as ActionBarProvider).setActionBarColor(ContextCompat.getColor(mContext, R.color.map_action_bar))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(map : GoogleMap) {
        this.map = map

        map.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL

            uiSettings.apply {
                isScrollGesturesEnabled = true
                isZoomGesturesEnabled = true
            }

            val seoul = LatLng(37.56, 126.97)

            val marker = MarkerOptions().apply {
                position(seoul)
                title("SEOUL")
            }

            addMarker(marker).showInfoWindow() // Marker 추가 및 화면 출력
            moveCamera(CameraUpdateFactory.newLatLng(seoul))
            animateCamera(CameraUpdateFactory.zoomTo(13f))
        }
    }

    override fun moveUp(per: Float) {
        (guideline.layoutParams as ConstraintLayout.LayoutParams)
                .takeIf { it.guidePercent > TouchEventHandler.MAX_LIMIT }
                ?.let {
                    it.guidePercent += per
                    guideline.layoutParams = it
                }

        // TODO HANDLING OTHER VIEWS VISIBILITY OR TRANSITION
    }

    override fun moveDown(per: Float) {
        (guideline.layoutParams as ConstraintLayout.LayoutParams)
                .takeIf { it.guidePercent < TouchEventHandler.MIN_LIMIT }
                ?.let {
                    it.guidePercent += per
                    guideline.layoutParams = it
                }
    }

    override fun expand() {
        // CONSTRAINT LAYOUT UPDATE
        root_layout.update {
            setGuidelinePercent(R.id.guideline, TouchEventHandler.MAX_LIMIT)

            TransitionManager.beginDelayedTransition(root_layout, ChangeBounds().apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 500
            })
        }

        // MAP
        Handler().postDelayed({
            // TODO 일단 POINT 로 정중앙 이동한 다음
            // 아래와 같이 축소
            map.moveCamera(CameraUpdateFactory.scrollBy (0f, mContext.resources.displayMetrics.heightPixels * .25f))
        }, 250)
    }

    override fun shrink() {
        // CONSTRAINT LAYOUT UPDATE
        root_layout.update {
            setGuidelinePercent(R.id.guideline, TouchEventHandler.MIN_LIMIT)

            TransitionManager.beginDelayedTransition(root_layout, ChangeBounds().apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 500
            })
        }

        // MAP
        Handler().postDelayed({
            map.moveCamera(CameraUpdateFactory.scrollBy (0f, -mContext.resources.displayMetrics.heightPixels * .25f))
        }, 250)
    }
}
