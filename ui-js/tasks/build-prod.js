'use strict';

import gulp from 'gulp';
import gutil from 'gulp-util';
import webpack from 'webpack';
import WebpackDevServer from 'webpack-dev-server';

import webpackConfig from '../webpack.config.js';

gulp.task('build-prod', () => {

  let myConfig = Object.create(webpackConfig);
  myConfig.devtool = false;
  myConfig.plugins = myConfig.plugins.concat(
    new webpack.DefinePlugin({
      "process.env": {
        "NODE_ENV": JSON.stringify("production")
      }
    }),
    new webpack.optimize.DedupePlugin(),
    new webpack.optimize.UglifyJsPlugin()
  );

  webpack(myConfig).run((err, stats) => {
    if (err) throw new gutil.PluginError("build-prod", err);
    gutil.log("[build-prod]", stats.toString({
        colors: true
      })
    );
  });
});