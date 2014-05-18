package ua.in.razom.app.ui.baloons;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;

/**
 * Created by ykomasyuk on 18.05.2014.
 */
public class CustomItemizedOverlay<Item extends OverlayItem> extends BaloonItemizedOverlay<CustomOverlayItem> {

    private ArrayList<CustomOverlayItem> m_overlays = new ArrayList<CustomOverlayItem>();
    private Context c;

    public CustomItemizedOverlay(Drawable defaultMarker, MapView mapView) {
        super(boundCenter(defaultMarker), mapView);
        c = mapView.getContext();
    }

    public void addOverlay(CustomOverlayItem overlay) {
        m_overlays.add(overlay);
        populate();
    }

    @Override
    protected CustomOverlayItem createItem(int i) {
        return m_overlays.get(i);
    }

    @Override
    public int size() {
        return m_overlays.size();
    }

    @Override
    protected boolean onBaloonTap(int index, CustomOverlayItem item) {
        Toast.makeText(c, "onBaloonTap for overlay index " + index,
                Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected BaloonOverlayView<CustomOverlayItem> createBaloonOverlayView() {
        // use our custom baloon view with our custom overlay item type:
        return new CustomBaloonOverlayView<CustomOverlayItem>(getMapView().getContext(), getBaloonBottomOffset());
    }

}
