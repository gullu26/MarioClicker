package com.example.sudarshanseshadri.marioclicker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager vp;

    SuperMarioFragment superMarioFragment;
    PaperMarioFragment paperMarioFragment;
    StoreFragment storeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        superMarioFragment = new SuperMarioFragment();
        paperMarioFragment = new PaperMarioFragment();
        storeFragment = new StoreFragment();

        vp= (ViewPager) findViewById(R.id.container);
        addPages(vp);

        tab= (TabLayout) findViewById(R.id.tabs);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.setupWithViewPager(vp);
        tab.setOnTabSelectedListener(tabSelectedListener(vp));

    }

    private void addPages(ViewPager viewPager)
    {
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.addPage(superMarioFragment);
        myPagerAdapter.addPage(paperMarioFragment);
        myPagerAdapter.addPage(storeFragment);

        vp.setAdapter(myPagerAdapter);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener(final  ViewPager pager)
    {
        return  new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    public void addPassiveCoins(int cost, double amount)
    {
        superMarioFragment.addPassiveIncome((float)amount);
        paperMarioFragment.pay(cost);
    }

    public void addPassiveSparks(int cost, double amount)
    {
        paperMarioFragment.addPassiveIncome((float)amount);
        superMarioFragment.pay(cost);
    }

    public int[] getCurrency()
    {
        return new int[]{paperMarioFragment.getSparks(), superMarioFragment.getCoins()};
    }

}