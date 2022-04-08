import axios from 'axios'
import {ElMessage} from 'element-plus'
import router from '@/router/index'
// import {localGet} from './index'
// import {error} from "@babel/eslint-parser/lib/convert";


// 这边由于后端没有区分测试和正式，姑且都写成一个接口。
axios.defaults.baseURL = 'http://localhost'
// 携带 cookie
// axios.defaults.withCredentials = true
// 请求头，headers 信息
// axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
// 携带用户token
// axios.defaults.headers['token'] = localGet('token') || ''
// 默认 post 请求，使用 application/json 形式
// axios.defaults.headers.post['Content-Type'] = 'application/json'

// 请求拦截器，内部根据返回值，重新组装，统一管理。
axios.interceptors.response.use(res => {
    if (typeof res.data !== 'object') {
        ElMessage.error('服务端异常！')
        return Promise.reject(res)
    }
    if (res.data.code !== 200) {
        if (res.data.message) ElMessage.error(res.data.message)
        // 未登录错误码 419
        if (res.data.code === 419) {
            router.push({path: '/login'})
        }
        return Promise.reject(res.data)
    }
    return Promise.resolve(res.data)
}, error => {
    return Promise.reject(error)
})

export default axios