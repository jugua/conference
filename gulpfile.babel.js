/*
 Usage:

 gulp                > run dev server - serving from src, listening on prot 3000
 gulp serve-src      > the same

 gulp build-dev      > build to dist-dev, do not uglify
 gulp serve-dev      > run dev server - serving from dist-dev, listening on prot 3001

 gulp build-prod     > build to dist, uglify
 gulp serve-prod     > run prod server, serve from dist, listening on prot 3002

 gulp component --name <componentName>         > create a component boilerplate

 */

'use strict';

const PORT = process.env.PORT || 8089;

import gulp from 'gulp';
import  server from './server';
import './tasks/serve-src';
import './tasks/serve-dev';
import './tasks/serve-prod';
import './tasks/build-dev';
import './tasks/build-prod';
import './tasks/component';

gulp.task('default',['build-dev','serve-src'], ()=> {
  server(8089);
});