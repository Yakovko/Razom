var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore'),
    IssueModel = require('models/issue/issue');

require("mongooseDb");

/*
 CREATE
 var issue = new IssueModel({
 title: 'test title',
 description: 'test description',
 geo: {
 lat: 4,
 lon: 34
 },
 category: "454534fghhddf5",
 tags: ['tag1', 'tag2']
 });
 issue.save(function(err) {
 if(err) {
 console.log('issue done');
 return false;
 }

 console.log('issue done');
 });*/

/*WATCH*/
/*IssueModel.watchUser("53777884d459967c076a08bb", "watchUserId2", function(err){
 if(err) {
 console.log('watchUser false');
 return false;
 }

 console.log('watchUser done');
 })*/

/*UNWATCH*/
/*
IssueModel.unwatchUser("53777884d459967c076a08bb", "watchUserId2", function(err){
    if(err) {
        console.log('unwatchUser false');
        return false;
    }

    console.log('unwatchUser done');
})*/
/*APPLY*/
/*IssueModel.applyUser("53777884d459967c076a08bb", "applyUserId2", function(err){
 if(err) {
 console.log('applyUser false');
 return false;
 }

 console.log('applyUser done');
 })*/

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