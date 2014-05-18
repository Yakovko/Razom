package ua.in.razom.app.ui.fragments.mapscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import ua.in.razom.app.R;


public class FilterFragment extends Fragment {

    ListAdapter listAdapter;
    List<String> listData;
    private ListView listView;

    @SuppressWarnings("unused")
    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        saveUIReferences(view);

    }

    private void prepareListData() {

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
        listView = (ListView) view.findViewById(R.id.categories_list);


    }


}