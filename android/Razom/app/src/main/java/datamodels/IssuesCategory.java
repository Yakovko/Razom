package datamodels;


import ua.in.razom.app.R;

public enum IssuesCategory {
    ECOLOGY(R.color.ecology_color, R.drawable.pin02, R.drawable.icon02, R.string.ecology_title),
    TRANSPORT(R.color.transport_color, R.drawable.pin03, R.drawable.icon03, R.string.transport_title),
    COMMON(R.color.common_color, R.drawable.pin04, R.drawable.icon04, R.string.common_title),
    STUFF(R.color.stuff_color, R.drawable.pin05, R.drawable.icon05, R.string.stuff_title);
    private int color;
    private int pinIcon;
    private int menuIcon;
    private int title;
    private boolean isEnabled = true;

    IssuesCategory(int color, int pinIcon, int menuIcon, int title) {
        this.color = color;
        this.pinIcon = pinIcon;
        this.menuIcon = menuIcon;
        this.title = title;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getPinIcon() {
        return pinIcon;
    }

    public void setPinIcon(int pinIcon) {
        this.pinIcon = pinIcon;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
