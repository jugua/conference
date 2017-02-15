var gulp = require('gulp');
var gutil = require('gulp-util');
var webpack = require('webpack');

var webpackConfig = require('../webpack.config.js');

gulp.task('build', function () {
  var myConfig = Object.create(webpackConfig);

  myConfig.devtool = 'sourcemap';
  myConfig.debug = true;

  webpack(myConfig).run(function (err, stats) {
    if (err) throw new gutil.PluginError('build', err);
    gutil.log('[build]', stats.toString({
        colors: true
      })
    );
  });
});