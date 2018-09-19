package com.example.admin.musicbeansapp.ui.posts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.UserLoginActivity;
import com.example.admin.musicbeansapp.ui.bands.BandProfile;
import com.example.admin.musicbeansapp.ui.posts.fragments.BandListAdminFragment;
import com.example.admin.musicbeansapp.ui.posts.fragments.ClientListAdminFragment;
import com.example.admin.musicbeansapp.ui.posts.fragments.FavBandFragment;
import com.example.admin.musicbeansapp.ui.posts.fragments.PostAdminFragment;


public class MainMenuBand extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        PostAdminFragment.OnFragmentInteractionListener, FavBandFragment.OnFragmentInteractionListener,
        BandListAdminFragment.OnFragmentInteractionListener,ClientListAdminFragment.OnFragmentInteractionListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_band);

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
                    new PostAdminFragment()).commit();
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
                        new PostAdminFragment()).commit();
                break;
            case R.id.nav_band_account:
                Intent _intent = new Intent(getApplicationContext(),
                        BandProfile.class);
                startActivityForResult(_intent,0);
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(getApplicationContext(),
                        UserLoginActivity.class);
                startActivityForResult(intent,0);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
