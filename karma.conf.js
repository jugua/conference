// var jasmineWebpackPlugin = require('jasmine-webpack-plugin');
var webpack = require('webpack');
// var HtmlWebpackPlugin = require('html-webpack-plugin');
var  nodeEnvironment = process.env.NODE_ENV;
module.exports = function (config) {
    config.set({
        frameworks: ['jasmine'],
        files: [{ pattern: 'spec.bundle.js', watched: false }],
        preprocessors: { 'spec.bundle.js': ['webpack'] },
        browsers: ['PhantomJS'],
        plugins: [
            require("karma-jasmine"),
            require("karma-phantomjs-launcher"),
            require("karma-spec-reporter"),
            require("karma-webpack")
        ],
        webpack: {
            devtool: 'inline-source-map',
            path:'./',
            // entry: 'spec.bundle.js',
            // plugins:[
            //     new webpack.DefinePlugin({
            //         'INCLUDE_ALL_MODULES': function includeAllModulesGlobalFn(modulesArray, application) {
            //             modulesArryay.forEach(function executeModuleIncludesFn(moduleFn) {
            //                 moduleFn(application);
            //             });
            //         },
            //         'ENVIRONMENT': JSON.stringify(nodeEnvironment)
            //     }),
            //   // new jasmineWebpackPlugin()
            // ],
            module: {
                loaders: [
                    { test: /\.js$/, exclude: /node_modules/, loader: "babel-loader" },
                    { test: /\.html/, exclude: /(node_modules)/, loader: 'html-loader' },
                    { test: /\.sass$/, loaders: [ 'style', 'css', 'resolve-url', 'sass' ] }

                ]
            }
        },
        webpackServer: {
            noInfo: true // prevent console spamming when running in Karma!
        },

        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['spec'],

        // web server port
        port: 9876,

        // enable colors in the output
        colors: true,


        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,

        autoWatch: false,

        browsers: ['PhantomJS'],

        singleRun: true
    })

};