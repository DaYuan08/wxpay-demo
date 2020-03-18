var Fly = require("flyio/dist/npm/wx")
var fly = new Fly

// #ifdef MP-WEIXIN
fly.config.baseURL = 'http://127.0.0.1:8080' // http请求地址
// #endif

// #ifdef H5
fly.config.baseURL = '/mp-api' // http请求地址
// #endif

fly.config.timeout = 100000;

// 请求拦截器
fly.interceptors.request.use((request) => {
	// #ifdef MP-WEIXIN
	let token = uni.getStorageSync('token')
	// #endif
	// #ifdef H5
	let token = localStorage.getItem('token')
	// #endif
	if (token) {
		request.headers['token'] = token
	}
	// #ifdef H5
	request.headers['Content-Type'] = 'application/json'
	// #endif
	return request
}, function(error) {
	return Promise.reject(error)
})

// 响应拦截器
fly.interceptors.response.use((res) => {
	return res
}, (error) => {
	return Promise.reject(error)
})

export default fly
