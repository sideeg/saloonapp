package com.sideeg.saloonapp.ui.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.ui.fragment.HomeFragment;

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
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp_w);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


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
}
