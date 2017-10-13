const path = require('path');
const webpack = require('webpack');
const cssnano = require('cssnano');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');
var HtmlWebpackPlugin = require('html-webpack-plugin');

const DEBUG = process.env.NODE_ENV !== 'production';

// > Root App
const APP_FOLDER = path.resolve(__dirname);
// > Dist
const DIST_FOLDER = path.resolve(APP_FOLDER, './target/dist/react');
const DIST_FOLDER_STYLE = path.resolve(DIST_FOLDER, './style');

const DIST_FILE_JS_BUNDLE = 'js/bundle.js';
const DIST_FILE_CSS_BUNDLE_NAME = 'bundle.css';
const DIST_FILE_CSS_BUNDLE = `./style/${DIST_FILE_CSS_BUNDLE_NAME}`;
// > Src
const SRC_FOLDER = path.resolve(APP_FOLDER, './src');
const SRC_FILE_JS_APP = path.resolve(SRC_FOLDER, 'index.jsx');

module.exports = {
  // > JS Input / Output
  entry: SRC_FILE_JS_APP,
  output: {
    path: DIST_FOLDER,
    publicPath: '',
    filename: DIST_FILE_JS_BUNDLE,
    sourceMapFilename: 'sourcemaps/[file].map',
  },
  // > Module Folders (packages and extensions)
  resolve: {
    modules: ['node_modules', APP_FOLDER],
    extensions: ['.js', '.json', '.jsx', '.css', '.scss'],
    descriptionFiles: ['package.json'],
  },
  // > Module Handles
  module: {
    rules: [
      // ESLint
      {
        enforce: 'pre',
        test: /\.(js|jsx)?$/,
        exclude: /node_modules/,
        loader: 'eslint-loader',
        options: {
          failOnWarning: false,
          failOnError: false
        },
      },
      // > JS / JSX
      {
        test: /\.(js|jsx)?$/,
        loader: 'babel-loader',
        include: [APP_FOLDER],
        exclude: /(node_modules)/,
        options: {
          presets: ['env', 'react', 'stage-2'],
        },
      },
      // > CSS / SCSS
      {
        test: /\.(css|scss|sass)?$/,
        use: ExtractTextPlugin.extract({
          fallback: 'style-loader',
          use: ['css-loader', 'sass-loader'],
          publicPath: DIST_FOLDER_STYLE,
        }),
      },
      {
        test: /\.(gif|png|jpe?g|svg)$/,
        use: [
          {
            loader: 'file-loader',
            options: {
              name: '[name].[ext]',
              publicPath: '../',
              outputPath: 'images/'
            }
          }
        ]
      },
      {
        test: /\.(eot|ttf|woff|woff2)$/,
        use: [
          {
            loader: 'file-loader',
            options: {
              name: '[name].[ext]',
              publicPath: '../',
              outputPath: 'fonts/oswald/'
              // useRelativePath: true,

            }
          }
        ]
      }
    ], // rules
  }, // module
  devtool: DEBUG ? 'source-map' : '',
  context: __dirname,
  target: 'web',
  plugins: DEBUG ?
      [
        // > Configure CSS Bundle file
        new ExtractTextPlugin({
          filename: DIST_FILE_CSS_BUNDLE,
          disable: false,
          allChunks: true,
        }),
        new HtmlWebpackPlugin({
          title: "Conference",
          template: 'src/root-react.html'
        })
      ] :
      [
        new webpack.DefinePlugin({
          'process.env': {
            NODE_ENV: JSON.stringify('production'),
          },
        }),
        new webpack.optimize.OccurrenceOrderPlugin(),
        // > Minimize JS
        new webpack.optimize.UglifyJsPlugin({
          sourceMap: false,
          mangle: false,
        }),
        // > CSS Bundle
        new ExtractTextPlugin({
          filename: DIST_FILE_CSS_BUNDLE,
          disable: false,
          allChunks: true,
        }),
        // > Minimize CSS
        new OptimizeCssAssetsPlugin({
          assetNameRegExp: DIST_FILE_CSS_BUNDLE_NAME,
          cssProcessor: cssnano,
          cssProcessorOptions: {
            discardComments: { removeAll: true },
          },
          canPrint: true,
        }),
      ], // plugins
  cache: false,
  watchOptions: {
    aggregateTimeout: 1000,
    poll: true,
  },
  devServer: {
    inline: true,
    historyApiFallback: true,
    contentBase: APP_FOLDER,
    compress: true,
    port: 7070,
    hot: true,
  },
};
