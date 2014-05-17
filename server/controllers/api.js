var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore'),
    IssueModel = require('models/issue/issue');

var controller = {
    createIssue: function(req, res, n) {
        var data = req.body;

        var issue = new IssueModel(data);

        issue.save(function(err) {
            if(err) {
                return n(new HttpError(err));
            }

            res.send(issue);
        })
    }
}
module.exports = controller;