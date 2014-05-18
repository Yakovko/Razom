package dataobjects;

import java.util.Date;

/**
 * Created by ykomasyuk on 18.05.2014.
 */
public class Issue {
    private String _id;
    private int __v;
    private String title;
    private String description;
    private Date dateCreate;
    private double lat;
    private double lon;
    private String userId; // Author Id
    private String category; // Category Id
    private String[] tags;
    private String[] media; //Photo attachment as Base64 String
    private int likes;
    private Comment[] comments; //Not sure what is returned
    private String resolutionDescription;
    private String[] apply; //Not sure what is returned
    private String[] watcher; //Not sure what is returned
    //    private String done;
    private int state;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getMedia() {
        return media;
    }

    public void setMedia(String[] media) {
        this.media = media;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public String getResolutionDescription() {
        return resolutionDescription;
    }

    public void setResolutionDescription(String resolutionDescription) {
        this.resolutionDescription = resolutionDescription;
    }

    public String[] getApply() {
        return apply;
    }

    public void setApply(String[] apply) {
        this.apply = apply;
    }

    public String[] getWatcher() {
        return watcher;
    }

    public void setWatcher(String[] watcher) {
        this.watcher = watcher;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
