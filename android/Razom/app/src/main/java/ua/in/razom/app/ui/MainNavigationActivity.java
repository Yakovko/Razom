package ua.in.razom.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import actions.NavigationAction;
import dataobjects.Category;
import dataservice.Api;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ua.in.razom.app.R;
import ua.in.razom.app.ui.fragments.PostIssueFragment;
import ua.in.razom.app.ui.fragments.ProfileFragment;
import ua.in.razom.app.ui.fragments.TestApiFragment;

public class MainNavigationActivity extends ActionBarActivity {

    public List<Category> categories;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initActionBar();
        showFragment(TestApiFragment.newInstance(), false);
        Api.InitDataService();
        categories = new ArrayList<Category>();
        requestCategories();
//        requestIssues();
    }

    private void requestCategories() {
        Api.DataService.getCategoryList(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                MainNavigationActivity.this.categories = categories;
            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        });
    }

    //    private void requestIssues() {
//        Api.DataService.getCategoryList(new Callback<List<Category>>() {
//            @Override
//            public void success(List<Category> categories, Response response) {
//                MainNavigationActivity.this.categories = categories;
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                Toast.makeText(MainNavigationActivity.this, "Fail to retrieve categories. " + retrofitError.getResponse().getReason(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.hide();


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
                Bundle bundle = new Bundle();
                bundle.putDouble(PostIssueFragment.LATITUDE, action.getLat());
                bundle.putDouble(PostIssueFragment.LONGITUDE, action.getLon());
                showFragment(PostIssueFragment.newInstance(bundle), true);
                break;
            case PROFILE:
                showFragment(ProfileFragment.newInstance(), true);
                break;

        }
    }
}
