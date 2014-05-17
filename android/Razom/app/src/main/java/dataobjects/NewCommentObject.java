package dataobjects;

/**
 * Created by ykomasyuk on 17.05.2014.
 */
public class NewCommentObject {
    private String _userId;
    private String _issueId;
    private String _comment;

    public String get_userId() {
        return _userId;
    }

    public void set_userId(String _userId) {
        this._userId = _userId;
    }

    public String get_issueId() {
        return _issueId;
    }

    public void set_issueId(String _issueId) {
        this._issueId = _issueId;
    }

    public String get_comment() {
        return _comment;
    }

    public void set_comment(String _comment) {
        this._comment = _comment;
    }
}
