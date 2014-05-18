package dataobjects;

import java.util.Date;

/**
 * Created by ykomasyuk on 18.05.2014.
 */
public class Issue {
    private String _id;
    private String title;
    private String description;
    private Date dateCreate;
    private double lat;
    private double lon;
    private String userId; // Author Id
    private String category; // Category Id
    private String[] tags;
    private String media; //Photo attachment as Base64 String
    private int likes;
    private Comment[] comments; //Not sure what is returned
    private String resolutionDescription;
    //  private String[] apply; //Not sure what is returned
//  private String[] watcher; //Not sure what is returned
    private String done;
    private int state;
}
