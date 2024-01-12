package com.example.nitmz.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nitmz.FragmentsTT.FridayTT;
import com.example.nitmz.FragmentsTT.MondayTT;
import com.example.nitmz.FragmentsTT.SaturdayTT;
import com.example.nitmz.FragmentsTT.SundayTT;
import com.example.nitmz.FragmentsTT.ThursdayTT;
import com.example.nitmz.FragmentsTT.TuesdayTT;
import com.example.nitmz.FragmentsTT.WednesdayTT;

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
