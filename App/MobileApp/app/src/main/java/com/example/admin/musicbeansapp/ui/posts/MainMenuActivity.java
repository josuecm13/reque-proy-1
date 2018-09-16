package com.example.admin.musicbeansapp.ui.posts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.admin.musicbeansapp.UserLoginActivity;
import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.ui.LoginActivity;
import com.example.admin.musicbeansapp.ui.posts.fragments.BandListFragment;
import com.example.admin.musicbeansapp.ui.posts.fragments.FavBandFragment;
import com.example.admin.musicbeansapp.ui.posts.fragments.PostFragment;


public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PostFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_posts_client);
        }


    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_posts_client:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PostFragment()).commit();
                break;
            case R.id.nav_favband_client:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavBandFragment()).commit();
                break;
            case R.id.nav_bandlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BandListFragment()).commit();
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(getApplicationContext(),
                        UserLoginActivity.class);
                startActivityForResult(intent,0);
        }
        return true;
    }
}
