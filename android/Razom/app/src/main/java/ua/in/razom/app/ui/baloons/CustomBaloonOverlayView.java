package ua.in.razom.app.ui.baloons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.OverlayItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import ua.in.razom.app.R;

/**
 * Created by ykomasyuk on 18.05.2014.
 */
public class CustomBaloonOverlayView<Item extends OverlayItem> extends BaloonOverlayView<CustomOverlayItem> {

    private TextView title;
    private TextView snippet;
    private ImageView image;

    public CustomBaloonOverlayView(Context context, int baloonBottomOffset) {
        super(context, baloonBottomOffset);
    }

    @Override
    protected void setupView(Context context, final ViewGroup parent) {

        // inflate our custom layout into parent
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.baloon_overlay, parent);

        // setup our fields
        title = (TextView) v.findViewById(R.id.balloon_item_title);
        image = (ImageView) v.findViewById(R.id.balloon_item_image);

    }

    @Override
    protected void setBalloonData(CustomOverlayItem item, ViewGroup parent) {

        // map our custom item data to fields
        title.setText(item.getTitle());
        snippet.setText(item.getSnippet());

        // get remote image from network.
        // bitmap results would normally be cached, but this is good enough for demo purpose.
        image.setImageResource(R.drawable.issue);
        new FetchImageTask() {
            protected void onPostExecute(Bitmap result) {
                if (result != null) {
                    image.setImageBitmap(result);
                }
            }
        }.execute(item.getImageURL());

    }

    private class FetchImageTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... arg0) {
            Bitmap b = null;
            try {
                b = BitmapFactory.decodeStream((InputStream) new URL(arg0[0]).getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
    }

}
