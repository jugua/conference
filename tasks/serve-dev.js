'use strict';

import gulp from 'gulp';
import gutil from 'gulp-util';
import webpack from 'webpack';
import WebpackDevServer from 'webpack-dev-server';

import webpackConfig from '../webpack.config.js';

gulp.task('serve-dev', () => {
  let myConfig = Object.create(webpackConfig);
  myConfig.debug = true;

  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + "/dist-dev",
    stats: {
      colors: true
    }
  }).listen(3001, "0.0.0.0", function (err) {
    if (err) throw new gutil.PluginError("serve-dev", err);
    gutil.log("[serve-dev]", "http://localhost:3001/");
  });
});