package com.nitmizoram.nitmz.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nitmizoram.nitmz.FragmentsMM.FridayMM;
import com.nitmizoram.nitmz.FragmentsMM.MondayMM;
import com.nitmizoram.nitmz.FragmentsMM.SaturdayMM;
import com.nitmizoram.nitmz.FragmentsMM.SundayMM;
import com.nitmizoram.nitmz.FragmentsMM.ThursdayMM;
import com.nitmizoram.nitmz.FragmentsMM.TuesdayMM;
import com.nitmizoram.nitmz.FragmentsMM.WednesdayMM;

public class MessMenuPagerAdapter extends FragmentPagerAdapter {

    public MessMenuPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // Return the fragment for the corresponding position
        switch (position) {
            case 0:
                return new SundayMM();
            case 1:
                return new MondayMM();
            case 2:
                return new TuesdayMM();
            case 3:
                return new WednesdayMM();
            case 4:
                return new ThursdayMM();
            case 5:
                return new FridayMM();
            case 6:
                return new SaturdayMM();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Return the number of fragments
        return 7;
    }
}

