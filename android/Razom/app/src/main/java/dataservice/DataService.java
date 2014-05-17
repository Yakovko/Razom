package dataservice;

import dataobjects.NewCommentObject;
import dataobjects.NewIssueObject;
import dataobjects.ResolutionObject;
import dataobjects.UserIssueObject;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by ykomasyuk on 17.05.2014.
 */
public interface DataService {
    @POST("/api/issue/create")
    void createIssue(@Body NewIssueObject newIssue, Callback<NewIssueObject> cb);

    @POST("/api/issue/watch")
    void watchIssue(@Body UserIssueObject userIssueObject, Callback<UserIssueObject> cb);

    @POST("/api/issue/unwatch")
    void unwatchIssue(@Body UserIssueObject userIssueObject, Callback<UserIssueObject> cb);

    @POST("/api/issue/like")
    void likeIssue(@Body UserIssueObject userIssueObject, Callback<UserIssueObject> cb);

    @POST("/api/issue/comment/add")
    void addComment(@Body NewCommentObject newCommentObject, Callback<NewCommentObject> cb);

    @POST("/api/issue/resolution")
    void postResolution(@Body ResolutionObject resolutionObject, Callback<ResolutionObject> cb);


}
