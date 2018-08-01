package com.example.mateusjose.newchatos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mateusjose.newchatos.Fragments.FragAccessories;
import com.example.mateusjose.newchatos.Fragments.FragCalcados;
import com.example.mateusjose.newchatos.Fragments.FragCalcas;
import com.example.mateusjose.newchatos.Fragments.FragCamisas;
import com.example.mateusjose.newchatos.Fragments.FragmentTabHomem;
import com.example.mateusjose.newchatos.Fragments.FragmentTabMulher;

/**
 * Created by mateusjose on 2/15/18.
 */

public class TabsPager extends FragmentStatePagerAdapter{


    public TabsPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0:
                return "Mulheres";
            case 1:
                return "Homens";
        }
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                FragmentTabMulher fragmentTabMulher =new FragmentTabMulher();
                return fragmentTabMulher;
            case 1:
                FragmentTabHomem fragmentTabHomem =new FragmentTabHomem();
                return fragmentTabHomem;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
