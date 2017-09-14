'use strict';

const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const nodeEnvironment = process.env.NODE_ENV;
const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const autoprefixer = require('autoprefixer');

module.exports = {
  context: path.resolve(`${__dirname}/src`),
  entry: {
    bundle: './index',
    vendor: [
      'angular',
      'angular-ui-router',
      'angular-resource',
      'ng-file-upload',
      'angular-material'
    ]
  },
  devtool: 'source-map',
  output: {
    publicPath: '/',
    path: path.resolve(`${__dirname}/target/dist`),
    filename: '[name].js'
  },
  devServer: {
    inline: true
  },
  plugins: [
    new HtmlWebpackPlugin({
      entry: './index.js',
      template: './index.html',
      inject: 'body',
      hash: false
    }),
    new webpack.DefinePlugin({
      include_all_modules: function includeAllModulesGlobalFn(modulesArray, application) {
        modulesArray.forEach(function executeModuleIncludesFn(moduleFn) {
          moduleFn(application);
        });
      },
      ENVIRONMENT: JSON.stringify(nodeEnvironment)
    }),
    new ExtractTextPlugin('main.css', {allChunks: true})
  ],
  module: {
    loaders: [
      {test: /\.js$/, exclude: /node_modules/, loaders: ['ng-annotate', 'babel-loader']},
      {test: /\.html/, exclude: /(node_modules)/, loader: 'html-loader'},
      {test: /\.sass$/, loader: ExtractTextPlugin.extract('css?sourceMap!resolve-url!sass?sourceMap')},
      {test: /\.(jpg|png|svg|eot|otf|svg|ttf|woff|woff2)$/, loader: 'file?name=[path][name].[ext]'},
      {test: /\.js$/, loader: 'eslint-loader', exclude: /node_modules/}
    ]
  },
  postcss: [autoprefixer({browsers: ['last 2 versions']})]
};