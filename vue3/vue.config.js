const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        // 项目运行地址和端口
        port: 80,
        // 运行项目自动打开浏览器
        open: false,
        // 跨域代理（后端的端口：8080）
        // public有的资源：不跨域；否则跨域（优先匹配前端资源）
        /*proxy: 'http://localhost:8080',*/
        // 指定接口的请求 跨域
        proxy: {
            // http://ipaddress:80/api/路径 的请求：跨域
            '/api': {
                target: 'http://localhost:8080',
                // 为了将请求到服务器的路径 由‘http://ipaddress:80/api/路径’ 改回‘http://ipaddress:80/路径’
                // pathRewrite: {'^/api': ''},
                ws:true,    // 用于支持websocket
                changeOrigin:true,  // 向后端伪装来源，控制请求头的host头
            }
        }
    }
})
