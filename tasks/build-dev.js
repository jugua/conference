'use strict';

import gulp from 'gulp';
import gutil from 'gulp-util';
import webpack from 'webpack';
import WebpackDevServer from 'webpack-dev-server';

import webpackConfig from '../webpack.config.js';

gulp.task('build-dev', () => {

  let myConfig = Object.create(webpackConfig);
  myConfig.devtool = "sourcemap";
  myConfig.debug = true;
  myConfig.output = {
    publicPath: "/",
    path: __dirname + '/../dist',
    filename: "[name].js"
  };

  webpack(myConfig).run((err, stats) => {
    if (err) throw new gutil.PluginError("build-dev", err);
    gutil.log("[build-dev]", stats.toString({
        colors: true
      })
    );
  });
});