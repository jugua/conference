/*
Serve FE sources from src with Java BE running at localhost.
FE in running at http://127.0.0.1:3000/, sources Live Reload supported.
Java BE should be started separately, with API listening at http://127.0.0.1:8081, like: mvn install -Pjava -Dtomcat
*/

var gulp = require('gulp');
var gutil = require('gulp-util');
var webpack = require('webpack');
var WebpackDevServer = require('webpack-dev-server');
var webpackConfig = require('../webpack.config.js');
var LiveReloadPlugin = require('webpack-livereload-plugin');

gulp.task('serve-src-java-local', function () {
  const myConfig = Object.create(webpackConfig);

  myConfig.debug = true;
  myConfig.plugins.push(new LiveReloadPlugin({
    port: 3011,
    appendScriptTag: true
  }));

  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + '/src',
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8081',
        secure: false
      }
    },
    stats: {
      colors: true
    }
  }).listen(3000, '127.0.0.1', function (err) {
    if (err) throw new gutil.PluginError('serve-src-java-local', err);
    gutil.log('[serve-src-java-local]', 'http://127.0.0.1:3000');
  });
});
