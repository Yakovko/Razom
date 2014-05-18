var rootController = require('../controllers/root')
    , api = require('../controllers/api');

module.exports = function(app) {
    //static
    app.get('/', rootController.home);
    app.get('/issue/:id', rootController.issue);
    app.post('/api/issue/create', api.createIssue);
    app.post('/api/issue/watch', api.watch);
    app.post('/api/issue/unwatch', api.unwatch);
    app.post('/api/issue/like', api.like);
    app.post('/api/issue/resolution', api.editResolution);
    app.post('/api/issue/apply', api.apply);
    app.post('/api/issue/disapply', api.disapply);
    app.post('/api/issue/comment/add', api.addComment);
    app.get('/api/category_list', api.categoryList);
    app.get('/api/issues', api.issues);

    app.get('/issues', rootController.issues);
}