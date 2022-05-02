// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })
module.exports = {
  devServer: {                //记住，别写错了devServer//设置本地默认端口  选填
    port: 80,
    host: 'localhost',
    proxy: {                 //设置代理，必须填
      '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
        target: 'http://localhost:8080',     //代理的目标地址
        changeOrigin: true,              //是否设置同源，输入是的
        ws: true,
        pathRewrite: {                   //路径重写
          '^/api': '' ,                    //选择忽略拦截器里面的内容
        }
      }
    }
  },

  publicPath: './', // 基本路径
  outputDir: 'dist', // 输出文件目录
  filenameHashing: true, // 生成的静态资源在它们的文件名中包含了 hash 以便更好的控制缓存 想要去除hash 值，置为false
  lintOnSave: false, // eslint-loader 是否在保存的时候检查
  productionSourceMap: true, // 生产环境是否生成 sourceMap 文件
}
