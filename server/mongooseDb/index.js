var mongoose = require('mongoose'),
    logger = require('libs/log')(module),
    config = require('config');

mongoose.connect('mongodb://' + config.get('db:ip') + '/' + config.get('db:nameDatabase'));

mongoose.connection.on('error', function (err) {
    logger.error(err)
});

mongoose.connection.on('open', function () {
    logger.info('Mongoose open connection: ' + 'mongodb://' + config.get('db:ip') + '/' + config.get('db:nameDatabase'));
});