var config = require('config')
    , HttpError = require('error').HttpError
    , IssueModel = require('models/issue/issue')
    , CategoryModel = require('models/category/category')
    , _ = require('underscore');

var controller = {
    home: function(req, res, next){
        res.render('index', {});
    },
    map: function(req, res, next){
        res.render('map', {});
    },
    issue: function(req,res, n){

        //1 data about issue
        var id = req.params.id;

        IssueModel.getById(id, function(err, issue){
            if(err){
                console.log(err)
                return n(new HttpError("50X", "Server error"));
            }

            if(!issue){
                return n(new HttpError(404, "Cannot find issue"));
            }

            CategoryModel.getById(issue.category, function(err, category){
                if(err){
                    console.log(err)
                    return n(new HttpError("50X", "Server error"));
                }

                if(!category){
                    category = {name: 'Default'};
                }

                res.render('issue', {
                    issue:issue,
                    category: category
                });
            });


        });


    }
}
module.exports = controller;