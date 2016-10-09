'use strict';

import gulp from 'gulp';
import gutil from 'gulp-util';
import webpack from 'webpack';
import WebpackDevServer from 'webpack-dev-server';

import webpackConfig from '../webpack.config.js';

gulp.task('serve-prod', () => {
  let myConfig = Object.create(webpackConfig);
  myConfig.debug = true;

  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + "/dist",
    stats: {
      colors: true
    }
  }).listen(3002, "0.0.0.0", function (err) {
    if (err) throw new gutil.PluginError("serve-prod", err);
    gutil.log("[serve-prod]", "http://localhost:3002/");
  });
});