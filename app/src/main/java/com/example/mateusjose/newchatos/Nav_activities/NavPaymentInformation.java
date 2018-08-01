package com.example.mateusjose.newchatos.Nav_activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mateusjose.newchatos.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NavPaymentInformation extends Fragment {

    public NavPaymentInformation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav_my_boutiques, container, false);
    }
}
