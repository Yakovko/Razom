var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore'),
    async = require('async'),
    IssueModel = require('models/issue/issue');
    CategoryModel = require('models/category/category');

require("mongooseDb");

IssueModel.remove({}, function(err){
    if(err){
        console.log("error issue");
        return false;
    }
    console.log("done")
});
CategoryModel.remove({}, function(err){
    if(err){
        console.log("category issue");
        return false;
    }
    console.log("done")
});