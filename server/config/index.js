var nconf = require("nconf");
var path = require("path");

nconf.file('project', {file: path.join(__dirname, 'project.json')});

module.exports = nconf;