package ua.in.razom.app.ui.fragments;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import actions.NavigationAction;
import dataobjects.NewIssueRequestObject;
import dataobjects.NewIssueResponse;
import dataservice.Api;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tools.ScalingHelper;
import ua.in.razom.app.R;
import ua.in.razom.app.ui.MainNavigationActivity;

public class PostIssueFragment extends Fragment {
    public final static String LONGITUDE = "LONG";
    public final static String LATITUDE = "LAT";
    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 10101;
    private double lon;
    private double lat;
    private TextView location;
    private EditText title;
    private EditText description;
    private ImageView image;
    private Button submitBtn;
    private Uri outputFileUri;
    private Uri selectedImageUri;
    private boolean isC;

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
        showLocation();
        setListeners();
    }

    private void setListeners() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageIntent();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageUri != null) {
                    submitIssue();

                }
            }
        });
    }

    private void submitIssue() {
        NewIssueRequestObject newIssue = new NewIssueRequestObject();
        newIssue.setTitle(title.getText().toString());
        newIssue.setDescription(description.getText().toString());
        newIssue.setCategory((MainNavigationActivity.categories != null && MainNavigationActivity.categories.size() > 0) ? MainNavigationActivity.categories.get(0).get_id() : "111");
        newIssue.setUser("fw4gg6hge5y6h56hh");
        newIssue.setTags(new String[]{"#YASHA", "#duRak"});
        newIssue.setLat(lat + Math.random() * 0.02 - 0.01);
        newIssue.setLon(lon + Math.random() * 0.02 - 0.01);

        Bitmap bitmap = null;
        if (selectedImageUri != null) {
            if (isC)
                bitmap = BitmapFactory.decodeFile(selectedImageUri.getPath());
            else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.issue);
        Bitmap scaledBitmap = ScalingHelper.scaleWithAspectRatio(bitmap, 150, 150);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String image = "data:image/jpeg;base64," + Base64.encodeToString(b, Base64.NO_WRAP);
        newIssue.setMedia(new String[]{image});
        Api.DataService.createIssue(newIssue, new Callback<NewIssueResponse>() {
            @Override
            public void success(NewIssueResponse newIssueResponse, Response response) {
                Toast.makeText(getActivity(), "Issue created", Toast.LENGTH_SHORT).show();
                closeFragment();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println();
                Toast.makeText(getActivity(), "Issue failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closeFragment() {
        EventBus.getDefault().post(NavigationAction.MAP);
    }

    private void showLocation() {
        location.setText(getString(R.string.current_location, lat, lon));
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
        location = (TextView) view.findViewById(R.id.location);
        title = (EditText) view.findViewById(R.id.title);
        description = (EditText) view.findViewById(R.id.description);
        image = (ImageView) view.findViewById(R.id.photo);
        submitBtn = (Button) view.findViewById(R.id.submit_btn);
    }

    private void openImageIntent() {

// Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "dsgsdfgsdfgdfsgdfs.txt";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }


                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }
                setImage(isCamera, selectedImageUri);
            }
        }
    }

    private void setImage(boolean isC, Uri u) {
        this.isC = isC;
        if (u != null) {
            if (isC)
                image.setImageBitmap(BitmapFactory.decodeFile(u.getPath()));
            else {
                try {
                    image.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), u));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}