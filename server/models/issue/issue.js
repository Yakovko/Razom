var _ = require('underscore'),
    util = require('util'),
    async = require('async'),
    mongoose = require('mongoose');

var Schema = mongoose.Schema,
    ObjectId = Schema.ObjectId;

var IssueSchema = new Schema({
    title: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    dateCreate: {
        type: Date,
        default: new Date()
    },
    geo: {
        type: {},
        default: {
        }
    },
    userId: {
        type: String,
        default: "fw4gg6hge5y6h56hh",
        required: true
    },
    tags: {
        type: Array,
        default: []
    },
    media: {
        type: Array,
        default: []
    },
    likes: {
        type: Number,
        default: 0
    },
    comments: {
        type: Array,
        default: []
    },
    resolutionDescription: {
        type: String,
        default: ""
    },
    apply: {
        type: Array,
        default: []
    },
    watcher: {
        type: Array,
        default: []
    },
    done: {
        type: String,
        required: false
    },
    state: {
        type: Number,
        default: 0
    }
});
IssueSchema.statics.example = function(){};
IssueSchema.methods.example = function(){};
IssueSchema.statics.watchUser = function(issueId, userId, cb){
    this.update({ _id: issueId }, {
        $push: {
            watcher: userId
        }
    }, function (err, numberAffected, raw) {
        if(err){
            next(err);
            return false;
        }
        cb(null);
    });
}
IssueSchema.statics.unwatchUser = function(issueId, userId, cb){
    this.update({ _id: issueId }, {
        $pull: {
            watcher: userId
        }
    }, function (err, numberAffected, raw) {
        if(err){
            next(err);
            return false;
        }
        cb(null);
    });
}
IssueSchema.statics.like = function(issueId, cb){
    this.update({ _id: issueId }, {$inc: {likes: 1}}, function (err, numberAffected, raw) {
        if(err){
            next(err);
            return false;
        }
        cb(null);
    });
}
IssueSchema.statics.getById = function(issueId, cb){
    var query = {
        _id: issueId
    }
    this.find(query, null, function(err, issues){
        if( err ){
            cb("Server error");
            return false;
        }
        if( issues.length === 0 ){
            cb(null, false);
        }else{
            cb(null, issues[0]);
        }
    });
}

var IssueModel = mongoose.model('Issue', IssueSchema);
module.exports = IssueModel;