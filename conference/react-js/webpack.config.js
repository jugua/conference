const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');

const DEBUG = process.env.NODE_ENV !== 'production';

// >> Target Structure <<
// > Root App
const APP_FOLDER = path.resolve(__dirname, './app');
// > Dist
const DIST_FOLDER = path.resolve(APP_FOLDER, './dist');
const DIST_FOLDER_STYLE = path.resolve(DIST_FOLDER, './style');

const DIST_FILE_JS_BUNDLE = 'js/bundle.js';
const DIST_FILE_CSS_BUNDLE_NAME = 'bundle.css';
const DIST_FILE_CSS_BUNDLE = `style/${DIST_FILE_CSS_BUNDLE_NAME}`;
// > Src
const SRC_FOLDER = path.resolve(APP_FOLDER, './src');
const SRC_FILE_JS_APP = path.resolve(SRC_FOLDER, 'app.jsx');

module.exports = {
	// > JS Input / Output
	entry: SRC_FILE_JS_APP,
	output: {
		path: DIST_FOLDER,
		publicPath: '/dist/',
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
			// > JS / JSX
			{
				test: /\.(js|jsx)?$/,
				loader: 'babel-loader',
				include: [APP_FOLDER],
				exclude: /(node_modules)/,
				options: {
					presets: ['es2015', 'react', 'stage-2'],
				},
			},
			// > CSS / SCSS
			{
				test: /\.(css|scss|sass)?$/,
				use: ExtractTextPlugin.extract({
					fallback: 'style-loader/url!file-loader',
					use: ['css-loader', 'sass-loader'],
					publicPath: DIST_FOLDER_STYLE,
				}),
			},
		], // rules
	}, // module
	devtool: DEBUG ? 'source-map' : '',
	context: __dirname,
	target: 'web',
	plugins:
		DEBUG ?
			[
				// > Configure CSS Bundle file
				new ExtractTextPlugin({
					filename: DIST_FILE_CSS_BUNDLE,
					disable: false,
					allChunks: true,
				}),
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
					cssProcessor: require('cssnano'),
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
		contentBase: APP_FOLDER,
		compress: true,
		inline: true,
		port: 7070,
	},
};
