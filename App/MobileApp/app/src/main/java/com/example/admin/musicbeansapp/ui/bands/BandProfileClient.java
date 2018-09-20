package com.example.admin.musicbeansapp.ui.bands;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.adapters.TabAdapter;
import com.example.admin.musicbeansapp.ui.bands.fragments.DescriptionProfile;
import com.example.admin.musicbeansapp.ui.bands.fragments.EventProfile;
import com.example.admin.musicbeansapp.ui.bands.fragments.ProductProfile;

import org.w3c.dom.Text;

import musicbeans.dataaccess.ImageManager;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Band;
import musicbeans.entities.Sesion;
import musicbeans.entities.ViewBag;

public class BandProfileClient extends AppCompatActivity implements EventProfile.OnFragmentInteractionListener,DescriptionProfile.OnFragmentInteractionListener,ProductProfile.OnFragmentInteractionListener {

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

       ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rate(v);
            }
        });

        LoadInfo info = new LoadInfo();
        info.execute();
        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),true);
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
    public void rate(float v)
    {
        RateBand rateBand = new RateBand();
        rateBand.execute();
        Toast.makeText(getApplicationContext(),v+"", Toast.LENGTH_SHORT).show();
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

    class LoadInfo extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String band = Sesion.getInstance().getBand();
            Band _band = musicbeans.dataaccess.Band.getBand(band);

            TextView _name = findViewById(R.id.bandName);
            TextView _rating = findViewById(R.id.rating);
            if(_band!=null) {
                _name.setText(_band.getUsername());
                _rating.setText(_band.getRate() + "");
                ViewBag.put("text",_band.getDescription());
                double rat= musicbeans.dataaccess.Band.getBandRating(Sesion.getInstance().getBand());
                if(rat==-1)
                    _rating.setText("5");
                else
                    _rating.setText(rat+"");

                int client = musicbeans.dataaccess.Band.getBandRatingClient(Sesion.getInstance().getBand());

                if(client!=-1)
                {
                    RatingBar ratingBar = findViewById(R.id.ratingStars);

                    ratingBar.setRating(client);
                }
            }
            return null;
        }
    }
    class RateBand extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            RatingBar ratingBar = (findViewById(R.id.ratingStars));
            final musicbeans.dataaccess.Status status = musicbeans.dataaccess.Band.rateBand(Sesion.getInstance().getBand(),(byte)ratingBar.getRating());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(status== musicbeans.dataaccess.Status.NETWORK_ERROR)
                    {
                        Toast.makeText(getApplicationContext(),"Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                    if(status== musicbeans.dataaccess.Status.OK)
                    {
                        Toast.makeText(getApplicationContext(),"Se calificó la banda", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if(status== musicbeans.dataaccess.Status.OK)
            {
                final double rat= musicbeans.dataaccess.Band.getBandRating(Sesion.getInstance().getBand());
                final TextView r = findViewById(R.id.rating);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(rat==-1)
                            r.setText("5");
                        else
                            r.setText(rat+"");
                    }
                });

            }
            return null;
        }
    }
}
