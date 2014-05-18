package ua.in.razom.app.ui.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.ByteArrayOutputStream;

import dataobjects.NewIssueRequestObject;
import dataobjects.NewIssueResponse;
import dataservice.Api;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tools.ScalingHelper;
import ua.in.razom.app.R;

public class TestApiFragment extends Fragment {

    private Button postIssueBtn;
    private Button viewIssueBtn;
    private Button profileBtn;

    public static TestApiFragment newInstance() {
        return new TestApiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_issue, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        saveUIReferences(view);
        createTestIssue();
    }

    private void createTestIssue() {
        NewIssueRequestObject newIssue = new NewIssueRequestObject();
        newIssue.setTitle("Terrible Disaster!!!");
        newIssue.setDescription("Terrible disaster - immediate action required!");
        newIssue.setCategory("5377b52e944ec4a4175b967a");
        newIssue.setUser("fw4gg6hge5y6h56hh");
        newIssue.setTags(new String[]{"OMGWTF!!!1", "#dumbtag"});
        newIssue.setLat(50.440595);
        newIssue.setLon(30.513077);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.issue);
        Bitmap scaledBitmap = ScalingHelper.scaleWithAspectRatio(bitmap, 150, 150);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String image = "data:image/jpeg;base64," + Base64.encodeToString(b, Base64.NO_WRAP);
        newIssue.setMedia(new String[]{image});
        Api.DataService.createIssue(newIssue, new Callback<NewIssueResponse>() {
            @Override
            public void success(NewIssueResponse newIssueResponse, Response response) {
                System.out.println();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println();
            }
        });
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
        postIssueBtn = (Button) view.findViewById(R.id.post_issue_btn);
        viewIssueBtn = (Button) view.findViewById(R.id.view_issue_btn);
        profileBtn = (Button) view.findViewById(R.id.profile_btn);
    }


}