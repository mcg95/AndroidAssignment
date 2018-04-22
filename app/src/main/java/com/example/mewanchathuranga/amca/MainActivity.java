package com.example.mewanchathuranga.amca;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mewanchathuranga.amca.Tabs.JobTab;
import com.firebase.ui.storage.R;
import com.google.firebase.auth.ActionCodeResult;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(com.example.mewanchathuranga.amca.R.layout.fragment_main, container, false);
            ((TextView) rootView.findViewById(com.example.mewanchathuranga.amca.R.id.section_label)).setText(getString(com.example.mewanchathuranga.amca.R.string.section_format, new Object[]{Integer.valueOf(getArguments().getInt(ARG_SECTION_NUMBER))}));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case ActionCodeResult.PASSWORD_RESET /*0*/:
                    return new JobTab();
                default:
                    return null;
            }
        }

        public int getCount() {
            return 1;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mewanchathuranga.amca.R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        this.mViewPager = (ViewPager) findViewById(com.firebase.ui.auth.R.id.container);
        this.mViewPager.setAdapter(this.mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(com.example.mewanchathuranga.amca.R.id.tabs);
        this.mViewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(this.mViewPager));
      //  ((FloatingActionButton) findViewById(com.example.mewanchathuranga.amca.R.id.fab)).setOnClickListener(new OnClickListener() {
          //  public void onClick(View view) {
      //          Snackbar.make(view, "Replace with your own action", 0).setAction("Action", null).show();
     //       }
  //      });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.example.mewanchathuranga.amca.R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == com.example.mewanchathuranga.amca.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
