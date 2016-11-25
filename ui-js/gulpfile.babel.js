/*
 Usage:

 gulp                > run webpack dev server - and api server
 gulp build-dev      > build to dist-dev, do not uglify
 gulp build-prod     > build to dist, uglify

 gulp component --name <componentName>         > create a component boilerplate

 */

'use strict';

if (!process.env.PORT){
  process.env.PORT = 6089;
}

import gulp from 'gulp';
import  server from './server/server';
import './tasks/serve-src';
import './tasks/build-dev';
import './tasks/build-prod';
import './tasks/component';

gulp.task('default',['build-dev','serve-src'], ()=> {
  server();
});

gulp.task('java',['serve-java']);