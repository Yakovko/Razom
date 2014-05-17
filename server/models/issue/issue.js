var _ = require('underscore'),
    util = require('util'),
    async = require('async'),
    mongoose = require('mongoose');

var Schema = mongoose.Schema;
var IssueSchema = new Schema({
    dateCreate: {
        type: Date,
        default: new Date(),
        required: true
    },
    lastModify: {
        type: Date,
        default: new Date(),
        required: true
    },
    name: {
        type: String,
        default: "Default name",
        required: true
    },
    userId: {
        type: String,
        required: false
    },
    isPublic: {
        type: Boolean,
        default: true,
        required: true
    },

    type: {
        type: String,
        default: "guest",
        required: true
    },

    share: {
        type: Array,
        default: [],
        required: false
    },
    groups: {
        type: Array,
        default: [],
        required: false
    },

    description: {
        type: String,
        default: "",
        required: true
    },

    shareLink: {
        type: String,
        default: ""
    }

});

IssueSchema.statics.example = function(){};
IssueSchema.methods.example = function(){};

var ProjectModel = mongoose.model('Project', projectSchema);
module.exports = ProjectModel;