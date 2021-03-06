package com.example.admin.musicbeansapp.ui.bands;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.adapters.TabAdapter;
import com.example.admin.musicbeansapp.ui.bands.fragments.DescriptionProfile;
import com.example.admin.musicbeansapp.ui.bands.fragments.EventProfile;
import com.example.admin.musicbeansapp.ui.bands.fragments.ProductProfile;

import musicbeans.dataaccess.ImageManager;
import musicbeans.entities.Band;
import musicbeans.entities.Sesion;
import musicbeans.entities.ViewBag;

public class BandProfile extends AppCompatActivity implements EventProfile.OnFragmentInteractionListener,DescriptionProfile.OnFragmentInteractionListener,ProductProfile.OnFragmentInteractionListener {
    private  String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_profile);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Eventos"));
        tabLayout.addTab(tabLayout.newTab().setText("Tienda"));
        tabLayout.addTab(tabLayout.newTab().setText("Descripción"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ImageView view = (ImageView)findViewById(R.id.profile);
        RatingBar ratingBar = findViewById(R.id.ratingStars);
        ratingBar.setVisibility(View.INVISIBLE);


        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        LoadInfo info = new LoadInfo();
        info.execute();
    }
    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
    class LoadInfo extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String band = Sesion.getInstance().getUsername();
            Band _band = musicbeans.dataaccess.Band.getBand(band);

            TextView _name = findViewById(R.id.bandName);
            TextView _rating = findViewById(R.id.rating);
            if(_band!=null) {
                final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);

                _name.setText(_band.getUsername());
                _rating.setText(_band.getRate() + "");
                ViewBag.put("text",_band.getDescription());

                double rat= musicbeans.dataaccess.Band.getBandRating(Sesion.getInstance().getUsername());
                if(rat==-1)
                    _rating.setText("5");
                else
                    _rating.setText(rat+"");
            }
            return null;
        }
    }
}
