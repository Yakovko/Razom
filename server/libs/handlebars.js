var exphbs  = require('express3-handlebars'),
    hbs;

hbs = exphbs.create({
    // Specify helpers which are only registered on this instance.
    helpers: {
        example: function () { return 'example!'; }
    }
});