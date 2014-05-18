var userId = 'fw4gg6hge5y6h56hh';

var config = require('config')
    , HttpError = require('error').HttpError
    , IssueModel = require('models/issue/issue')
    , CategoryModel = require('models/category/category')
    , _ = require('underscore'),
    api = require('../controllers/api');

var controller = {
    home: function(req, res, next){
        res.render('index', {});
    },
    issues: function(req, res, next) {
        var query = api.issuesQuery();

        query.exec(function (err, issues) {
            if (err) throw err;

            api.issuesQuery({userId: userId}).exec(function (err, createdIssues) {
                if (err) throw err;

                api.issuesQuery({apply: userId}).exec(function (err, appliedToIssues) {
                    if (err) throw err;

                    api.issuesQuery({watcher: userId}).exec(function (err, watchingIssues) {
                        if (err) throw err;

                        watchingIssues.push(appliedToIssues);

                        res.render('issueList', {issues: issues, createdIssues: createdIssues, watchingIssues: watchingIssues});
                    });
                });
            });

        });
    },
    issue: function(req,res, n){

        //1 data about issue
        var id = req.params.id;

        IssueModel.getById(id, function(err, issue){
            if(err){
                console.log(err)
                return n(new HttpError("50X", "Server error"));
            }

            if(!issue){
                return n(new HttpError(404, "Cannot find issue"));
            }

            CategoryModel.getById(issue.category, function(err, category){
                if(err){
                    console.log(err)
                    return n(new HttpError("50X", "Server error"));
                }

                if(!category){
                    category = {name: 'Default'};
                }

                res.render('issue', {
                    issue:issue,
                    category: category
                });
            });


        });


    }
}
module.exports = controller;