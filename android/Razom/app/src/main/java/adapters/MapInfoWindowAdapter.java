package adapters;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import dataobjects.Issue;
import ua.in.razom.app.R;

/**
 * Created by ykomasyuk on 18.05.2014.
 */
public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater;
    private ImageView image;
    private TextView title;
    private List<Issue> issues;

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public MapInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    // Use default InfoWindow frame
    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    // Defines the contents of the InfoWindow
    @Override
    public View getInfoContents(Marker arg0) {

        // Getting view from the layout file info_window_layout
        View v = inflater.inflate(R.layout.map_balloon, null);

        // Getting reference to the TextView to set latitude
        ImageView image = (ImageView) v.findViewById(R.id.balloon_item_image);

        // Getting reference to the TextView to set longitude
        TextView title = (TextView) v.findViewById(R.id.balloon_item_title);

        Issue i = null;
        for (Issue issue : issues) {
            if (issue.get_id().equals(arg0.getTitle())) {
                i = issue;
                break;
            }
        }
        if (i != null) {
            if (i.getMedia().length > 0) {
                String encodedImage = i.getMedia()[0];
                encodedImage = encodedImage.substring(encodedImage.lastIndexOf("base64,") + 7);
                byte[] decodedString = Base64.decode(encodedImage, Base64.NO_WRAP);
                image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
            }
            title.setText(i.getTitle());
        }
        // Returning the view containing InfoWindow contents
        return v;

    }
}
