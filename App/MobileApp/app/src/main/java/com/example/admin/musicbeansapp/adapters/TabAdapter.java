package com.example.admin.musicbeansapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.musicbeansapp.ui.bands.fragments.DescriptionProfile;
import com.example.admin.musicbeansapp.ui.bands.fragments.EventProfile;
import com.example.admin.musicbeansapp.ui.bands.fragments.ProductProfile;

public class TabAdapter extends FragmentStatePagerAdapter {

    int tabNumber;
    boolean client=false;
    DescriptionProfile tab3;
    public TabAdapter(FragmentManager fm,int tabNumber)
    {
        super(fm);
        this.tabNumber=tabNumber;
    }
    public TabAdapter(FragmentManager fm,int tabNumber,boolean client)
    {
        super(fm);
        this.tabNumber=tabNumber;
        this.client=client;
    }
    @Override
    public Fragment getItem(int position) {
       switch (position)
       {
           case 0:
               EventProfile tab1 = new EventProfile();
               tab1.setClient(client);
               return tab1;
           case 1:
               ProductProfile tab2 = new ProductProfile();
               tab2.setClient(client);
               return  tab2;
           case 2:
               DescriptionProfile tab3 = new DescriptionProfile();
               this.tab3=tab3;
               return  tab3;
           default:
               return null;
       }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }
}
