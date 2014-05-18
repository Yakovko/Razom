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

import java.util.List;

import actions.NavigationAction;
import adapters.MapInfoWindowAdapter;
import dataobjects.Issue;
import dataservice.Api;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ua.in.razom.app.R;

public class LocationFragment extends Fragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {


    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationClient locationClient;
    private MapView mapView;
    private GoogleMap map;
    private View addPinView;
    private MapInfoWindowAdapter adapter;


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
        adapter = new MapInfoWindowAdapter(inflater);
        map.setInfoWindowAdapter(adapter);


        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        addPinView = v.findViewById(R.id.add_marker);

        // Updates the location and zoom of the MapView
        locationClient = new LocationClient(getActivity(), this, this);
        locationClient.connect();

        requestMarkers();

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

    private void requestMarkers() {
        Api.DataService.getAllIssues(new Callback<List<Issue>>() {
            @Override
            public void success(List<Issue> issues, Response response) {
                addIssuesToMap(issues);
                adapter.setIssues(issues);
//                adapter.notifyAll();
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });

    }

    private void addIssuesToMap(List<Issue> issues) {
        int[] pins = {R.drawable.pin02, R.drawable.pin03, R.drawable.pin04, R.drawable.pin05, R.drawable.pin01};
        for (int i = 0; i < issues.size(); i++) {
            Issue issue = issues.get(i);
            int pin = 0;
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(issue.getLat(), issue.getLon()))
                    .title(issue.get_id())
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(pins[i % pins.length])));
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
        if (currentLocation != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15);
            map.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onDisconnected() {
        // Display the connection status

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(
                        getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

}