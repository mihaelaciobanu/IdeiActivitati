package com.example.ideiactivitati.ui.formular

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.ideiactivitati.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.*
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng


class LocalizareService: Service() {

    var coordonate : LatLng? = null
    lateinit var client : FusedLocationProviderClient
    val channelName = "Activitatile mele"
    val channelId = "ActivitatileMele"

    val locationRequest = create().apply {
        interval = 1000
        fastestInterval=1000
        priority = PRIORITY_HIGH_ACCURACY
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            Log.i("Localizare", "${p0.lastLocation.latitude}, ${p0.lastLocation.longitude}")
            coordonate = LatLng(p0.lastLocation.latitude, p0.lastLocation.longitude)

        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.i("Localizare", "onCreate")

        client = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate()
    }

    fun afiseazaNotificare() {
        val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Activitatile mele")
            .setContentText("Se preiau coordonatele traseului...")
            .setSmallIcon(R.drawable.ic_menu_camera)
            .build()

        startForeground(1, notification)
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        afiseazaNotificare()

        Log.i("Localizare", "onStartCommand")

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } else {
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.i("Localizare", "onDestroy")
        client.removeLocationUpdates(locationCallback)

        // trimit lista cu coordonate prin broadcast
        val intent = Intent("TRIMITE_COORD")
        intent.putExtra("locatie", coordonate)

        sendBroadcast(intent)

        super.onDestroy()
    }
}
