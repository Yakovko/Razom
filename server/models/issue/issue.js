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
};
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
};
IssueSchema.statics.applyUser = function(issueId, userId, cb){
    this.update({ _id: issueId }, {
        $push: {
            apply: userId
        }
    }, function (err, numberAffected, raw) {
        if(err){
            next(err);
            return false;
        }
        cb(null);
    });
};
IssueSchema.statics.disapplyUser = function(issueId, userId, cb){
    this.update({ _id: issueId }, {
        $pull: {
            apply: userId
        }
    }, function (err, numberAffected, raw) {
        if(err){
            next(err);
            return false;
        }
        cb(null);
    });
};
IssueSchema.statics.addComment = function(issueId, userId, message, cb){
    this.update({ _id: issueId }, {
        $push: {
            comments: {
                user: userId,
                message: message
            }
        }
    }, function (err, numberAffected, raw) {
        if(err){
            next(err);
            return false;
        }
        cb(null);
    });
};


var IssueModel = mongoose.model('Issue', IssueSchema);
module.exports = IssueModel;