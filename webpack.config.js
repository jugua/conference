var webpack = require('webpack');
var nodeEnvironment = process.env.NODE_ENV;

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
        new webpack.DefinePlugin({
            'INCLUDE_ALL_MODULES': function includeAllModulesGlobalFn(modulesArray, application) {
                modulesArray.forEach(function executeModuleIncludesFn(moduleFn) {
                    moduleFn(application);
                });
            },
            ENVIRONMENT: JSON.stringify(nodeEnvironment)
        })
    ],
    module: {
        loaders: [
            { test: /\.js$/, exclude: /node_modules/, loader: "babel-loader" },
            {test: /\.html/, exclude: /(node_modules)/, loader: 'html-loader'},
        ],

    }
};