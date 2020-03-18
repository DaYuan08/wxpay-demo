import fly from '../utils/http'

export function queryOrder() {
	return fly.get('/order')
}

export function payment(order) {
	return fly.post('/order/pay', order)
}

export function refund(order) {
	return fly.post('/order/refund', order)
}