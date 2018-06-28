package com.lychee.ui.main.map

import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lychee.R
import com.lychee.databinding.FragmentMapBinding
import com.lychee.extensions.update
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.main.ActionBarProvider
import kotlinx.android.synthetic.main.fragment_map.*

/**
 *
 */
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(R.layout.fragment_map),
        OnMapReadyCallback {

    override val viewModelClass: Class<MapViewModel>
        get() = MapViewModel::class.java

    var map : GoogleMap? = null

    override fun onCreateView() {
        binding.apply {
            // MAP
            mapView.getMapAsync(this@MapFragment)

            // VIEW PAGER
            itemViewPager.apply {
                val data = MockData.get()
                adapter = MapPagerAdapter(data) // TEST

                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {}

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                    override fun onPageSelected(position: Int) {
                        data[position].apply {
                            val location = LatLng(lat, lng)

                            map?.run {
                                moveCamera(CameraUpdateFactory.newLatLng(location))
                                // IF EXPAND STATE
                                if(isExpanded())
                                    moveCamera(CameraUpdateFactory.scrollBy (0f, mContext.resources.displayMetrics.heightPixels * .25f))
                            }
                        }
                    }
                })
            }
            itemViewPager.adapter = MapPagerAdapter(MockData.get()) // TEST

            // SCALE BUTTON
            scaleButton.setOnClickListener {
                if(isExpanded()) { // IF EXPAND STATE
                    shrink()
                    it.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.reverse_rotaion_animation))
                }
                else { // ELSE IF SHRINK STATE
                    expand()
                    it.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotation_animation))
                }
            }
        }

        (mContext as ActionBarProvider).setActionBarColor(ContextCompat.getColor(mContext, R.color.map_action_bar))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()

        // TODO GET DATA FROM DB
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

    private fun isExpanded() : Boolean = ((guideline.layoutParams as ConstraintLayout.LayoutParams).guidePercent == MAX_LIMIT)

    private fun expand() {
        root_layout.update {
            setGuidelinePercent(R.id.guideline, MAX_LIMIT)

            TransitionManager.beginDelayedTransition(root_layout, ChangeBounds().apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 500
            })
        }

        // MAP
        Handler().postDelayed({
            map?.apply {
                moveCamera(CameraUpdateFactory.newLatLng(LatLng(37.56, 126.97))) // SEOUL
                moveCamera(CameraUpdateFactory.scrollBy (0f, mContext.resources.displayMetrics.heightPixels * .25f))
            }
        }, 250)
    }

    private fun shrink() {
        // CONSTRAINT LAYOUT UPDATE
        root_layout.update {
            setGuidelinePercent(R.id.guideline, MIN_LIMIT)

            TransitionManager.beginDelayedTransition(root_layout, ChangeBounds().apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 500
            })
        }

        // MAP
        Handler().postDelayed({
            map?.apply {
                moveCamera(CameraUpdateFactory.newLatLng(LatLng(37.56, 126.97))) // TODO APPLY DATA'S LATLNG
            }
        }, 250)
    }

    companion object {
        // 최대 확장/축소값
        const val MAX_LIMIT = 0.35f
        const val MIN_LIMIT = 0.8f
    }
}
