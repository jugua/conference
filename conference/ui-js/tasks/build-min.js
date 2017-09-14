var gulp = require('gulp');
var gutil = require('gulp-util');
var webpack = require('webpack');

var webpackConfig = require('../webpack.config.js');

gulp.task('build-min', function () {
  var myConfig = Object.create(webpackConfig);

  myConfig.devtool = false;
  myConfig.plugins = myConfig.plugins.concat(
    new webpack.DefinePlugin({
      'process.env': {
        'NODE_ENV': JSON.stringify('production')
      }
    }),
    new webpack.optimize.DedupePlugin(),
    new webpack.optimize.UglifyJsPlugin()
  );

  webpack(myConfig).run(function (err, stats) {
    if (err) throw new gutil.PluginError('build-min', err);
    gutil.log('[build-min]', stats.toString({
        colors: true
      })
    );
  });
});