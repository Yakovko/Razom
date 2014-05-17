
/*

 $gt >
 $gte >=

 $lt <
 $lte <=

 $exists : true
 $type  номер типа поля  name: {$type:2 } - все документы, у которых name текстовое поле
 $regex


 */

/*
 $or

 Принимает массив запросов
 */

db.users.find({
    $or: [
        {interests: {$exists: true}},
        {name: {$regex: "^М"}}
    ]
})

/*
 $and

 Принимает массив запросов
 отдается любой документ попадающий под каждый запрос

 чаще всего можно сократить такой запрос, не используя $and

 */

db.users.find({
    $and: [
        {interests: {$exists: true}},
        {name: {$regex: "^M"}}
    ]
})

db.users.find({
    interests: {$exists: true},
    name: {$regex: "^М"}
})

/*
 $all
 поиск документов у которых например в массиве есть и pizza и bear
 */
db.accounts.find({
    interests: {$all: ["pizza", "bear"]}
});


/*
 $in
 поиск документов у которых например в массиве есть или pizza или bear
 может относиться не только к массиву а и например к тествому полю
 name: "Pet9"

 */

db.accounts.find({
    interests: {$in: ["pizza", "bear"]}
});


db.proudct.find({
    price: {$gt: 10000},
    reviews.rating : 5
})

/*cursor
 cur = db.users.find();null;

 все методы с cur возвращают новый cur
 cur.limit(5) - отдаст только 5 значений

 cur.sort


 последовательность выполнения

 Sort - Skip - Limit
 * */

cur.sort({name: -1})

/*
 cur.skip(2) - пропускает n первых документов
 */

cur.skip(4)


cur = db.exam.find({type: "exam"}).sort({score: -1}).skip(50).limit(20);


/*Count*/
db.scors.count();
db.scors.count({name: "Pet9"});

/*update*/
db.users.update({1},{2})
{1} - поиск необходимых документов
{2} - новый документ
происходит полная замена найденых документов, документом {2}


/*
 $set - установить какое то поле в документ
 */
db.people.update({name: "CHANGE"}, {$set: {age: 30}})

/*
 $inc - инкрементировать поле
 */

db.people.update({name: "CHANGE"}, {$inc: {age: 30}})

db.users.update(
    {username: "splunler"},
    {$set: {
        country: "RU"
    }}
)

/*
 $unset - удаляет поле
 */

db.people.update(
    {name: "CHANGE"},
    {$unset: {
        _id: 1
    }}
)

/*

 $push,
 $pop,
 $pull,
 $pushAll,
 $pullAll,
 $addToSet

 */

db.arrays.insert(
    {_id: 0
        a: [1, 2, 3, 4]
    }
)

/*добавляет в конец одно значени*/
db.arrays.update(

)

/*добавляет в конец массив*/
db.arrays.update(
    {
        _id: 0
    },
    {
        $push: {
            a: 50
        }
    }
)

/*удаляет начальный элемнет*/
db.arrays.update(
    {
        _id: 0
    },
    {
        $pop: {
            a: -1
        }
    }
)

/*удаляет одно значение и не важно где оно находиться*/
db.arrays.update(
    {
        _id: 0
    },
    {
        $pull: {
            a: 6
        }
    }
)

/*удаляет значения и не важно где оно находиться*/
db.arrays.update(
    {
        _id: 0
    },
    {
        $pullAll: {
            a: [4, 5]
        }
    }
)

/*Добавлет значения если таких еще нет в массиве*/
db.arrays.update(
    {
        _id: 0
    },
    {
        $addToSet: {
            a: [4, 5]
        }
    }
)


/*
 upsert: true
 Если нет документов для которых можно применить update создается новый документ

 */

db.people.update(
    {
        name: "George"
    },
    {
        $set: {
            age: 50
        }
    },
    {
        upsert: true
    }
)

/*
 multi: true
 Если выставить этот параметр то обновятся все документы,
 если не выставить то только один

 */

db.people.update(
    {},
    {
        $set: {
            title: "Dr"
        }
    },{
        multi: true
    }
)

/*
 remove()
 */

db.people.remove({})


/*

 db.runCommand({ getLastError: 1})

 показывает состояние последнего действия
 */


/*
 Создание индекса
 */

db.school.ensureIndex({student_id: 1})

/*
 Создание уникального индекса
 */

db.school.ensureIndex({student_id: 1}, {unique: true})

/*
 Создание уникального индекса с удалением всех повторяющихся записей
 Тоесть мы создаем уникальный индекс и есть две записи с одинаковым полем, которое мы индексируем.
 Mongdb в этом случае удалит какое-то одно, контролировать какое - не возможности.

 Уникальный индекс для поля adress не может создаваться если в коллекции не все документы
 имеют это поле. Получиться что некоторые документы, у которых нет этого поля, будут содержать
 полк adress = null. Что приведт к дубликации. ДЛя таких случаев можно использовать
 Sparse индексы.
 */

db.school.ensureIndex({student_id: 1}, {unique: true,  dropDups: true})


/*
 Создание Sparse индекса
 Создает индексы только для тех документов, которые содержат индексируемое поле.
 Осторожно, про сортировке вернет не все записи, а только те которые имеют
 проиндексированное поле.

 */

db.addresses.ensureIndex( { "adress": 1 }, { sparse: true } )

/*
 Найти все индексы
 */

db.system.indexes.find()

/*
 Найти индекса с определенной коллекции
 */
db.students.getIndexes()

/*
 Убрать индексы
 */
db.students.dropIndex({'student_id': 1})

/*
 Можем добавлять индексы к вложенным документам
 */
db.students.ensureIndex({addresses.phones: 1})

/*
 explaine()
 */
db.students.find({name: "Peter"}).explaine()

/*
 Получить статистику по коллекции
 */
db.students.stats()
db.students.totalIndexSize()

/*
 Можно с помощью hint указать какой индекс использовать
 */

db.students.find({'name': "peter"}).hint({'name':1})
db.students.find({'name': "peter"}).hint({$natural:1})

/*
 Можно использовать геопозиционные индексы
 */

db.students.find({location: {$near: [50, 20]}});

/*
 Посмотреть профайлер
 */

db.system.profile.find().pretty()
/*так же можно посмотреть не весь профайлер а только интересующую часть
 в принципе мы можем работать с профайлером как с таблицей и запрашивать от туда
 только интересующую нас информацию
 */
db.system.profile.find({
    ns: 'school.students'
}).sort({
    ts: 1
}).pretty()

db.system.profile.find({
    ns: 'school.students',
    millis: {$gt: 1}
}).sort({
    ts: 1
}).pretty()

//все запросы длинне чем 1 секунда
db.system.profile.find({millis: {$gt: 1000}}).sort({ts: -1})

/*
 Посмотреть уровень профайлера
 */
db.getProfilingLevel()
db.getProfilingStatus()

db.setProfilingLevel(1, 200)
// 1 уровень
// 200 кол-во миллисекунд для slow query
db.setProfilingLevel(0)
// 0 уровень - выключить весь профайлинг

/*
 общий обзор что делает абза данных
 */
mongotop 15

/*
 АГРЕГАЦИЯ
 */

//агрегироваь все записи по категории и вывести сумму
//товаров находящуюся в каждой изх категории
db.products.aggregate([{
    $group:{
        "_id": "$category",
        num_products: {
            $sum: 1
        }
    }
}])
db.products.aggregate([{
    $group:{
        _id: {
            'price': '$price'
        },
        num_products: {
            $sum: 1
        }
    }
}])
db.products.aggregate([{
    $group:{
        _id: {
            'category': "$category",
            'price': '$price'
        },
        num_products: {
            $sum: 1
        }
    }
}])

/*
 Aggregation expression Overview
 */
$sum
$avg
$min
$max
$push
$addToSet
$first
$last


/*
 $sum
 суммирует значение '$price' в разрезе
 какого либо group by
 */

db.products.aggregate([{
    $group: {
        _id: "$category",
        sum_price: {
            $sum: '$price'
        }
    }
}])

db.zips.aggregate([{
    $group: {
        '_id': '$state',
        'population': {
            $sum: '$pop'
        }
    }
}])

/*
 $avg
 */

db.products.aggregate([{
    $group: {
        _id: '$category',
        sum_avg: {
            $avg: '$price'
        }
    }
}])

db.zips.aggregate([{
    '$group': {
        '_id':'$state',
        'average_pop': {
            '$avg': '$pop'
        }
    }
}])


/*
 $addToSet
 добавляет значения в массив, добавляет только уникальные записи
 */

db.zips.aggregate([{
    "$group": {
        "_id" : "$city",
        "postal_codes": {
            "$addToSet": "$_id"
        }
    }
}])


/*
 $push
 добавляет значения в массив, добавляет все записи
 тоесть возможны дубликаты
 */

/*
 min
 max
 */

db.products.aggregate([{
    "$group": {
        "_id": "$category",
        "max": {
            "$max": '$price'
        }
    }
}])


db.zips.aggregate([{
    "$group": {
        "_id": "$state",
        "pop": {
            "$max": '$pop'
        }
    }
}])

/*
 Возможно также двойное объеденение
 */

db.fun.aggregate([
    {
        $group:{
            _id:{a:"$a", b:"$b"},
            c:{$max:"$c"}}
    },
    {
        $group:{
            _id:"$_id.a",
            c:{$min:"$c"}
        }
    }
])

/*
 $project - изменяет текущую структуру документов на другую

 'state': 1 - если хотим что бы значение осталось таким же
 '_id': 0 - это значение надо отбросить
 'city': {$toLower: '$city'} - перевод в нижний регистр
 */


// $multiply - это умножение
db.products.aggregate([{
    $project:{
        _id: 0,
        'maker': {$toLower: "$category"},
        'detail': {
            'category': '$category',
            'price': {
                $multiply: ['$price', 10]
            }
        },
        'item': '$name'
    }
}])


db.zips.aggregate([{
    '$project': {
        '_id': 0,
        'city': {$toLower: '$city'},
        'pop': 1,
        'state': 1,
        'zip': '$_id'
    }
}])


/*
 $match
 позволяет отфильтровать значения для агрегации
 Например сначала отфильтруем нужные значения, а потом по ним уже как то сгрупируем данные
 */

db.zips.aggregate([{
    '$match': {
        'pop': {
            '$gt': 100000
        }
    }
}])

/*
 $sort
 */


db.zips.aggregate([{
    '$match': {
        'state': 'AL'
    }
},{
    '$sort': {
        'state': 1,
        'city': 1
    }
}])


/*
 skip and limit
 */

/*
 $first and $last
 отдает первое значение в агрегированных документах и последнее
 соттветственно
 */

db.fun.aggregate([
    {$match:{a:0}},
    {$sort:{c:-1}},
    {$group:{_id:"$a", c:{$first:"$c"}}}
])


/*
 $unwind
 допустим у нас есть один документ
 */

{
    name: "titlePost"
    tags: ['firstTag', 'secondTag']
}

//если мы сделаем
db.test.aggregate([{
    $unwind: $tags
}])

//то мы в результуте получим два документа
{
    name: "titlePost"
    tags: 'firstTag'
},
{
    name: "titlePost"
    tags: 'secondTag'
}
/*тоесть мы разбиваем массив, для того что бы потом можно было както сгрупировать получившиеся 
 документы

 возможно применение двойного $unwind, когда у нас есть такие документы
 {
 name: "titlePost"
 tags: ['firstTag', 'secondTag'],
 colors: ['red', 'green']
 }
 */
db.test.aggregate([{$unwind: $tags}, {$unwind: $colors} ])

/*Репликация*/

//просмотреть статус репликации
rs.status()

//проверить мастер ли это 
rs.isMaster()

//переводит SECO
rs.slaveOk()


//проспотреть конфигурацию replication set
rs.conf()

//все возможные команды
rs.help()


/*
 Когда один сервер выходит из строя, новый PRIMARY
 находиться примерно за несколько секунд.\

 Когда старый сервер восстанавливается, то на него
 копируется все данные, которые были изменены
 пока сервер был в нерабочем состоянии.

 Также возможна ситуация, когда на PRIMARY записались данные
 а до SECONDARY они не дошли, потому что PRIMARY упал.
 Когда он восстановиться, он увидит что у нового PRIMARY
 нет этой записи, и тогда он запишет ее в файл а с своего
 набра данных эту запись удалит.

 В драйвере к разным языкам программирования, можно указывать
 сразу несколько хотсов для соеденения.

 Если мы делаем какие-то операции с mongo и неожиданно
 PRIMARY сервер отвалился то наша операция завершиться неудачей
 и программа прекратиит работу
 */

/*Sharding*/
