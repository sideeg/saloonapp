package com.sideeg.saloonapp.utility;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.widget.Toast;


import com.sideeg.saloonapp.R;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public final class RuntimePermissionHelper {

    private static RuntimePermissionHelper runtimePermissionHelper;
    public static final int PERMISSION_REQUEST_CODE = 1;
    private Activity activity;
    private ArrayList<String> requiredPermissions;
    private ArrayList<String> ungrantedPermissions = new ArrayList<>();

    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;

    private RuntimePermissionHelper(Activity activity)  {
        this.activity = activity;
    }

    public static synchronized RuntimePermissionHelper getInstance(Activity activity){
        if(runtimePermissionHelper == null) {
            runtimePermissionHelper = new RuntimePermissionHelper(activity);
        }
        return runtimePermissionHelper;
    }


    private void initPermissions() {
        requiredPermissions = new ArrayList<>();
        requiredPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        requiredPermissions.add(Manifest.permission.CAMERA);
        requiredPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requiredPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        //Add all the required permission in the list
    }

    public void requestPermissionsIfDenied(){
        ungrantedPermissions = getUnGrantedPermissionsList();
        if(canShowPermissionRationaleDialog()){
            showMessageOKCancel(activity.getResources().getString(R.string.permission_message),
                    (dialog, which) -> askPermissions());
            return;
        }
        askPermissions();
    }

    public void requestPermissionsIfDenied(final String permission){
        if(canShowPermissionRationaleDialog(permission)){
            showMessageOKCancel(activity.getResources().getString(R.string.permission_message),
                    (dialog, which) -> askPermission(permission));
            return;
        }
        askPermission(permission);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public boolean canShowPermissionRationaleDialog() {
        boolean shouldShowRationale = false;
        for(String permission: ungrantedPermissions) {
            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if(shouldShow) {
                shouldShowRationale = true;
            }
        }
        return shouldShowRationale;
    }

    public boolean canShowPermissionRationaleDialog(String permission) {
        boolean shouldShowRationale = false;
            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if(shouldShow) {
                shouldShowRationale = true;
        }
        return shouldShowRationale;
    }

    private void askPermissions() {
        if(ungrantedPermissions.size()>0) {
            ActivityCompat.requestPermissions(activity, ungrantedPermissions.toArray(new String[ungrantedPermissions.size()]), PERMISSION_REQUEST_CODE);
        }
    }

    private void askPermission(String permission) {
            ActivityCompat.requestPermissions(activity, new String[] {permission}, PERMISSION_REQUEST_CODE);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
//                        Intent intent = new Intent(activity,ErrorActivity.class);
//                        intent.putExtra("permissions_denied",true);
//                        activity.startActivity(intent);
//                        activity.finish();
                    Toast.makeText(activity, "some function well not work", Toast.LENGTH_SHORT).show();
                })
                .create()
                .show();
    }


    public boolean isAllPermissionAvailable() {
        boolean isAllPermissionAvailable = true;
        initPermissions();
        for(String permission : requiredPermissions){
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
                isAllPermissionAvailable = false;
                break;
            }
        }
        return isAllPermissionAvailable;
    }

    public ArrayList<String> getUnGrantedPermissionsList() {
        ArrayList<String> list = new ArrayList<>();
        for(String permission: requiredPermissions) {
            int result = ActivityCompat.checkSelfPermission(activity, permission);
            if(result != PackageManager.PERMISSION_GRANTED) {
                list.add(permission);
            }
        }
        return list;
    }

    public boolean isPermissionAvailable(String permission) {
        boolean isPermissionAvailable = true;
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
                isPermissionAvailable = false;
            }
        return isPermissionAvailable;
    }
}