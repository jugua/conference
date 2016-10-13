'use strict';

import gulp from 'gulp';
import gutil from 'gulp-util';
import webpack from 'webpack';
import WebpackDevServer from 'webpack-dev-server';

import webpackConfig from '../webpack.config.js';

gulp.task('serve-src', () => {
  let myConfig = Object.create(webpackConfig);
  myConfig.debug = true;

  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + "/src",
    proxy: {
      "*": 'http://localhost:8089'
    },
    stats: {
      colors: true
    }
  }).listen(3000, "0.0.0.0", function (err) {
    if (err) throw new gutil.PluginError("serve-src", err);
    gutil.log("[serve-src]", "http://localhost:3000/");
  });
});