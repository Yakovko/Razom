package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import datamodels.IssuesCategory;

public class FilterCategoriesAdapter extends ArrayAdapter<IssuesCategory> {

    private final LayoutInflater inflater;
    private final int resource;
    private String displayName;
    private int selectedPosition;
    private int parentHeight;

    public FilterCategoriesAdapter(Context context, int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
//            holder.container = (RelativeLayout) convertView.findViewById(R.id.slidingMenuContainer);
//            holder.icon = (ImageView) convertView.findViewById(R.id.action_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        IssuesCategory model = getItem(position);
        holder.icon.setImageResource(model.getPinIcon());
        holder.title.setText(model.getTitle());


        return convertView;
    }


    public void setSelectedPosition(final int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public boolean isItemEnabled(int position) {
        return true;
    }

    public void setParentHeight(int height) {
        parentHeight = height;
        notifyDataSetChanged();
    }

    /**
     * Find UI elements once.
     * Save this references like tag for reusable view.
     */
    public class ViewHolder {
        public RelativeLayout container;
        public ImageView icon;
        public TextView title;

    }
}