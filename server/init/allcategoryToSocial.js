var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore'),
    async = require('async'),
    IssueModel = require('models/issue/issue');

console.log('start');

require("mongooseDb");

IssueModel.update({}, {category: "5378fa92124279bb200cb325"}, {multi: true}, function(err){
    console.log(err);
    if(err){
        console.log('false');
        return false;
    }
    console.log('done')
});


IssueModel.update({}, {comments: []}, {multi: true}, function(err){
    console.log(err);
    if(err){
        console.log('false');
        return false;
    }
    console.log('done')
});