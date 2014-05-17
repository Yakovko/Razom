var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore'),
    async = require('async'),
    CategoryModel = require('models/category/category');

require("mongooseDb");

var categories = [
    {
        name: "Social"
    },
    {
        name: "Test 1"
    },
    {
        name: "Test 2"
    }
]

CategoryModel.remove({});

CategoryModel.save(categories, function(err){
    if(err) {
        console.log('CategoryModel false');
        return false;
    }

    console.log('CategoryModel done');
})