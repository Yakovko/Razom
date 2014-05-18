package ua.in.razom.app.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ua.in.razom.app.R;

public class PostIssueFragment extends Fragment {
    public final static String LONGITUDE = "LONG";
    public final static String LATITUDE = "LAT";
    private double lon;
    private double lat;

    public static PostIssueFragment newInstance() {
        return new PostIssueFragment();
    }

    public static PostIssueFragment newInstance(Bundle args) {
        PostIssueFragment fragment = new PostIssueFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        lon = args.getDouble(LONGITUDE);
        lat = args.getDouble(LATITUDE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_issue, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        saveUIReferences(view);
        Toast.makeText(getActivity(), "onViewCreated, lon = " + lon + ", lat = " + lat, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void saveUIReferences(View view) {
    }


}