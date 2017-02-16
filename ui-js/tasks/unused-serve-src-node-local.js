/* Serve FE sources from src with Node.js BE running at localhost - not currently used */

import gulp from 'gulp';
import gutil from 'gulp-util';
import webpack from 'webpack';
import WebpackDevServer from 'webpack-dev-server';
import webpackConfig from '../webpack.config.js';
import LiveReloadPlugin from 'webpack-livereload-plugin';

gulp.task('serve-src', () => {
  let myConfig = Object.create(webpackConfig);
  myConfig.debug = true;
  myConfig.plugins.push(new webpack.DefinePlugin({
    localRunFE : false
  }));
  myConfig.plugins.push( new LiveReloadPlugin({
    port:3011,
    appendScriptTag:true
  }));

  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + "/src",
    proxy: {
      "*": 'http://localhost:'+ process.env.PORT
    },
    stats: {
      colors: true
    }
  }).listen(process.env.PORT-1, "0.0.0.0", function (err) {
    if (err) throw new gutil.PluginError("serve-src", err);
    gutil.log("[serve-src]", "http://localhost:"+ (process.env.PORT-1));
  });
});

gulp.task('serve-java', () => {
  let myConfig = Object.create(webpackConfig);
  myConfig.debug = true;
  myConfig.plugins.push(new webpack.DefinePlugin({
    localRunFE : true
  }));
  myConfig.plugins.push( new LiveReloadPlugin({
    port:3011,
    appendScriptTag:true
  }));

  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + "/src",
    proxy: {
      "**": 'http://localhost:8025',
      changeOrigin: true
    },
    stats: {
      colors: true
    }
  }).listen(process.env.PORT-1, "0.0.0.0", function (err) {
    if (err) throw new gutil.PluginError("serve-java", err);
    gutil.log('[serve-java]', 'http://localhost:8025');
  });
});