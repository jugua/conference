/*
 Usage:

 gulp serve-src-java-local      > Serve FE sources from src with Java BE running at localhost
 gulp serve-src-java-vm         > Serve FE sources from src with Java BE running at Jenkins VM port 8025
 gulp build                     > Build FE sources to dist, do not minify
 gulp build-min                 > Build FE sources to dist, minified [default]

 gulp component --name <componentName>         > create a component boilerplate

 */

require('./tasks/serve-src-java-local');
require('./tasks/serve-src-java-vm');
require('./tasks/build');
require('./tasks/build-min');
require('./tasks/component');

var gulp = require('gulp');
gulp.task('default', ['build-min']);  // default alias


// if (!process.env.PORT){
//  process.env.PORT = 6089;
// }
//
// var gulp = require('gulp');
// var server = require('./server/server');
// require('./tasks/unused-serve-src-node-local');
// gulp.task('default', ['serve-src'], function () {
//  server();
// });
//
// require('./tasks/unused-proxy-vm');
