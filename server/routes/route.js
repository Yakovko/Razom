var rootController = require('../controllers/root')
    , api = require('../controllers/api');

module.exports = function(app) {
    //static
    app.get('/', rootController.home);
    app.get('/api/issue/create', api.createIssue);

}