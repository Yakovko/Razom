package ua.in.razom.app.ui.baloons;

import android.graphics.Bitmap;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * Created by ykomasyuk on 18.05.2014.
 */
public class CustomOverlayItem extends OverlayItem {

    protected String mImageURL;

    public CustomOverlayItem(GeoPoint point, String title, String snippet, String imageURL) {
        super(point, title, snippet);
        mImageURL = imageURL;
    }

    public Bitmap getImageBitmap() {
        return null;
    }

    public void setImageURL(String imageURL) {
        this.mImageURL = imageURL;
    }

}
