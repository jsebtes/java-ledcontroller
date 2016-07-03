var path = require('path');

//externals: {
//angular: 'angular'
//},

module.exports = {
    entry: './app/bootstrap.js',
    output: {
        path: './dist',
        filename: 'app.bundle.js'
    },
    //devtool: '#cheap-source-map',
    devtool: '#source-map',
    module: {
        loaders: [
            {
                test: /.js?$/,
                loader: 'babel-loader',
                include: path.resolve(__dirname, "app"),
                query: {
                    presets: ['es2015', 'stage-1']
                }
            },
            { test: /\.html$/, loader: "html" }
        ]
    }
};
