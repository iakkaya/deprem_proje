package com.example.deprem_proje.Location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permission {
    private Context context;
    private LocationPermissionListener listener;

    public interface LocationPermissionListener {
        void onPermissionGranted();
        void onPermissionDenied();
    }

    public Permission(Context context, LocationPermissionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public boolean checkLocationPermission() {
        if (hasLocationPermission()) {
            listener.onPermissionGranted();
            return  true;
        } else {
            requestLocationPermission();
            return  false;
        }
    }

    private boolean hasLocationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        int permissionState = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // İzin reddedildi ancak kullanıcıya neden gerekli olduğu anlatıldı. İzin isteği tekrar gösterilebilir.
            listener.onPermissionDenied();
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                listener.onPermissionGranted();
            } else {
                listener.onPermissionDenied();
            }
        }
    }

}
