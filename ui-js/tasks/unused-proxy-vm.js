/* Proxy all requests to Jenkins VM port 8025 - not currently used */

import gulp from 'gulp';
import gutil from 'gulp-util';
import webpack from 'webpack';
import WebpackDevServer from 'webpack-dev-server';
import webpackConfig from '../webpack.config.js';
import LiveReloadPlugin from 'webpack-livereload-plugin';

gulp.task('proxy-vm', () => {
  let myConfig = Object.create(webpackConfig);

  myConfig.debug = true;
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