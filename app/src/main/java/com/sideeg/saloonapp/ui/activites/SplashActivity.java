package com.sideeg.saloonapp.ui.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.utility.LocalSession;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Runnable runnable = () -> {

        LocalSession localSession = new LocalSession(getApplicationContext());

        Boolean IsSessionCreated = localSession.getIsSessionCreated();;


        if((IsSessionCreated ))
        {

            Intent intent = new Intent(getApplicationContext(), NavActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar =  findViewById(R.id.splash_progresbar);
        progressBar.postDelayed(runnable,3000);
    }
}
