var config = require('config')
    , HttpError = require('error').HttpError
    , _ = require('underscore');

var controller = {
    home: function(req, res, next){
        res.render('index', {
        });
    }
}
module.exports = controller;