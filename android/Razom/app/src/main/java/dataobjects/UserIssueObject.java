package dataobjects;

/**
 * Created by ykomasyuk on 17.05.2014.
 */
public class UserIssueObject {
    private int _userId;
    private int _issueId;

    public int getUserId() {
        return _userId;
    }

    public void setUserId(int _userId) {
        this._userId = _userId;
    }

    public int getIssueId() {
        return _issueId;
    }

    public void setIssueId(int _issueId) {
        this._issueId = _issueId;
    }
}
