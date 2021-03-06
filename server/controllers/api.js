var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore'),
    IssueModel = require('models/issue/issue'),
    CategoryModel = require('models/category/category');

var controller = {
    createIssue: function(req, res, n) {
        var data = req.body;

        var issue = new IssueModel(data);

        issue.save(function(err) {
            if(err) {
                return n(new HttpError(err));
            }

            res.send({_id: issue._id});
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
    },
    categoryList: function(req, res, n) {
        var data = req.body;

        CategoryModel.find({}, {
            _v: -1,
            name: 1,
            _id: 1
        }, function(err, categories){
            if(err){
                return next(new HttpError(400, "Server error"));
            }
            res.send(categories);
        });

    },
    issues: function(req, res, n) {
        var query = controller.issuesQuery(req.query);

        query.exec(function (err, results) {
            if (err) throw err;

            res.send(results);
        });
    },
    issuesQuery: function(data) {
        if(!data)
            data = {};
        var category = data.category,
            userId = data.userId,
            state = data.status,
            apply = data.apply,
            watcher = data.watcher,
            id = data.id,
            lon = data.lon,
            lat = data.lat,
            radius = data.radius;
        var query = IssueModel.find({});

        if(category)
            query.where('category').equals(category);
        if(userId)
            query.where('userId').equals(userId);
        if(state)
            query.where('state').equals(state);
        if(apply)
            query.where('apply').equals(apply);
        if(watcher)
            query.where('watcher').equals(watcher);
        if(id)
            query.where('_id').equals(id);
        if(lon != undefined &&  lat  != undefined && radius != undefined) {
            lon = parseInt(lon);
            lat = parseInt(lat);
            radius = parseInt(radius);

            query.where('lon').lt(lon + radius);
            query.where('lon').gt(lon - radius);
            query.where('lat').lt(lat + radius);
            query.where('lat').gt(lat - radius);
        }

        return query;
    }
}
module.exports = controller;