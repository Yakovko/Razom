package ua.in.razom.app.ui.fragments.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.ExpandableListAdapter;
import ua.in.razom.app.R;


public class SideMenuFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ExpandableListView expListView;

    @SuppressWarnings("unused")
    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_side_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        saveUIReferences(view);
//        fillSlidingMenuActions(SlidingMenuAction.OVERVIEW.ordinal());

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (groupPosition == 2 || groupPosition == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("My Issue (3)");
        listDataHeader.add("Favourite (4)");
        listDataHeader.add("Settings");
        listDataHeader.add("Help");

        // Adding child data
        List<String> myIssue = new ArrayList<String>();
        myIssue.add("Name1");
        myIssue.add("Name2");
        myIssue.add("Name3");

        List<String> favourites = new ArrayList<String>();
        favourites.add("Name1");
        favourites.add("Name2");
        favourites.add("Name3");
        favourites.add("Name4");

        listDataChild.put(listDataHeader.get(0), myIssue); // Header, Child data
        listDataChild.put(listDataHeader.get(1), favourites);
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
        expListView = (ExpandableListView) view.findViewById(R.id.menu_list);


    }


}