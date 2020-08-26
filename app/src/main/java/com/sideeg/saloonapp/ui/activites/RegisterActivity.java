package com.sideeg.saloonapp.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.models.LoginResponse;
import com.sideeg.saloonapp.networking.ApiClient;
import com.sideeg.saloonapp.networking.NetWorkApi;
import com.sideeg.saloonapp.utility.LocalSession;
import com.sideeg.saloonapp.utility.ProgressRequestBody;
import com.sideeg.saloonapp.utility.RuntimePermissionHelper;
import com.sideeg.saloonapp.utility.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {

    private String TAG = "RegisterActivity";

    EditText userName,userPhone,userEmail,userPassword;
    ImageView saloonLogoIV,commericalRegisterIV,IdentityCardIV;
    private HashMap<Integer, Uri> imagesUriMap;
    private List<EditText> views;
    Dialog dialog;



    private int GalaryCode = 1777;
    private static final int CAMERA_REQUEST = 1888;
    private Uri imageToUploadUri;
    private int SELECTED_IMAGE_TYPE;
    private LocalSession mLocalSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        initViews();



    }

    private void initViews() {
        userName = findViewById(R.id.regUserName);
        userPassword = findViewById(R.id.regUserPassword);
        userEmail = findViewById(R.id.edtUserEmail);
        userPhone = findViewById(R.id.reg_user_phone);
        saloonLogoIV = findViewById(R.id.iv_saloon_logo_Pic);
        commericalRegisterIV = findViewById(R.id.ivcommerical_register_Pic);
        IdentityCardIV = findViewById(R.id.ivIdentityCard_Pic);
        Button register = findViewById(R.id.btnregister);
        register.setOnClickListener(e -> sendToServer());

        views = new ArrayList<>();
        views.add(userName);
        views.add(userPhone);
        views.add(userEmail);
        views.add(userPassword);

        imagesUriMap = new HashMap<>();

        saloonLogoIV.setOnClickListener(e -> imagePicker(R.id.iv_saloon_logo_Pic));
        commericalRegisterIV.setOnClickListener(e -> imagePicker(R.id.ivcommerical_register_Pic));
        IdentityCardIV.setOnClickListener(e -> imagePicker(R.id.ivIdentityCard_Pic));
    }


    private void imagePicker(int IMAGE_TYPE_CODE) {
        SELECTED_IMAGE_TYPE = IMAGE_TYPE_CODE;
        if (Build.VERSION.SDK_INT >= 23) {
            RuntimePermissionHelper runtimePermissionHelper = RuntimePermissionHelper.getInstance( this);
            if (!runtimePermissionHelper.isAllPermissionAvailable()) {
                runtimePermissionHelper.setActivity(this);
                runtimePermissionHelper.requestPermissionsIfDenied();
            }
            Log.i(TAG, "ungraded permission: " + runtimePermissionHelper.getUnGrantedPermissionsList().toString());
        }

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_setting_layout);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.findViewById(R.id.camera).setOnClickListener(view -> getImageFromCamera());
        dialog.findViewById(R.id.gallery).setOnClickListener(view -> getImageFromGalary());
        dialog.findViewById(R.id.cancel).setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void getImageFromCamera() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
       // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        imageToUploadUri = Uri.fromFile(f);
        if (RuntimePermissionHelper.getInstance(this).isPermissionAvailable(Manifest.permission.CAMERA))
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        else {
            RuntimePermissionHelper.getInstance(this).requestPermissionsIfDenied(Manifest.permission.CAMERA);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void getImageFromGalary(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,GalaryCode);
    }
    String saloonlogopicturePath;
    String commericalpicturePath;
    String idpicturePath;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (dialog.isShowing())
            dialog.dismiss();
        Log.v("imageTage", "onActivityResult");
        if ( resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                if (imageToUploadUri != null) {
                    Uri uri = Uri.parse(imageToUploadUri.getPath());
                    switch (SELECTED_IMAGE_TYPE) {
                        case R.id.ivcommerical_register_Pic:
                            commericalpicturePath = uri.getPath();
                            imagesUriMap.put(R.id.ivcommerical_register_Pic, uri);
                            commericalRegisterIV.setImageURI(uri);

//                            commericalRegisterIV.setImageURI(uri);
//                            commericalRegisterIV.invalidate();
                            break;
                        case R.id.ivIdentityCard_Pic:
                            idpicturePath = uri.getPath();
                            imagesUriMap.put(R.id.ivIdentityCard_Pic, uri);
                            IdentityCardIV.setImageURI(uri);
                            break;
                        case R.id.iv_saloon_logo_Pic:
                            saloonlogopicturePath = uri.getPath();
                            imagesUriMap.put(R.id.iv_saloon_logo_Pic, uri);
                            saloonLogoIV.setImageURI(uri);
                            break;
                    }

                }

            }else  if (requestCode == GalaryCode){
                Uri uri = data.getData();
                Cursor cursor;
                switch (SELECTED_IMAGE_TYPE) {
                    case R.id.iv_saloon_logo_Pic:
                        imagesUriMap.put(R.id.iv_saloon_logo_Pic, uri);
                        saloonLogoIV.setImageURI(uri);
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        cursor = getContentResolver().query(uri,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        saloonlogopicturePath = cursor.getString(columnIndex);
                        cursor.close();
                        break;
                    case R.id.ivcommerical_register_Pic:
                        imagesUriMap.put(R.id.ivcommerical_register_Pic, uri);
//                        Picasso.get().load(data.getData()).fit().into(iv_insurance_Pic);
                        commericalRegisterIV.setImageURI(uri);

                        String[] filePathColumn2 = { MediaStore.Images.Media.DATA };

                        cursor = getContentResolver().query(uri,
                                filePathColumn2, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex2 = cursor.getColumnIndex(filePathColumn2[0]);
                        commericalpicturePath = cursor.getString(columnIndex2);
                        cursor.close();

                        break;

                    case R.id.ivIdentityCard_Pic:
                        imagesUriMap.put(R.id.ivIdentityCard_Pic, uri);
//                        Picasso.get().load(data.getData()).fit().into(iv_insurance_Pic);
                        IdentityCardIV.setImageURI(uri);

                        String[] filePathColumn3 = { MediaStore.Images.Media.DATA };

                        cursor = getContentResolver().query(uri,
                                filePathColumn3, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex3 = cursor.getColumnIndex(filePathColumn3[0]);
                        idpicturePath = cursor.getString(columnIndex3);
                        cursor.close();

                        break;
                }
            }
        }
    }

    private Boolean valdiateData() {
        if (imagesUriMap.get(R.id.iv_saloon_logo_Pic) == null || imagesUriMap.get(R.id.ivIdentityCard_Pic) == null
                ||imagesUriMap.get(R.id.ivcommerical_register_Pic) == null  ) {

            Utility.showAlertDialog(getString(R.string.warring), getString(R.string.insert_image), this);
            return false;
        }

        for (EditText editText : views) {
            if (editText.getText().toString().equals("")) {
                editText.setError("Please enter " + editText.getHint());
                editText.requestFocus();
                return false;
            }
        }
        return true;
    }



    private void sendToServer() {
        if (!valdiateData()){
            return;
        }
        ProgressDialog loading =  ProgressDialog.show(this, getString(R.string.loading), getString(R.string.wait), false, false);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        File saloonLogoFile = new File(saloonlogopicturePath);
        File commericalFile = new File(commericalpicturePath);
        File idFile = new File(idpicturePath);

        mLocalSession=new LocalSession(getApplicationContext());
        NetWorkApi api = ApiClient.getClient(ApiClient.BASE_URL).create(NetWorkApi.class);
        Call<LoginResponse> loginCall = api.registerSaloon(userPhone.getText().toString(),userName.getText().toString(),userEmail.getText().toString(),userPassword.getText().toString()
        ,"23","43", MultipartBody.Part.createFormData("commerical_regstration_image", "commerical_regstration_image", new ProgressRequestBody(commericalFile, "image", this))
        ,MultipartBody.Part.createFormData("saloon_logo", "saloon_logo", new ProgressRequestBody(saloonLogoFile, "image", this))
        ,MultipartBody.Part.createFormData("photo_id", "photo_id", new ProgressRequestBody(idFile, "image", this)));

        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isError()) {
                        loading.dismiss();
                        Utility.showAlertDialog(getString(R.string.error), response.body().getMessage(), RegisterActivity.this);

                    } else {
                        Log.i(TAG, "token: " +  response.body().getData().toString());

                        loading.dismiss();
                        mLocalSession.createSession(true
                                ,response.body().getData().getPhone()
                                ,response.body().getData().getName()
                                ,response.body().getData().getEmail()
                                ,response.body().getData().getId()
                                ,response.body().getData().getPhoto_id_full_path()
                                ,response.body().getData().getCommerical_regstration_image_full_path()
                                ,response.body().getData().getLat()
                                ,response.body().getData().getLan()
                                ,response.body().getData().getSaloon_logo_full_path());

                        startActivity(new Intent(getApplicationContext(), NavActivity.class));
                        finish();

                    }
                } else {
                    Log.i(TAG, response.errorBody().toString());
                    loading.dismiss();
                    Utility.showAlertDialog(getString(R.string.error), getString(R.string.servererror), RegisterActivity.this);

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Utility.showAlertDialog(getString(R.string.error), t.getMessage(), RegisterActivity.this);
                Utility.printLog(TAG, t.getMessage());
                loading.dismiss();
            }
        });
    }

    @Override
    public void onProgressUpdate(File mFile, int percentage) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }
}
