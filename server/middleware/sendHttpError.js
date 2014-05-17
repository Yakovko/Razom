module.exports = function(req, res, next){

    res.sendHttpError = function(error){

        if( req.headers['x-requested-with'] == "XMLHttpRequest" ){
            res.status( error.status );
            res.send({
                error: error.message
            });
        }else{

            res.status( error.status );
            res.render('error', {
                error: error.message
            });
        }

    }

    next();

}