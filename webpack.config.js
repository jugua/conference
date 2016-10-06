var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

var  nodeEnvironment = process.env.NODE_ENV;

var ExtractTextPlugin = require("extract-text-webpack-plugin");

module.exports = {

    entry: {
        bundle:"./src/index",
        vendor : ['angular', 'angular-ui-router']
    },
    devtool: "source-map",
    output: {
        publicPath: "/",
        path: __dirname + '/dist',
        filename: "[name].js"
    },
    plugins:[
        new HtmlWebpackPlugin({
            entry: 'src/index.js',
            template: 'src/index.html',
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
        })
    ],
    module: {
        loaders: [
            { test: /\.js$/, exclude: /node_modules/, loader: "babel-loader" },
            { test: /\.html/, exclude: /(node_modules)/, loader: 'html-loader' },
            { test: /\.sass$/, loader: ExtractTextPlugin.extract('css?sourceMap!sass?sourceMap' )},
            { test: /\.(jpg|png|svg|eot|otf|svg|ttf|woff|woff2)$/, loader:'file?name=[path][name].[ext]'}

        ]

    },

    plugins: [
        new ExtractTextPlugin("main.css", {allChunks: true})
    ]
};