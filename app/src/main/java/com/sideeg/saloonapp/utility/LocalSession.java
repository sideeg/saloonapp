package com.sideeg.saloonapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalSession {

    private static final String PREF_NAME = "Driver SettingsData";
    private static final String pharmacyId = "pharmacy_id";
    private static final String pharmacyName = "pharmacyName";
    private static final String pharmacyPhone = "pharmacyPhone";
    private static final String clientEmail = "clientEmail";
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor editor;
    //keys
    private static final String token = "api_token";
    private static final String isSessionCreated = "isSessionCreated";
    private static final String isFcmSaved ="isFcmSaved";
    private static final String isProfileCreated = "isProfileCreated";
    private static final String personalPhotoPath = "personalPhotoPath";
    private static final String lat = "lat";
    private static final String lng = "lng";
    private static final String user_name = "user_name";
    private static final String license_no = "license_no";
    private static final String pharmacyStatus = "status";
    public static final String phoneOtp = "otp";
    private static final String fcmToken = "token";
    private static final String orderId ="order_id";
    private static final String isLastOrderIdSaved ="isLastOrderIdSaved";
    private static final String ORDER_STATUS = "status";
    public void createSession(Boolean isSessionCreated,String pharmacyName,String phone,String userName, String clientEmail, String pharmacyId, String clintToken,String licenseNo,String lat,String lng
    ,String status) {
        editor.putBoolean(LocalSession.isSessionCreated, isSessionCreated);
        editor.putString(LocalSession.pharmacyName,pharmacyName);
        editor.putString(LocalSession.user_name,userName);
        editor.putString(LocalSession.pharmacyPhone,phone);
        editor.putString(LocalSession.clientEmail,clientEmail);
        editor.putString(LocalSession.pharmacyId, pharmacyId);
        editor.putString(LocalSession.token, clintToken);
        editor.putString(LocalSession.license_no, licenseNo);
        editor.putString(LocalSession.lat, lat);
        editor.putString(LocalSession.lng, lng);
        editor.putString(LocalSession.pharmacyStatus, status);
        editor.apply();
        editor.commit();
    }
    public void createProfile(Boolean isProfileCreated,String pharmacyName,String phone,String user_name, String clientEmail, String clintToken, String personalPhotoPath,String lat,String lng,String license_no) {
        editor.putBoolean(LocalSession.isProfileCreated, isProfileCreated);
        editor.putString(LocalSession.pharmacyName,pharmacyName);
        editor.putString(LocalSession.clientEmail,clientEmail);
        editor.putString(LocalSession.token, clintToken);
        editor.putString(LocalSession.pharmacyPhone, phone);
        editor.putString(LocalSession.personalPhotoPath, personalPhotoPath);
        editor.putString(LocalSession.lat, lat);
        editor.putString(LocalSession.lng, lng);
        editor.putString(LocalSession.user_name, user_name);
        editor.putString(LocalSession.license_no, license_no);
        editor.apply();
        editor.commit();
    }

    public void saveOrderId(Boolean isIdSaved,String orderId){
        editor.putBoolean(isLastOrderIdSaved,isIdSaved);
        editor.putString(orderId,orderId);
    }

    public int getOrderStatus() {
        return mPreferences.getInt(ORDER_STATUS, 4);
    }

    public void setOrderStatus(int value) {
        editor.putInt(ORDER_STATUS, value);
        editor.apply();
        //	editor.commit();
    }
    public static String getOrderId() {
        return mPreferences.getString(orderId,"");
    }

    public static void setDeviceId(String deviceId) {
        editor.putString("DEVICE_ID", deviceId);
        editor.commit();
    }

    public static String getDeviceId() {
        return mPreferences.getString("DEVICE_ID", null);
    }
    public void saveFcm(Boolean isFcmSaved,String token){
        editor.putBoolean(LocalSession.isFcmSaved,isFcmSaved);
        editor.putString(LocalSession.fcmToken,token);
        editor.apply();
        editor.commit();
    }


    public static String getFcmToken() {
        return mPreferences.getString(fcmToken,"");
    }

    public static String getPharmacyStatus() {
        return mPreferences.getString(pharmacyStatus,"");
    }

    public static String getLicense_no() {
        return mPreferences.getString(license_no,"");
    }

    public static String getLat() {
        return mPreferences.getString(lat,"");
    }

    public static String getLng() {
        return mPreferences.getString(lng,"");
    }

    public static String getPrefName() {
        return PREF_NAME;
    }

    public static String getUser_name() {
        return mPreferences.getString(user_name,"");
    }

    public static String getPersonalPhotoPath() {
        return mPreferences.getString(personalPhotoPath,"");
    }

    public LocalSession(Context context) {
        mPreferences = context.getSharedPreferences(PREF_NAME, 0);

        editor = mPreferences.edit();
    }

    public LocalSession(Context context, String token) {
        mPreferences = context.getSharedPreferences(PREF_NAME, 0);

        editor = mPreferences.edit();
        setToken(token);
    }
    public Boolean getIsSessionCreated() {
        return mPreferences.getBoolean(LocalSession.isSessionCreated, false);
    }
    public static SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public static String getPharmacyPhone() {
        return mPreferences.getString(pharmacyPhone,"");
    }


    public static String getIsProfileCreated() {
        return isProfileCreated;
    }

    public static String getPharmacyId() {
        return mPreferences.getString(pharmacyId,"");
    }

    public static String getPharmacyName() {
        return mPreferences.getString(pharmacyName,"");
    }

    public static String getClientEmail() {
        return mPreferences.getString(clientEmail,"");
    }

    public static String getToken() {
        return mPreferences.getString(token,"");
    }


    public static void setToken(String token1) {
        editor.putString(token,token1);
        editor.commit();
        editor.apply();
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
        editor.commit();

    }


}
