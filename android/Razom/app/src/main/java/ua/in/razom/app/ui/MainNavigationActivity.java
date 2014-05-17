package ua.in.razom.app.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fasterxml.jackson.databind.ObjectMapper;

import actions.NavigationAction;
import dataservice.JacksonConverter;
import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;
import ua.in.razom.app.R;
import ua.in.razom.app.ui.fragments.PostIssueFragment;
import ua.in.razom.app.ui.fragments.ProfileFragment;
import ua.in.razom.app.ui.fragments.SelectActionFragment;
import ua.in.razom.app.ui.fragments.ViewIssuesFragment;


public class MainNavigationActivity extends ActionBarActivity {

    private ActionBar actionBar;
    private RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initActionBar();
        showFragment(SelectActionFragment.newInstance(), false);
        restAdapter = new RestAdapter.Builder()
                .setConverter(new JacksonConverter(new ObjectMapper()))
                .setEndpoint("http://localhost:5005")
                .build();
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setIcon(R.drawable.logo_t2);

    }

    private void showFragment(Fragment fragment, boolean addToBackStack) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            ft.setTransition(FragmentTransaction.TRANSIT_NONE);
            ft.addToBackStack(fragment.getClass().getName());
        } else {
            ft.setTransition(FragmentTransaction.TRANSIT_NONE);
            ft.disallowAddToBackStack();
        }
        ft.replace(R.id.content_frame, fragment, fragment.getClass().getName());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().registerSticky(this, NavigationAction.class);
    }


    public void onEvent(NavigationAction action) {
        switch (action) {
            case POST_ISSUE:
                showFragment(PostIssueFragment.newInstance(), true);
                break;
            case VIEW_ISSUE:
                showFragment(ViewIssuesFragment.newInstance(), true);
                break;
            case PROFILE:
                showFragment(ProfileFragment.newInstance(), true);
                break;

        }
    }
}
