var express = require('express'),
    app = express(),
    route = require('routes/route'),
    http = require('http'),
    HttpError = require('error').HttpError,
    logger = require("libs/log")(module),
    i18n = require("i18n"),
    hbs,
    exphbs  = require('express3-handlebars'),
    config = require("config");


require("mongooseDb");
// Passport configuration
require('auth');
app.use(express.favicon());

//Il8n configuration
i18n.configure({
    locales:['en', 'ru'],
    directory: __dirname + '/locales',
    extension: '.json'
});

app.configure(function() {
    app.use(express.static(__dirname + '/frontend/public/'));
    if( process.env.NODE_SITE === "development" ) app.use(express.static(__dirname + '/frontend/dev/'));
    app.use(express.cookieParser());
    app.use(express.bodyParser({ keepExtensions: true, uploadDir: './tmp' }));
    app.set('views', __dirname + "/views");
    app.engine('.hbs', exphbs({extname: '.hbs'}));
    app.set('view engine', '.hbs');

    // init i18n module for this loop
    app.use(i18n.init);
});

app.use( require("middleware/sendHttpError") );
app.use(function(req, res,  next){
    res.setLocale('en');
    next();

})



//routing
route(app);

//404
app.use(function(req, res, next){
    logger.log('warn', { status: 404, url: req.url });
    res.render('error', { status: 404, url: req.url });
    res.status(404);
});

app.use(function(err, req, res, next){

    logger.log('error', { error: err });

    if( typeof err == "number"){
        err = new HttpError(err);
    }

    if( err instanceof HttpError ){
        res.sendHttpError(err);
    }else{

        if( app.get("env") == "development" ){
            express.errorHandler()(err, req, res, next);
        }else{
            express.errorHandler()(err, req, res, next);
            res.send(500);
        }
    }

})

//create server
var server = http.createServer(app);
server.listen(config.get("port"));
logger.info("Web server listening: " + config.get("port"));