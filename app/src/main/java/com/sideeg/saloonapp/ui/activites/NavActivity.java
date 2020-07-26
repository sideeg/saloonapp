package com.sideeg.saloonapp.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.ui.fragment.HomeFragment;
import com.sideeg.saloonapp.utility.LocalSession;

import java.util.Objects;

public class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    private enum FragmentTag {
        HOME

    }
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showFragment(new HomeFragment(),FragmentTag.HOME);

        drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Setting the actionbarToggle to drawer layout
//        drawer.setDrawerListener(actionBarDrawerToggle);
//        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp_w);

        //calling sync state is necessary or else your hamburger icon wont show up
//        actionBarDrawerToggle.syncState();


    }

    private void showFragment(Fragment fragment, FragmentTag tag){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment,tag.name());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        HomeFragment home = (HomeFragment) fragmentManager.findFragmentByTag(FragmentTag.HOME.name());
        if (home != null && home.isVisible()) {
            super.onBackPressed();
        } else {
            showFragment(new HomeFragment(), FragmentTag.HOME);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.call_us:
                makeCall();
                return true;
            case R.id.log_out:
                new LocalSession(getApplicationContext()).clearSession();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void makeCall(){
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel: +966599017761"));

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Objects.requireNonNull(this).checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }


                }else {
                    startActivity(phoneIntent);
                    Log.i("finished making a call=", "");
                }
            }

        } catch (ActivityNotFoundException ex) {

            Toast.makeText(this, "call faild,please try again later.", Toast.LENGTH_SHORT).show();
        }


    }

}
