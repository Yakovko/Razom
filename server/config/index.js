var nconf = require("nconf");
var path = require("path");

var node_site = process.env.NODE_SITE;
nconf.file('project', {file: path.join(__dirname, 'project.json')});

module.exports = nconf;