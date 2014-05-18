var request = require('request');

request.post({
    headers: {'content-type' : 'application/x-www-form-urlencoded'},
    url:     'http://razom.batros.in.ua/api/issue/create',
    form:    {
        title: 'Відсутність мила у туалеті.',
        description: 'Всі мовчать, бо бояться. Але я невзвмозі більше терпіти це неподобство.  Час настав! Ми повинні захистити майбутнє для наших рук! ',
        lat: 4,
        lon: 34,
        media: [],
        resolutionDescription: 'Поверніть людям мило (з крєміком). ',
        category: "медицина  та гігієна ",
        tags: ['Часопис ']
    }
}, function(error, response, body){
    console.log(body);
});

request.post({
    headers: {'content-type' : 'application/x-www-form-urlencoded'},
    url:     'http://razom.batros.in.ua/api/issue/create',
    form:    {
        title: 'Ручка не працює. ',
        description: 'Пару гвинтів і 10 хвилин. ',
        lat: 4,
        lon: 34,
        media: [],
        resolutionDescription: 'Двері відкриваються не дуже добре, бо ручка на дверях що ведуть на баклон – зламана.  ',
        category: "інфраструктура ",
        tags: ['Часопис ']
    }
}, function(error, response, body){
    console.log(body);
});

request.post({
    headers: {'content-type' : 'application/x-www-form-urlencoded'},
    url:     'http://razom.batros.in.ua/api/issue/create',
    form:    {
        title: 'Ручка не працює. ',
        description: 'Пару гвинтів і 10 хвилин. ',
        lat: 4,
        lon: 34,
        media: [],
        resolutionDescription: 'Двері відкриваються не дуже добре, бо ручка на дверях що ведуть на баклон – зламана.  ',
        category: "інфраструктура ",
        tags: ['Часопис ']
    }
}, function(error, response, body){
    console.log(body);
});

request.post({
    headers: {'content-type' : 'application/x-www-form-urlencoded'},
    url:     'http://razom.batros.in.ua/api/issue/create',
    form:    {
        title: 'Накопичення мусору.  ',
        description: 'Винести мусор. Замінити мусорні пакети.  ',
        lat: 4,
        lon: 34,
        media: [],
        resolutionDescription: 'Переповнені мусорні кошики. ',
        category: "інфраструктура ",
        tags: ['Часопис ']
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
