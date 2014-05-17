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
        type: ObjectId,
        default: 1,
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
        type: ObjectId,
        required: false
    },
    state: {
        type: Number,
        default: 0
    }
});

IssueSchema.statics.example = function(){};
IssueSchema.methods.example = function(){};

var IssueModel = mongoose.model('Issue', IssueSchema);
module.exports = IssueModel;