package com.nitmizoram.nitmz.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nitmizoram.nitmz.FragmentsNB.BH1;
import com.nitmizoram.nitmz.FragmentsNB.BH2;
import com.nitmizoram.nitmz.FragmentsNB.BH3;
import com.nitmizoram.nitmz.FragmentsNB.BH4;
import com.nitmizoram.nitmz.FragmentsNB.GH1;

public class NitBusesPagerAdapter extends FragmentPagerAdapter {
    public NitBusesPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        // Return the fragment for the corresponding position
        switch (position) {
            case 0:
                return new BH1();
            case 1:
                return new BH2();
            case 2:
                return new BH3();
            case 3:
                return new BH4();
            case 4:
                return new GH1();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
