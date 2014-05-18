package dataservice;

import java.util.List;

import dataobjects.Category;
import dataobjects.Issue;
import dataobjects.NewCommentObject;
import dataobjects.NewIssueRequestObject;
import dataobjects.NewIssueResponse;
import dataobjects.ResolutionObject;
import dataobjects.UserIssueObject;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by ykomasyuk on 17.05.2014.
 */
public interface DataService {
    @POST("/api/issue/create")
    void createIssue(@Body NewIssueRequestObject newIssue, Callback<NewIssueResponse> cb);

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

    @GET("/api/category_list")
    void getCategoryList(Callback<List<Category>> cb);

    @GET("/api/issues")
    void getAllIssues(Callback<List<Issue>> cb);

    @GET("/api/issues")
    void getIssue(@Query("id") String id, Callback<Issue> cb);


}
