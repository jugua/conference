/*
 Usage:

 gulp                > run dev server - serving from src, listening on prot 3000
 gulp serve-src      > the same

 gulp build-dev      > build to dist-dev, do not uglify
 gulp serve-dev      > run dev server - serving from dist-dev, listening on prot 3001

 gulp build-prod     > build to dist, uglify
 gulp serve-prod     > run prod server, serve from dist, listening on prot 3002

 */

'use strict';
var gulp = require('gulp');
var gutil = require("gulp-util");
var webpack = require("webpack");
var WebpackDevServer = require("webpack-dev-server");

var webpackConfig = require("./webpack.config.js");

// run dev server - serving from src
gulp.task("default", ["webpack-src-server"]);
gulp.task("serve-src", ["webpack-src-server"]);

// build dev dist - build to dist-dev
gulp.task("build-dev", ["webpack-build-dev"]);

// run dev server, serving from dist-dev
gulp.task("serve-dev", ["webpack-dev-server"]);

// prod dist build - build to dist, uglify
gulp.task("build-prod", ["webpack-build-prod"]);

// run prod server, serving from dist
gulp.task("serve-prod", ["webpack-prod-server"]);


gulp.task("webpack-build-prod", function (callback) {
  // modify some webpack config options
  var myConfig = Object.create(webpackConfig);
  myConfig.plugins = myConfig.plugins.concat(
    new webpack.DefinePlugin({
      "process.env": {
        // This has effect on the react lib size
        "NODE_ENV": JSON.stringify("production")
      }
    }),
    new webpack.optimize.DedupePlugin(),
    new webpack.optimize.UglifyJsPlugin()
  );

  // run webpack
  webpack(myConfig, function (err, stats) {
    if (err) throw new gutil.PluginError("webpack-build-prod", err);
    gutil.log("[webpack-build-prod]", stats.toString({
      colors: true
    }));
    callback();
  });
});

// modify some webpack config options
var myDevConfig = Object.create(webpackConfig);
myDevConfig.devtool = "sourcemap";
myDevConfig.debug = true;
myDevConfig.output = {
  publicPath: "/",
  path: __dirname + '/dist-dev',
  filename: "[name].js"
};

// create a single instance of the compiler to allow caching
var devCompiler = webpack(myDevConfig);

gulp.task("webpack-build-dev", function (callback) {
  // run webpack
  devCompiler.run(function (err, stats) {
    if (err) throw new gutil.PluginError("webpack-build-dev", err);
    gutil.log("[webpack-build-dev]", stats.toString({
      colors: true
    }));
    callback();
  });
});

gulp.task("webpack-src-server", function (callback) {
  // modify some webpack config options
  var myConfig = Object.create(webpackConfig);
  myConfig.debug = true;
  // Start a webpack-dev-server
  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + "/src",
    stats: {
      colors: true
    }
  }).listen(3000, "localhost", function (err) {
    if (err) throw new gutil.PluginError("webpack-src-server", err);
    gutil.log("[webpack-src-server]", "http://localhost:3000/");
  });
});

gulp.task("webpack-dev-server", function (callback) {

  // modify some webpack config options
  var myConfig = Object.create(webpackConfig);
  myConfig.debug = true;
  // Start a webpack-prod-server
  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + "/dist-dev",
    stats: {
      colors: true
    }
  }).listen(3001, "localhost", function (err) {
    if (err) throw new gutil.PluginError("webpack-dev-server", err);
    gutil.log("[webpack-dev-server]", "http://localhost:3001/");
  });
});

gulp.task("webpack-prod-server", function (callback) {

  // modify some webpack config options
  var myConfig = Object.create(webpackConfig);
  myConfig.debug = true;
  // Start a webpack-prod-server
  new WebpackDevServer(webpack(myConfig), {
    contentBase: __dirname + "/dist",
    stats: {
      colors: true
    }
  }).listen(3002, "localhost", function (err) {
    if (err) throw new gutil.PluginError("webpack-prod-server", err);
    gutil.log("[webpack-prod-server]", "http://localhost:3002/");
  });
});