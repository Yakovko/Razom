package ua.in.razom.app.ui.baloons;

/**
 * Created by ykomasyuk on 18.05.2014.
 */

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import java.util.List;

import ua.in.razom.app.R;

public abstract class BaloonItemizedOverlay<Item extends CustomOverlayItem> extends ItemizedOverlay<Item> {

    private static final long BALOON_INFLATION_TIME = 300;
    private static Handler handler = new Handler();
    private static boolean isInflating = false;
    private static Runnable finishBaloonInflation = new Runnable() {
        public void run() {
            isInflating = false;
        }
    };
    final MapController mc;
    private MapView mapView;
    private BaloonOverlayView<Item> baloonView;
    private View clickRegion;
    private View closeRegion;
    private int viewOffset;
    private Item currentFocusedItem;
    private int currentFocusedIndex;
    private boolean showClose = true;
    private boolean showDisclosure = false;
    private boolean snapToCenter = true;

    /**
     * Create a new BaloonItemizedOverlay
     *
     * @param defaultMarker - A bounded Drawable to be drawn on the map for each item in the overlay.
     * @param mapView       - The view upon which the overlay items are to be drawn.
     */
    public BaloonItemizedOverlay(Drawable defaultMarker, MapView mapView) {
        super(defaultMarker);
        this.mapView = mapView;
        viewOffset = 0;
        mc = mapView.getController();
    }

    public static boolean isInflating() {
        return isInflating;
    }

    public int getBaloonBottomOffset() {
        return viewOffset;
    }

    /**
     * Set the horizontal distance between the marker and the bottom of the information
     * baloon. The default is 0 which works well for center bounded markers. If your
     * marker is center-bottom bounded, call this before adding overlay items to ensure
     * the baloon hovers exactly above the marker.
     *
     * @param pixels - The padding between the center point and the bottom of the
     *               information baloon.
     */
    public void setBaloonBottomOffset(int pixels) {
        viewOffset = pixels;
    }

    /**
     * Override this method to handle a "tap" on a baloon. By default, does nothing
     * and returns false.
     *
     * @param index - The index of the item whose baloon is tapped.
     * @param item  - The item whose baloon is tapped.
     * @return true if you handled the tap, otherwise false.
     */
    protected boolean onBaloonTap(int index, Item item) {
        return false;
    }

    /**
     * Override this method to perform actions upon an item being tapped before
     * its baloon is displayed.
     *
     * @param index - The index of the item tapped.
     */
    protected void onBaloonOpen(int index) {
    }

    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#onTap(int)
     */
    @Override
    //protected final boolean onTap(int index) {
    public final boolean onTap(int index) {

        handler.removeCallbacks(finishBaloonInflation);
        isInflating = true;
        handler.postDelayed(finishBaloonInflation, BALOON_INFLATION_TIME);

        currentFocusedIndex = index;
        currentFocusedItem = createItem(index);
        setLastFocusedIndex(index);

        onBaloonOpen(index);
        createAndDisplayBaloonOverlay();

        if (snapToCenter) {
            animateTo(index, currentFocusedItem.getPoint());
        }

        return true;
    }

    /**
     * Animates to the given center point. Override to customize how the
     * MapView is animated to the given center point
     *
     * @param index  The index of the item to center
     * @param center The center point of the item
     */
    protected void animateTo(int index, GeoPoint center) {
        mc.animateTo(center);
    }

    /**
     * Creates the baloon view. Override to create a sub-classed view that
     * can populate additional sub-views.
     */
    protected BaloonOverlayView<Item> createBaloonOverlayView() {
        return new BaloonOverlayView<Item>(getMapView().getContext(), getBaloonBottomOffset());
    }

    /**
     * Expose map view to subclasses.
     * Helps with creation of baloon views.
     */
    protected MapView getMapView() {
        return mapView;
    }

    /**
     * Makes the baloon the topmost item by calling View.bringToFront().
     */
    public void bringBaloonToFront() {
        if (baloonView != null) {
            baloonView.bringToFront();
        }
    }

    /**
     * Sets the visibility of this overlay's baloon view to GONE and unfocus the item.
     */
    public void hideBaloon() {
        if (baloonView != null) {
            baloonView.setVisibility(View.GONE);
        }
        currentFocusedItem = null;
    }

    /**
     * Hides the baloon view for any other BaloonItemizedOverlay instances
     * that might be present on the MapView.
     *
     * @param overlays - list of overlays (including this) on the MapView.
     */
    private void hideOtherBaloons(List<Overlay> overlays) {

        for (Overlay overlay : overlays) {
            if (overlay instanceof BaloonItemizedOverlay<?> && overlay != this) {
                ((BaloonItemizedOverlay<?>) overlay).hideBaloon();
            }
        }

    }

    public void hideAllBaloons() {
        if (!isInflating) {
            List<Overlay> mapOverlays = mapView.getOverlays();
            if (mapOverlays.size() > 1) {
                hideOtherBaloons(mapOverlays);
            }
            hideBaloon();
        }
    }

    /**
     * Sets the onTouchListener for the baloon being displayed, calling the
     * overridden {@link #onBaloonTap} method.
     */
    private OnTouchListener createBaloonTouchListener() {
        return new OnTouchListener() {

            float startX;
            float startY;

            public boolean onTouch(View v, MotionEvent event) {

                View l = ((View) v.getParent()).findViewById(R.id.baloon_main_layout);
                Drawable d = l.getBackground();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (d != null) {
                        int[] states = {android.R.attr.state_pressed};
                        if (d.setState(states)) {
                            d.invalidateSelf();
                        }
                    }
                    startX = event.getX();
                    startY = event.getY();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (d != null) {
                        int newStates[] = {};
                        if (d.setState(newStates)) {
                            d.invalidateSelf();
                        }
                    }
                    if (Math.abs(startX - event.getX()) < 40 &&
                            Math.abs(startY - event.getY()) < 40) {
                        // call overridden method
                        onBaloonTap(currentFocusedIndex, currentFocusedItem);
                    }
                    return true;
                } else {
                    return false;
                }

            }
        };
    }

    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#getFocus()
     */
    @Override
    public Item getFocus() {
        return currentFocusedItem;
    }

    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#setFocus(Item)
     */
    @Override
    public void setFocus(Item item) {
        super.setFocus(item);
        currentFocusedIndex = getLastFocusedIndex();
        currentFocusedItem = item;
        if (currentFocusedItem == null) {
            hideBaloon();
        } else {
            createAndDisplayBaloonOverlay();
        }
    }

    /**
     * Creates and displays the baloon overlay by recycling the current
     * baloon or by inflating it from xml.
     *
     * @return true if the baloon was recycled false otherwise
     */
    private boolean createAndDisplayBaloonOverlay() {
        boolean isRecycled;
        if (baloonView == null) {
            baloonView = createBaloonOverlayView();
            clickRegion = (View) baloonView.findViewById(R.id.baloon_inner_layout);
            clickRegion.setOnTouchListener(createBaloonTouchListener());
//            closeRegion = (View) baloonView.findViewById(R.id.baloon_close);
//            if (closeRegion != null) {
//                if (!showClose) {
//                    closeRegion.setVisibility(View.GONE);
//                } else {
//                    closeRegion.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            hideBaloon();
//                        }
//                    });
//                }
//            }
//            if (showDisclosure && !showClose) {
//                View v = baloonView.findViewById(R.id.baloon_disclosure);
//                if (v != null) {
//                    v.setVisibility(View.VISIBLE);
//                }
//            }
            isRecycled = false;
        } else {
            isRecycled = true;
        }

        baloonView.setVisibility(View.GONE);

        List<Overlay> mapOverlays = mapView.getOverlays();
        if (mapOverlays.size() > 1) {
            hideOtherBaloons(mapOverlays);
        }

        if (currentFocusedItem != null)
            baloonView.setData(currentFocusedItem);

        GeoPoint point = currentFocusedItem.getPoint();
        MapView.LayoutParams params = new MapView.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point,
                MapView.LayoutParams.BOTTOM_CENTER);
        params.mode = MapView.LayoutParams.MODE_MAP;

        baloonView.setVisibility(View.VISIBLE);

        if (isRecycled) {
            baloonView.setLayoutParams(params);
        } else {
            mapView.addView(baloonView, params);
        }

        return isRecycled;
    }

    public void setShowClose(boolean showClose) {
        this.showClose = showClose;
    }

    public void setShowDisclosure(boolean showDisclosure) {
        this.showDisclosure = showDisclosure;
    }

    public void setSnapToCenter(boolean snapToCenter) {
        this.snapToCenter = snapToCenter;
    }

}
