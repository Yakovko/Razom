var express = require('express'),
    app = express(),
    route = require('routes/route'),
    http = require('http'),
    HttpError = require('error').HttpError,
    exphbs  = require('express3-handlebars'),
    config = require("config"),
    fs = require("fs");

require("mongooseDb");
var numberquery = 0;
app.use(express.favicon());


var hbs = exphbs.create({
    // Specify helpers which are only registered on this instance.
    helpers: {
        foo: function () { return 'FOO!'; }
    }
});

app.configure(function() {
    app.use(express.static(__dirname + '/public/'));
    app.use(express.cookieParser());
    app.use(express.bodyParser({ keepExtensions: true, uploadDir: './tmp' }));
    app.set('views', __dirname + "/views");

    app.engine('.hbs', exphbs({
        extname: '.hbs',
        defaultLayout: 'main',
        layoutsDir: __dirname + '/views/layouts'
    }));
    app.set('view engine', '.hbs');
});


app.use(function(req, res, next){

    numberquery += 1;
    console.log(numberquery);

    next();

})

app.use( require("middleware/sendHttpError") );

require('libs/handlebars');
//routing
route(app);

//404
app.use(function(req, res, next){
    res.render('error', {
        error: {
            status: 404,
            message: "No data"
        }
    });
    res.status(404);
});

app.use(function(err, req, res, next){

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
console.log("Web server listening: " + config.get("port"));