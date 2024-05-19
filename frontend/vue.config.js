const fs = require('fs');

module.exports = {
  transpileDependencies: ['vue'], // Add other dependencies if needed
  devServer: {
    server: {
      type: 'https',
      options: {
        key: fs.readFileSync('./key.pem'),
        cert: fs.readFileSync('./cert.pem'),
      },
    },
    port: 443,
  },
};
