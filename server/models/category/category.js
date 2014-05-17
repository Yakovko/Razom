var _ = require('underscore'),
    mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CategorySchema = new Schema({
    name: {
        type: String,
        required: true
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