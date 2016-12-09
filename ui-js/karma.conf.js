'use strict';

module.exports = function (config) {
  config.set({
    frameworks: ['jasmine'],
    browsers: ['PhantomJS'],
    files: [{ pattern: 'spec.bundle.js', watched: false }],
    preprocessors: { 'spec.bundle.js': ['webpack'] },
    plugins: [
      require('karma-jasmine'),
      require('karma-phantomjs-launcher'),
      require('karma-coverage'),
      require('karma-spec-reporter'),
      require('karma-webpack')
    ],
    webpack: {
      isparta: {
        embedSource: true,
        noAutoWrap: true,
        babel: {
          presets: ['es2015']
        }
      },

      devtool: 'inline-source-map',
      module: {
        loaders: [
          { test: /\.js$/, exclude: /node_modules/, loader: 'babel-loader' },
          { test: /\.html/, exclude: /(node_modules)/, loader: 'html-loader' },
          { test: /\.js/, exclude: /(node_modules|\.spec\.js)/, loader: 'isparta' }
        ]
      }
    },
    webpackServer: {
      noInfo: true // prevent console spamming when running in Karma!
    },

    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['spec', 'coverage'],

    coverageReporter: {
      reporters: [
        { type: 'html', dir: 'coverage', subdir: '.' },
        { type: 'text-summary', dir: 'coverage', subdir: '.' },
        { type: 'lcovonly', dir: 'coverage', subdir: '.' }
      ]
    },

    // web server port
    port: 9876,

    // enable colors in the output
    colors: true,

    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,

    autoWatch: false,

    singleRun: true,
  });

};