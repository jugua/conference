var path = require("path");


module.exports = {
    entry: "./src/index",
    devtool: "source-map",


    output: {
        publicPath: "dist/public",
        path:"/",
        filename: "bundle.js"
    },
    module: {
        loaders: [
            { test: /\.js$/, exclude: /node_modules/, loader: "babel-loader" },
            {test: /\.html/, exclude: /(node_modules)/, loader: 'html-loader'},
        ],

    }
};