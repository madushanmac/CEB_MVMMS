package com.example.akla.login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    private static int nCalledGetItem;
    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context ,FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        nCalledGetItem++;
        System.out.println("#getItem() called " + nCalledGetItem + " times.");
        switch (position){
            case 0:
                System.out.println("CASE:ARC 000000000000000000000000000000000000000000000000000000000");
                return new AutoRecloserFragment();
            case 1:
                System.out.println("CaSE:LBS 111111111111111111111111111111111111111111111111111111111111");
                return new LBSFragment();
            case 2:
                System.out.println("CASE:ABS 2222222222222222222222222222222222222222222222222222222222");
                return new ABSFragment();
            case 3:
                System.out.println("CASE:DDLO 33333333333333333333333333333333333333333333333333333333333");
                return new DDLOFragment();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
