'use strict';

let webpack = require('webpack');
let HtmlWebpackPlugin = require('html-webpack-plugin');
let nodeEnvironment = process.env.NODE_ENV;
let path = require('path');
let ExtractTextPlugin = require("extract-text-webpack-plugin");
//let precss       = require('precss');
let autoprefixer = require('autoprefixer');


module.exports = {
    context: path.resolve(__dirname + '/src'),
    entry: {
        bundle: "./index",
        vendor: ['angular', 'angular-ui-router','angular-resource', 'ng-file-upload']
    },
    devtool: "source-map",
    output: {
        publicPath: "/",
        path: __dirname + '/dist',
        filename: "[name].js"
    },
    devServer: {
        inline:true
    },
    plugins: [
        new HtmlWebpackPlugin({
            entry: './index.js',
            template: './index.html',
            inject: 'body',
            hash: false
        }),
        new webpack.DefinePlugin({
            'include_all_modules': function includeAllModulesGlobalFn(modulesArray, application) {
                modulesArray.forEach(function executeModuleIncludesFn(moduleFn) {
                    moduleFn(application);
                });
            },
            'ENVIRONMENT': JSON.stringify(nodeEnvironment)
        }),
        new ExtractTextPlugin("main.css", {allChunks: true})
    ],
    module: {
        loaders: [
            { test: /\.js$/, exclude: /node_modules/, loader: "babel-loader" },
            { test: /\.html/, exclude: /(node_modules)/, loader: 'html-loader' },
            { test: /\.sass$/, loader: ExtractTextPlugin.extract('css?sourceMap!resolve-url!sass?sourceMap' ) },
            { test: /\.(jpg|png|svg|eot|otf|svg|ttf|woff|woff2)$/, loader:'file?name=[path][name].[ext]' },
            { test: /\.js$/,  loader: "eslint-loader", exclude: /node_modules/ }
        ]
    },
    postcss: [ autoprefixer({ browsers: ['last 2 versions'] }) ]
};