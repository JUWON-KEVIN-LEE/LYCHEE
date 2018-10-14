package com.lychee.ui.add

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.ui.PlacePicker
import com.lychee.R
import com.lychee.data.core.model.Place
import com.lychee.databinding.ActivityAddBinding
import com.lychee.ui.add.adapter.image.AddImageRecyclerViewAdapter
import com.lychee.ui.add.adapter.place.AddPlaceRecyclerViewAdapter
import com.lychee.ui.base.ui.BaseActivity
import com.lychee.util.extensions.gone
import com.lychee.util.extensions.visible
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import javax.inject.Inject

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_add

    override val viewModelClass: Class<AddViewModel>
        get() = AddViewModel::class.java

    private val permissions= arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    )

    private val locationPermission = android.Manifest.permission.ACCESS_FINE_LOCATION

    private var permissionsGranted: Boolean = false

    /* MOVE TO GPS PROVIDER SETTING */
    private var locationPermissionGranted: Boolean = false

    @Inject lateinit var mPlaceDetectionClient: PlaceDetectionClient

    private val mToday = Calendar.getInstance()

    private val mOnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        val isToday = (year == mToday.get(Calendar.YEAR)
                && month == mToday.get(Calendar.MONTH)
                && dayOfMonth == mToday.get(Calendar.DATE))

        mBinding.addDateTextView.text = if(isToday) "오늘" else "${year}년 ${month + 1}월 ${dayOfMonth}일"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(permissionsGranted || EasyPermissions.hasPermissions(this@AddActivity, *permissions)) {
            permissionsGranted = true
        }

        if(locationPermissionGranted ||
                (EasyPermissions.hasPermissions(this@AddActivity, locationPermission) &&
                        (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.GPS_PROVIDER))) {
            locationPermissionGranted = true

            findCurrentPlaces()
        } else {
            requestLocationPermission()
        }

        initView()

        if(permissionsGranted) loadAllImagesFromGallery()

        // if(locationPermissionGranted)
    }

    private fun initView() {
        with(mBinding) {
            /**
             * 뒤로 가기 버튼
             */
            addBackButton.setOnClickListener { finish() }

            /**
             * 날짜 선택
             */
            addDateLayout.setOnClickListener {
                val initial = addDateTextView.text == "오늘"

                val year: Int
                val month: Int
                val dayOfMonth: Int

                if(initial) {
                    year = mToday.get(Calendar.YEAR)
                    month = mToday.get(Calendar.MONTH)
                    dayOfMonth = mToday.get(Calendar.DATE)
                } else {
                    val d = addDateTextView.text.split(". ")

                    year = d[0].toInt()
                    month = d[1].toInt() - 1
                    dayOfMonth = d[2].toInt()
                }

                AddDatePicker.newInstance(mOnDateSetListener, year, month, dayOfMonth, initial)
                        .show(supportFragmentManager, AddDatePicker.TAG)
            }

            /**
             * 장소 선택
             */
            addPlaceLayout.setOnClickListener {
                try {
                    val intent = PlacePicker.IntentBuilder().build(this@AddActivity)

                    startActivityForResult(intent, PLACE_PICKER_REQUEST_CODE)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }

            addPlaceRecyclerView.apply {
                adapter = AddPlaceRecyclerViewAdapter(context)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            /**
             * 메모
             */
            addMemoLayout.setOnClickListener {

            }

            /**
             * 사진 선택
             */
            addPhotoLayout.setOnClickListener {
                if(permissionsGranted || EasyPermissions.hasPermissions(this@AddActivity, *permissions)) {
                    permissionsGranted = true

                    selectImagesFromGallery()
                } else {
                    requestPermissions()
                }
            }

            addPhotoRecyclerView.apply {
                adapter = AddImageRecyclerViewAdapter(this@AddActivity)
                layoutManager = LinearLayoutManager(this@AddActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun loadAllImagesFromGallery() {
        val images = mutableListOf<String>()

        val isSDCardAvailable = android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED

        val uri = if(isSDCardAvailable) MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        else MediaStore.Images.Media.INTERNAL_CONTENT_URI

        val projections = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA)

        val cursor = contentResolver.query(
                uri,
                projections,
                null,
                null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC")

        with(cursor) {
            if(cursor.count > 0) {
                for(index in 0 until count) {
                    cursor.moveToPosition(index)

                    val id = getString(getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val uri = getString(getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                    images.add(uri)
                }

                (mBinding.addPhotoRecyclerView.adapter as? AddImageRecyclerViewAdapter)?.images = images
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun findCurrentPlaces() {
        mPlaceDetectionClient.getCurrentPlace(null)
                .addOnCompleteListener { task ->
                    with(task) {
                        if(isSuccessful && result != null) {
                            val places = mutableListOf<Place>()

                            for(placeLikelihood in result) {
                                with(placeLikelihood.place) {
                                    places.add(
                                            Place(
                                                    name = name?.toString(),
                                                    address = address?.toString(),
                                                    latitude = latLng.latitude,
                                                    longitude = latLng.longitude)
                                    )
                                }
                            }

                            (mBinding.addPlaceRecyclerView.adapter as? AddPlaceRecyclerViewAdapter)
                                    ?.places = places
                        } else {
                            mBinding.addPlaceRecyclerView.gone()
                        }
                    }
                }
    }

    private fun selectImagesFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_FROM_GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                PICK_IMAGES_FROM_GALLERY_REQUEST_CODE -> {

                }
                TAKE_PHOTO_REQUEST_CODE -> {

                }
                PLACE_PICKER_REQUEST_CODE -> {
                    val place = PlacePicker.getPlace(this, data)

                    with(mBinding) {
                        addPlaceRecyclerView.gone()

                        addPlaceResultCardView.visible()
                        addPlaceResultTextView.text = "${place.name}\n${place.address}"
                    }
                }
            }
        }
    }

    private fun requestPermissions() {
        EasyPermissions.requestPermissions(this,
                "사진을 등록하기 위해서는 권한을 승인해주세요.",
                IMAGE_PERMISSION_REQUEST_CODE,
                *permissions)
    }

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(this,
                "위치 서비스를 사용하기 위해서는 권한을 승인해주세요.",
                IMAGE_PERMISSION_REQUEST_CODE,
                locationPermission)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                object: EasyPermissions.PermissionCallbacks {
                    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

                    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
                        when(requestCode) {
                            IMAGE_PERMISSION_REQUEST_CODE -> {
                                permissionsGranted = true

                                loadAllImagesFromGallery()
                            }

                            LOCATION_PERMISSION_REQUEST_CODE -> {
                                locationPermissionGranted =
                                        (getSystemService(Context.LOCATION_SERVICE) as LocationManager)
                                                .isProviderEnabled(LocationManager.GPS_PROVIDER)

                                if(locationPermissionGranted) findCurrentPlaces()
                            }
                        }
                    }

                    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {}
                })
    }

    companion object {
        const val PLACE_PICKER_REQUEST_CODE = 2

        private const val PICK_IMAGES_FROM_GALLERY_REQUEST_CODE = 8

        const val TAKE_PHOTO_REQUEST_CODE = 9

        /* PERMISSION REQUEST CODE */
        private const val IMAGE_PERMISSION_REQUEST_CODE = 10

        private const val LOCATION_PERMISSION_REQUEST_CODE = 11
    }
}
