package com.sideeg.saloonapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalSession {

    private static final String PREF_NAME = "Driver SettingsData";
    private static final String ID = "id";
    private static final String NAME = "Name";
    private static final String PHONE = "Phone";
    private static final String clientEmail = "clientEmail";
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor editor;
    //keys
    private static final String PHOTO_ID_FULL_PATH = "photo_id_full_path";
    private static final String isSessionCreated = "isSessionCreated";
    private static final String lat = "lat";
    private static final String lng = "lng";
    private static final String user_name = "user_name";
    private static final String REGSTRATION_IMAGE_FULL_PATH = "commerical_regstration_image_full_path";
    private static final String SALOON_LOGO_FULL_PATH = "saloon_logo_full_path";


    public void createSession(Boolean isSessionCreated,String phone,String userName, String saloonEmail, String Id, String photo_id_full_path,String commerical_regstration_image_full_path,String lat,String lng
    ,String saloon_logo_full_path) {
        editor.putBoolean(LocalSession.isSessionCreated, isSessionCreated);
        editor.putString(LocalSession.user_name,userName);
        editor.putString(LocalSession.PHONE,phone);
        editor.putString(LocalSession.clientEmail,saloonEmail);
        editor.putString(LocalSession.ID, Id);
        editor.putString(LocalSession.PHOTO_ID_FULL_PATH, photo_id_full_path);
        editor.putString(LocalSession.REGSTRATION_IMAGE_FULL_PATH, commerical_regstration_image_full_path);
        editor.putString(LocalSession.lat, lat);
        editor.putString(LocalSession.lng, lng);
        editor.putString(LocalSession.SALOON_LOGO_FULL_PATH, saloon_logo_full_path);
        editor.apply();
        editor.commit();
    }




    public static void setDeviceId(String deviceId) {
        editor.putString("DEVICE_ID", deviceId);
        editor.commit();
    }





    public static String getSaloonLogoFullPath() {
        return mPreferences.getString(SALOON_LOGO_FULL_PATH,"");
    }

    public static String getRegstrationImageFullPath() {
        return mPreferences.getString(REGSTRATION_IMAGE_FULL_PATH,"");
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


    public LocalSession(Context context) {
        mPreferences = context.getSharedPreferences(PREF_NAME, 0);

        editor = mPreferences.edit();
    }

    public LocalSession(Context context, String PHOTO_ID_FULL_PATH) {
        mPreferences = context.getSharedPreferences(PREF_NAME, 0);

        editor = mPreferences.edit();
        setPhotoIdFullPath(PHOTO_ID_FULL_PATH);
    }
    public Boolean getIsSessionCreated() {
        return mPreferences.getBoolean(LocalSession.isSessionCreated, false);
    }
    public static SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public static String getPhone() {
        return mPreferences.getString(PHONE,"");
    }



    public static String getId() {
        return mPreferences.getString(ID,"");
    }

    public static String getName() {
        return mPreferences.getString(NAME,"");
    }

    public static String getClientEmail() {
        return mPreferences.getString(clientEmail,"");
    }

    public static String getPhotoIdFullPath() {
        return mPreferences.getString(PHOTO_ID_FULL_PATH,"");
    }


    public static void setPhotoIdFullPath(String token1) {
        editor.putString(PHOTO_ID_FULL_PATH,token1);
        editor.commit();
        editor.apply();
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
        editor.commit();


    }


}
