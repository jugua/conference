var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPLugin = require('extract-text-webpack-plugin');
var path = require('path');

var  nodeEnvironment = process.env.NODE_ENV;

module.exports = {
    context: path.resolve(__dirname + '/src'),
    entry: {
        bundle: "./index",
        vendor: ['angular', 'angular-ui-router']
    },
    devtool: "source-map",
    output: {
        publicPath: "/",
        path: __dirname + '/dist',
        filename: "[name].js"
    },
    plugins:[
        new HtmlWebpackPlugin({
            entry: './index.js',
            template: './index.html',
            inject: 'body',
            hash: false
        }),
        new webpack.DefinePlugin({
            'INCLUDE_ALL_MODULES': function includeAllModulesGlobalFn(modulesArray, application) {
                modulesArray.forEach(function executeModuleIncludesFn(moduleFn) {
                    moduleFn(application);
                });
            },
            'ENVIRONMENT': JSON.stringify(nodeEnvironment)
        }),
        new ExtractTextPLugin("[name].css")
    ],
    module: {
        loaders: [
            { test: /\.js$/, exclude: /node_modules/, loader: "babel-loader" },
            { test: /\.html/, exclude: /node_modules/, loader: 'html-loader' },
            { test: /\.sass$/, loader: ExtractTextPLugin.extract('style', '!css!resolve-url!sass') },
            { test: /\.jpg$/, loader: 'file?name=[path][name].[ext]' },
        ],

    }
};