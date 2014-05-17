package ua.in.razom.app.ui.fragments;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.ByteArrayOutputStream;

import dataobjects.NewIssueObject;
import dataservice.Api;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ua.in.razom.app.R;

public class PostIssueFragment extends Fragment {

    private Button postIssueBtn;
    private Button viewIssueBtn;
    private Button profileBtn;

    public static PostIssueFragment newInstance() {
        return new PostIssueFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_issue, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        saveUIReferences(view);

    }

    @Override
    public void onResume() {
        super.onResume();
        NewIssueObject testIssue = new NewIssueObject();
        testIssue.setTitle("Terrible Disaster!!!");
        testIssue.setDescription("Terrible disaster - immediate action required!");
        testIssue.setCategory("abcdefghigk");
        testIssue.setLat(50.440595);
        testIssue.setLon(30.513077);
        testIssue.setTags(new String[]{"OMGWTF!!!1", "#dumbtag"});
        testIssue.setUser("abcdefghigk");
        Drawable photo = getResources().getDrawable(R.drawable.issue);
        Bitmap bitmap = ((BitmapDrawable) photo).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        testIssue.setFile(Base64.encodeToString(bitMapData, Base64.DEFAULT));
        System.out.println(testIssue.getFile());
        Api.DataService.createIssue(testIssue, new Callback<NewIssueObject>() {
            @Override
            public void success(NewIssueObject newIssueObject, Response response) {
                System.out.println(response);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println(retrofitError);
            }
        });

//        Api.DataService.getCategoryList(new Callback<List<Category>>() {
//            @Override
//            public void success(List<Category> categories, Response response) {
//                System.out.println(response);
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                System.out.println(retrofitError);
//            }
//        });
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