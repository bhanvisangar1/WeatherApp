package com.weather.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by bhanvisangar on 11/26/16.
 */

public class PermissionHelper {

    public static boolean hasPermission(Context context, String permission){
        return (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);

    }

    public static void requestPermission(Activity activity, String permission, int requestCode){
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);

    }
}
