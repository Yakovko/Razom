var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore'),
    async = require('async'),
    CategoryModel = require('models/category/category'),
    IssueModel = require('models/issue/issue');

require("mongooseDb");

CREATE
var issue = new IssueModel({
    title: 'test title',
    description: 'test description',
    lat: 4,
    lon: 34,
    category: "454534fghhddf5",
    tags: ['tag1', 'tag2']
});
issue.save(function(err) {
    if(err) {
        console.log('issue done');
        return false;
    }

    console.log('issue done');
});

/*WATCH*/
IssueModel.watchUser("5377b256590016f418719cc6", "watchUserId2", function(err){
 if(err) {
 console.log('watchUser false');
 return false;
 }

 console.log('watchUser done');
 })

/*UNWATCH*/
/*
 IssueModel.unwatchUser("53777884d459967c076a08bb", "watchUserId2", function(err){
 if(err) {
 console.log('unwatchUser false');
 return false;
 }

 console.log('unwatchUser done');
 })*/

/*LIKE*/
/*
 IssueModel.like("53777884d459967c076a08bb", function(err){
 if(err) {
 console.log('like false');
 return false;
 }

 console.log('like done');
 })*/
/*RESOLUTION*/
/*

 async.waterfall([
 function(cb){
 IssueModel.getById("53777884d459967c076a08bb", cb);
 },
 function(issue, cb){
 if(!issue){
 cb("Cannot find Issue")
 }
 cb(null, issue);
 },
 function(issue, cb){
 issue.resolutionDescription = "dsgdfgdsgdg";
 issue.save(function(err){
 if(err){
 return cb("An error occurred. Please try again later");
 }
 cb(null);
 });
 }
 ],function(err){
 if( err ){
 console.log('resolution_description false');
 return false;
 }
 console.log('resolution_description true');
 })*/
/*APPLY*/
IssueModel.applyUser("5377b256590016f418719cc6", "applyUserId2", function(err){
 if(err) {
 console.log('applyUser false');
 return false;
 }

 console.log('applyUser done');
 })

/*DISAPPLY*/
/*
 IssueModel.disapplyUser("53777884d459967c076a08bb", "applyUserId", function(err){
 if(err) {
 console.log('disapplyUser false');
 return false;
 }

 console.log('disapplyUser done');
 })*/

/*COMMENT*/
/*
 IssueModel.addComment("53777884d459967c076a08bb", "commentUserId", "comment", function(err){
 if(err) {
 console.log('addComment false');
 return false;
 }

 console.log('addComment done');
 })*/

/*CATEGORY GET LIST*/
/*
 CategoryModel.find({}, function(err, categories){
 if(err){
 console.log("error get category list");
 return false;
 }
 console.log(categories);
 });*/
