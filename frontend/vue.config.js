
// Vue3 관련 설정 파일
const path = require('path')

module.exports = {
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src/'),
        '~': path.join(__dirname, 'src/assets')
      }
    }
  },
  
  devServer: {
    
    proxy: {
      '/blocket': {
        target: 'http://3.34.191.232'
      }
    },
    
    historyApiFallback: true,
    hot: true,
    port: 3000
  },
  
  
}
