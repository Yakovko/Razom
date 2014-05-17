var rootController = require('../controllers/root')
    , api = require('../controllers/api');

module.exports = function(app) {
    //static
    app.get('/', rootController.home);
    app.get('/api/issue/create', api.createIssue);
    app.get('/api/issue/watch', api.watch);
    app.get('/api/issue/unwatch', api.unwatch);
    app.get('/api/issue/like', api.like);
    app.get('/api/issue/resolution', api.editResolution);
    app.get('/api/issue/apply', api.apply);
    app.get('/api/issue/disapply', api.disapply);
    app.get('/api/issue/comment/add', api.addComment);
    app.get('/api/issues', api.issues);


};