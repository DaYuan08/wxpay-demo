import * as home from '../api/home'
import * as shopCart from '../api/shopCart'
import * as order from '../api/order'
import * as user from '../api/user'

//api返回结果，正确的状态
export const STATUS_OK = '2000'

const api = {
	STATUS_OK: STATUS_OK,
	...user,
	...home,
	...shopCart,
	...order
}

export default api
