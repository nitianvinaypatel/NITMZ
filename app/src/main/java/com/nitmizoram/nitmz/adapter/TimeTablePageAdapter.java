package com.nitmizoram.nitmz.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nitmizoram.nitmz.FragmentsTT.FridayTT;
import com.nitmizoram.nitmz.FragmentsTT.MondayTT;
import com.nitmizoram.nitmz.FragmentsTT.SaturdayTT;
import com.nitmizoram.nitmz.FragmentsTT.SundayTT;
import com.nitmizoram.nitmz.FragmentsTT.ThursdayTT;
import com.nitmizoram.nitmz.FragmentsTT.TuesdayTT;
import com.nitmizoram.nitmz.FragmentsTT.WednesdayTT;

public class TimeTablePageAdapter extends FragmentPagerAdapter {

    public TimeTablePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // Return the fragment for the corresponding position
        switch (position) {
            case 0:
                return new SundayTT();
            case 1:
                return new MondayTT();
            case 2:
                return new TuesdayTT();
            case 3:
                return new WednesdayTT();
            case 4:
                return new ThursdayTT();
            case 5:
                return new FridayTT();
            case 6:
                return new SaturdayTT();
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
