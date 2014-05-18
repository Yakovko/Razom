var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore');

var controller = {
    home: function(req, res, next){
        res.render('index', {});
    },
    map: function(req, res, next){
        res.render('map', {});
    }
}
module.exports = controller;