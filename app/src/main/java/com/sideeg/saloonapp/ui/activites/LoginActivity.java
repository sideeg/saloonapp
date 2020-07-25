package com.sideeg.saloonapp.ui.activites;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.models.LoginResponse;
import com.sideeg.saloonapp.networking.ApiClient;
import com.sideeg.saloonapp.networking.NetWorkApi;
import com.sideeg.saloonapp.utility.LocalSession;
import com.sideeg.saloonapp.utility.Utility;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText edtUserName,edtUserPassword;
    private LocalSession mLocalSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserName = findViewById(R.id.edtUserName);
        edtUserPassword = findViewById(R.id.edtUserPassword);

        Button loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(view -> {
           login();
        });
    }

    public void registerClick(View view) {
        startActivity(new Intent(getBaseContext(),RegisterActivity.class));
    }

    private void login() {
        ProgressDialog loading = Utility.GetProcessDialog(this);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        mLocalSession=new LocalSession(getApplicationContext());
        NetWorkApi api = ApiClient.getClient(ApiClient.BASE_URL).create(NetWorkApi.class);
        Call<LoginResponse> loginCall = api.pharmacyLogin(edtUserName.getText().toString(),edtUserPassword.getText().toString());
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isError()) {
                        loading.dismiss();
                        Utility.showAlertDialog(getString(R.string.error), response.body().getMessage(), LoginActivity.this);

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
                    Utility.showAlertDialog(getString(R.string.error), getString(R.string.servererror), LoginActivity.this);

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Utility.showAlertDialog(getString(R.string.error), t.getMessage(), LoginActivity.this);
                Utility.printLog(TAG, t.getMessage());
                loading.dismiss();
            }
        });
    }
}
