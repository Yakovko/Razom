package dataobjects;

/**
 * Created by ykomasyuk on 17.05.2014.
 */
public class ResolutionObject {
    private String _userId;
    private String _issueId;
    private String _message;

    public String getUserId() {
        return _userId;
    }

    public void setUserId(String _userId) {
        this._userId = _userId;
    }

    public String getIssueId() {
        return _issueId;
    }

    public void setIssueId(String _issueId) {
        this._issueId = _issueId;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String _message) {
        this._message = _message;
    }
}
