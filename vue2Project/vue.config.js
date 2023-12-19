const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    devServer: {
        port: 8081
    },
    // devServer: {
    //     proxy: {
    //         '/api': {
    //             // 此处的写法，目的是为了 将 /api 替换成 目标地址target
    //             // target: 'https://api.live.bilibili.com/xlive/web-interface/v1/second/getList?platform=web&parent_area_id=9&area_id=0&sort_type=sort_type_291&page=1/',
    //             target:'https://www.baidu.com/',
    //             // 允许跨域
    //             changeOrigin: true,
    //             ws: true,
    //             pathRewrite: {
    //                 '^/api': ''
    //             }
    //         }
    //     }
    // },
    transpileDependencies: true
})
