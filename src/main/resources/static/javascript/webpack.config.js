var path = require('path');

module.exports = {
    entry: './app/bootstrap.js',
    externals: {
        'angular': 'angular',
        "jquery": "jQuery"
    },
    output: {
        path: './dist',
        filename: 'app.bundle.js'
    },
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
