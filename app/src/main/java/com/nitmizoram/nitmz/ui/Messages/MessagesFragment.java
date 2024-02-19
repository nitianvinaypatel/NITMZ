package com.nitmizoram.nitmz.ui.Messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.nitmizoram.nitmz.MainActivity;
import com.nitmizoram.nitmz.R;
import com.nitmizoram.nitmz.ui.Home.HomeFragment;
import com.google.android.material.tabs.TabLayout;

public class MessagesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        // Find the inner ViewPager in the layout
         ViewPager innerViewPager = view.findViewById(R.id.innerViewPager);

        // Create an adapter for the inner ViewPager
        InnerPagerAdapter innerPagerAdapter = new InnerPagerAdapter(getChildFragmentManager());

        // Set the adapter to the inner ViewPager
        innerViewPager.setAdapter(innerPagerAdapter);

        // (Optional) Find the TabLayout in the layout
        TabLayout innerTabLayout = view.findViewById(R.id.innerTabLayout);

        // (Optional) Set up TabLayout with the inner ViewPager
        if (innerTabLayout != null) {
            innerTabLayout.setupWithViewPager(innerViewPager);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).setToolbarTitle("Messages");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Replace with HomeFragment
                replaceFragment(new HomeFragment());
                MainActivity.getBottomNavigation().show(1, true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public static class InnerPagerAdapter extends FragmentPagerAdapter {

        public InnerPagerAdapter(@NonNull FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // Return the fragment for the given position
            switch (position) {
                case 0:
                    return new ChatFragment();
                case 1:
                    return new StatusFragment();
                case 2:
                    return new CallsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Return the total number of fragments
            return 3; // Chat, Status, Calls
        }

        @NonNull
        @Override
        public CharSequence getPageTitle(int position) {
            // (Optional) Return the title for each tab
            switch (position) {
                case 0:
                    return "Chats";
                case 1:
                    return "Status";
                case 2:
                    return "Calls";
                default:
                    return "";
            }
        }
    }
}
