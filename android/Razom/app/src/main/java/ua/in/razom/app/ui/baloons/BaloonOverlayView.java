package ua.in.razom.app.ui.baloons;

/**
 * Created by ykomasyuk on 18.05.2014.
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.OverlayItem;

import ua.in.razom.app.R;

public class BaloonOverlayView<Item extends OverlayItem> extends FrameLayout {

    private LinearLayout layout;
    private ImageView image;
    private TextView title;

    /**
     * Create a new BalloonOverlayView.
     *
     * @param context             - The activity context.
     * @param balloonBottomOffset - The bottom padding (in pixels) to be applied
     *                            when rendering this view.
     */
    public BaloonOverlayView(Context context, int balloonBottomOffset) {

        super(context);

        setPadding(10, 0, 10, balloonBottomOffset);

        layout = new LimitLinearLayout(context);
        layout.setVisibility(VISIBLE);

        setupView(context, layout);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.NO_GRAVITY;

        addView(layout, params);

    }

    /**
     * Inflate and initialize the BalloonOverlayView UI. Override this method
     * to provide a custom view/layout for the balloon.
     *
     * @param context - The activity context.
     * @param parent  - The root layout into which you must inflate your view.
     */
    protected void setupView(Context context, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.baloon_overlay, parent);
        image = (ImageView) v.findViewById(R.id.balloon_item_image);
        title = (TextView) v.findViewById(R.id.balloon_item_title);

    }

    /**
     * Sets the view data from a given overlay item.
     *
     * @param item - The overlay item containing the relevant view data.
     */
    public void setData(Item item) {
        layout.setVisibility(VISIBLE);
        setBaloonData(item, layout);
    }

    /**
     * Sets the view data from a given overlay item. Override this method to create
     * your own data/view mappings.
     *
     * @param item   - The overlay item containing the relevant view data.
     * @param parent - The parent layout for this BalloonOverlayView.
     */
    protected void setBaloonData(Item item, ViewGroup parent) {
        if (image.getDrawable() != null) {
            image.setImageBitmap(item.getImageBitmap());
        } else {
            image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.issue));
        }
        if (item.getTitle() != null) {
            title.setVisibility(VISIBLE);
            title.setText(item.getTitle());
        } else {
            title.setText("");
            title.setVisibility(GONE);
        }
    }

    private class LimitLinearLayout extends LinearLayout {

        private static final int MAX_WIDTH_DP = 280;

        final float SCALE = getContext().getResources().getDisplayMetrics().density;

        public LimitLinearLayout(Context context) {
            super(context);
        }

        public LimitLinearLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int mode = MeasureSpec.getMode(widthMeasureSpec);
            int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
            int adjustedMaxWidth = (int) (MAX_WIDTH_DP * SCALE + 0.5f);
            int adjustedWidth = Math.min(measuredWidth, adjustedMaxWidth);
            int adjustedWidthMeasureSpec = MeasureSpec.makeMeasureSpec(adjustedWidth, mode);
            super.onMeasure(adjustedWidthMeasureSpec, heightMeasureSpec);
        }
    }

}
