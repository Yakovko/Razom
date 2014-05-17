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
    },

    watch: function(req, res, n) {
        var data = req.body;

        IssueModel.watchUser(data.issueId, data.userId, function(err){
            if(err) {
                return n(new HttpError(err));
            }

            res.send();
        })
    },

    unwatch: function(req, res, n) {
        var data = req.body;

        IssueModel.unwatchUser(data.issueId, data.userId, function(err){
            if(err) {
                return n(new HttpError(err));
            }

            res.send();
        })
    },

    like: function(req, res, n) {
        var data = req.body;

        IssueModel.like(data.issueId, function(err){
            if(err) {
                return n(new HttpError(err));
            }

            res.send();
        })
    },

    editResolution: function(req, res, n) {
        var data = req.body;

        async.waterfall([
            function(cb){
                IssueModel.getById(data.userId, cb);
            },
            function(issue, cb){
                if(!issue){
                    cb("Cannot find Issue");
                }
                cb(null, issue);
            },
            function(issue, cb){
                issue.resolutionDescription = data.message;
                issue.save(function(err){
                    if(err){
                        return cb("An error occurred. Please try again later");
                    }
                    cb(null);
                });
            }
        ],function(err){
            if( err ){
                return n(new HttpError(400, err));
            }
            res.send();
        })
    },
    apply: function(req, res, n) {
        var data = req.body;

        IssueModel.applyUser(data.issueId, data.userId, function(err){
            if(err) {
                return n(new HttpError(err));
            }

            res.send();
        })
    },

    disapply: function(req, res, n) {
        var data = req.body;

        IssueModel.disapplyUser(data.issueId, data.userId, function(err){
            if(err) {
                return n(new HttpError(err));
            }

            res.send();
        })
    },

    addComment: function(req, res, n) {
        var data = req.body;

        IssueModel.addComment(data.issueId, data.userId, data.message, function(err){
            if(err) {
                return n(new HttpError(err));
            }

            res.send();
        })
    }
}
module.exports = controller;