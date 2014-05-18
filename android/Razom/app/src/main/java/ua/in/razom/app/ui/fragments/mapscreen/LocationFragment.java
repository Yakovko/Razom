package ua.in.razom.app.ui.fragments.mapscreen;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import actions.NavigationAction;
import datamodels.IssuesCategory;
import de.greenrobot.event.EventBus;
import ua.in.razom.app.R;

public class LocationFragment extends Fragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {


    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationClient locationClient;
    private MapView mapView;
    private GoogleMap map;
    private View addPinView;

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);


        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        addPinView = v.findViewById(R.id.add_marker);

        // Updates the location and zoom of the MapView
        locationClient = new LocationClient(getActivity(), this, this);
        locationClient.connect();

        addMarkers();

        setListeners();
        return v;
    }

    private void setListeners() {
        addPinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationClient.isConnected()) {
                    Location curLoc = locationClient.getLastLocation();
                    NavigationAction action = NavigationAction.POST_ISSUE;
                    action.setLon(curLoc.getLongitude());
                    action.setLat(curLoc.getLatitude());
                    EventBus.getDefault().post(action);
                }
            }
        });
    }

    private void addMarkers() {
        List<Issue> issues = getIssuesStub();
        for (Issue issue : issues) {
            int icon_res_id;
            switch (issue.category) {
                case COMMON:
                    icon_res_id = R.drawable.pin02;
                    break;
                case ECOLOGY:
                    icon_res_id = R.drawable.pin03;
                    break;
                case STUFF:
                    icon_res_id = R.drawable.pin04;
                    break;
                case TRANSPORT:
                default:
                    icon_res_id = R.drawable.pin05;
            }

            map.addMarker(new MarkerOptions()
                    .position(new LatLng(issue.lat, issue.lan))
                    .title(issue.title)
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(icon_res_id)));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        mapView.onResume();

        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Display the connection status
        Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
        Location currentLocation = locationClient.getLastLocation();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15);
        map.animateCamera(cameraUpdate);
    }

    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(getActivity(), "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
 /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        }
    }

    private List<Issue> getIssuesStub() {
        List<Issue> retList = new ArrayList<Issue>();
        retList.add(new Issue(50.440595, 30.513077, "Test 1", IssuesCategory.ECOLOGY));
        retList.add(new Issue(50.44052, 30.512036, "Test 2", IssuesCategory.COMMON));
        retList.add(new Issue(50.439222, 30.512449, "Test 3", IssuesCategory.STUFF));
        retList.add(new Issue(50.439533, 30.514526, "Test 4", IssuesCategory.TRANSPORT));
        return retList;
    }

    private class Issue {
        public double lan;
        public double lat;
        public String title;
        public IssuesCategory category;

        private Issue(double lat, double lan, String title, IssuesCategory category) {
            this.lan = lan;
            this.lat = lat;
            this.title = title;
            this.category = category;
        }

    }
}