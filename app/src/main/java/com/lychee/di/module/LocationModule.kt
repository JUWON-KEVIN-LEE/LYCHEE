package com.lychee.di.module

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.lychee.di.scope.ApplicationScoped
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides @ApplicationScoped
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient
            = LocationServices.getFusedLocationProviderClient(context)

    @Provides @ApplicationScoped
    fun provideGeocoder(context: Context): Geocoder = Geocoder(context)

    @Provides @ApplicationScoped
    fun provideGeoDataClient(context: Context): GeoDataClient = Places.getGeoDataClient(context)

    @Provides @ApplicationScoped
    fun providePlaceDetectionClient(context: Context): PlaceDetectionClient = Places.getPlaceDetectionClient(context)
}