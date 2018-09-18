package com.example.admin.musicbeansapp.ui.bands;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.adapters.TabAdapter;
import com.example.admin.musicbeansapp.ui.bands.fragments.DescriptionProfile;
import com.example.admin.musicbeansapp.ui.bands.fragments.EventProfile;
import com.example.admin.musicbeansapp.ui.bands.fragments.ProductProfile;

import musicbeans.dataaccess.ImageManager;

public class BandProfile extends AppCompatActivity implements EventProfile.OnFragmentInteractionListener,DescriptionProfile.OnFragmentInteractionListener,ProductProfile.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_profile);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Eventos"));
        tabLayout.addTab(tabLayout.newTab().setText("Tienda"));
        tabLayout.addTab(tabLayout.newTab().setText("Descripción"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        String[] url = new String[]{"img/banda1.PNG"};
        ImageView[] view = new ImageView[]{(ImageView)findViewById(R.id.profile)};
        ImageManager img = new ImageManager(url,view);
        img.execute();
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
    }
    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
    class RegisterBand extends AsyncTask<Void,Void,Void>
    {
        public RegisterBand(){}
        protected Void doInBackground(Void... fields)
        {
            String[] url = new String[]{"img/banda1.PNG"};
            ImageView[] view = new ImageView[]{(ImageView)findViewById(R.id.profile)};
            //ImageManager imn = new ImageManager();
            //imn.loadImages(url,view);

            return  null;
        }
        protected void onPostExecute(Void _void)
        {

        }
    }
}
