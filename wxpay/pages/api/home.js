import fly from '../utils/http'

export function queryBook() {
	return fly.get('/book')
}

export function addShopCart(data) {
	return fly.post('/shopCart', data)
}

export function removeShopCart(id) {
	return fly.delete('/shopCart/book/' + id)
}