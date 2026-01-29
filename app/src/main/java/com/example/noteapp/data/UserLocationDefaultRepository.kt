package com.example.noteapp.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import android.location.Location
import com.example.noteapp.exceptions.UnknownLocationErrorException
import com.example.noteapp.exceptions.LocationIsDisabledException
import com.example.noteapp.exceptions.NoLocationPermissionException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject


class UserLocationDefaultRepository @Inject constructor(): UserLocationRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ])
    override suspend fun getUserLocation(context: Context): NoteLocation {
        val client = LocationServices.getFusedLocationProviderClient(context)

        if (!isLocationPermissionGranted(context)) throw NoLocationPermissionException()
        if (!isLocationEnabled(context)) throw LocationIsDisabledException()

        val location = suspendCancellableCoroutine<Location?> { cont ->
            client.lastLocation
                .addOnSuccessListener { loc -> cont.resume(loc) {} }
                .addOnFailureListener { cont.resume(null) {} }
                .addOnCanceledListener { cont.cancel() }
        } ?: throw UnknownLocationErrorException()

        return NoteLocation(location.latitude, location.longitude)
    }



    private fun isLocationPermissionGranted(context: Context): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fine || coarse
    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationManger = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        return locationManger.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
    }
}