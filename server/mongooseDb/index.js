var mongoose = require('mongoose'),
    config = require('config');

mongoose.connect(config.get('mongoUrl'));

mongoose.connection.on('error', function (err) {
    console.log("mongodb error");
});

mongoose.connection.on('open', function () {
    console.log("mongodb open");
});