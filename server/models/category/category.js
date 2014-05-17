var _ = require('underscore'),
    mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CategorySchema = new Schema({
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
CategorySchema.statics.example = function(){};
CategorySchema.methods.example = function(){};
CategorySchema.statics.getById = function(categoryId, cb){
    var query = {
        _id: categoryId
    }
    this.find(query, null, function(err, categories){
        if( err ){
            cb("Server error");
            return false;
        }
        if( categories.length === 0 ){
            cb(null, false);
        }else{
            cb(null, categories[0]);
        }
    });
}

var CategoryModel = mongoose.model('Category', CategorySchema);
module.exports = CategoryModel;