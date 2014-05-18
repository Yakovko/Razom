var request = require('request');


request.post({
    headers: {'content-type' : 'application/x-www-form-urlencoded'},
    // url:     'http://razom.batros.in.ua/api/issue/create',
    url:     'http://localhost:60010/api/issue/create',
    form:    {
        title: 'test title',
        description: 'test description',
        lat: 4,
        lon: 34,
        media: "",
        category: "454534fghhddf5",
        tags: ['tag1', 'tag2']
    }
}, function(error, response, body){
    console.log(body);
});
/*
var url = "http://razom.batros.in.ua/api/category_list",
    options = {
        type: "GET"
    };

request(url, options, function(err, response, buffer) {
    if(err) {
        console.log( 'request error' );
        return false;
    }

    console.log(response.body);
});*/
