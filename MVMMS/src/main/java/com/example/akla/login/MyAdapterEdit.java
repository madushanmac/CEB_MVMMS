package com.example.akla.login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapterEdit extends FragmentPagerAdapter {
    private static int nCalledGetItem;
    private Context myContext;
    int totalTabs;

    public MyAdapterEdit(Context context , FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        nCalledGetItem++;
        System.out.println("#getItem() called " + nCalledGetItem + " times.");
        System.out.println("EDITTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        switch (position){
            case 0:
                System.out.println("CASE:ARC Edit 000000000000000000000000000000000000000000000000000000000");
                return new AutoRecloserFragmentEdit();
            case 1:
                System.out.println("CaSE:LBS Edit 111111111111111111111111111111111111111111111111111111111111");
                return new LBSFragmentEdit();
            case 2:
                System.out.println("CASE:ABS Edit 2222222222222222222222222222222222222222222222222222222222");
                return new ABSFragmentEdit();
            case 3:
                System.out.println("CASE:DDLO Edit 33333333333333333333333333333333333333333333333333333333333");
                return new DDLOFragmentEdit();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
