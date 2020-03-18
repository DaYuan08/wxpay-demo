import fly from '../utils/http'

export function getInfo(){
	return fly.get('/user').then(res => res)
}

export function auth(code, encryptedData, iv) {
	return fly.post('/user/auth', {code, encryptedData, iv})
}

export function authMp(code) {
	return fly.post('/user/authMp', code)
}