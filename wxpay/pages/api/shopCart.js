import fly from '../utils/http'

export function queryShopCart() {
	return fly.get('/shopCart')
}

export function deleteShopCart(id) {
	return fly.delete('/shopCart/' + id)
}

export function settlementShopCart(shopCartIds, prices) {
	return fly.post('/order', {shopCartIds, prices})
}