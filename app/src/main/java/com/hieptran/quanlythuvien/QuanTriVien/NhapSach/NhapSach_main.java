package com.hieptran.quanlythuvien.QuanTriVien.NhapSach;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.Adapter_nhapsach.DanhsachSach;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;

public class NhapSach_main extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_sach_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addList(new NhapSach(), "Nhập sách");
        mSectionsPagerAdapter.addList(new CapNhapSach(), "Cập Nhập Sách");
        mSectionsPagerAdapter.addList(new DanhsachSach(), "Danh sách sách");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nhap_sach_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        private ArrayList<String> titleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragmentArrayList.get(position);
        }

        public void addList(Fragment fragment, String title) {
            fragmentArrayList.add(fragment);
            titleList.add(title);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragmentArrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return titleList.get(position);
        }
    }
}
