<template>
	<view class="home">
		<view class="home-order" v-if="orders.length > 0">
			<view class="order-view" v-for="order in orders" :key="order.id">
				<view class="order-top">
					<view class="order-number">
						订单号：{{order.id}}
					</view>
					<view class="order-state">
						{{order.state == '0' ?'待支付' : order.state == '1' ?'已支付' : '已退款'}}
					</view>
				</view>
				<view class="order-bottom">
					<view class="order-price">
						总价：￥{{toFix(2, order.prices)}}
					</view>
					<view class="order-control">
						<button v-show="order.state == '0'" type="primary" @click="pay(order.id)">去支付</button>
						<button v-show="order.state == '1'" type="warn" @click="refund(order.id)">退款</button>
					</view>
				</view>
			</view>
		</view>
		<view class="home-none" v-else>
			暂无订单
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orders: [],
			}
		},
		onShow() {
			this.query();
		},
		methods: {
			query() {
				this.$api.queryOrder().then(res => {
					if (res.data.code === 2000) {
						this.orders = res.data.data;
					} else {
						uni.showToast({
							title: '登陆过期，请重新登录',
							icon: 'none'
						})
					}
				})
			},
			pay(id) {
				this.$api.payment(this.orders.filter(order => order.id == id)[0]).then(res => {
					if (res.data.code === 5003) {
						uni.showToast({
							title: '登陆过期，请重新登录',
							icon: 'none'
						})
					} else if (res.data.code === 5001) {
						uni.showToast({
							title: '操作失败，系统维护中...',
							icon: 'none'
						})
					} else {
						// #ifdef H5
						debugger
						WeixinJSBridge.invoke(
							"getBrandWCPayRequest", {
								appId: res.data.data.appId,
								timeStamp: res.data.data.timeStamp,
								nonceStr: res.data.data.nonceStr,
								package: res.data.data.packageValue,
								signType: res.data.data.signType,
								paySign: res.data.data.paySign
							},
							function(res) {
								debugger
								console.info(res);
								if (res.err_msg == "get_brand_wcpay_request:ok") {
									this.query();
									uni.showToast({
										title: '支付成功'
									})
								}
							}
						);
						// #endif
						// #ifdef MP-WEIXIN
						uni.getProvider({
							service: 'payment',
							success: (pro_res) => {
								uni.requestPayment({
									provider: pro_res.provider,
									appId: res.data.data.appId,
									nonceStr: res.data.data.nonceStr,
									timeStamp: res.data.data.timeStamp,
									package: res.data.data.packageValue,
									paySign: res.data.data.paySign,
									signType: res.data.data.signType,
									success: (pay_res) => {
										this.query();
										uni.showToast({
											title: '支付成功'
										})
									}
								})
							}
						})
						// #endif
					}
				})
			},
			refund(id) {
				this.$api.refund(this.orders.filter(order => order.id == id)[0]).then(res => {
					if (res.data.code === 2000) {
						uni.showToast({
							title: '申请成功',
						})
					} else {
						uni.showToast({
							title: '操作失败，系统维护中...',
							icon: 'none'
						})
					}
				})
			}
		}
	}
</script>

<style>
	.home-order {
		height: 100vh;
	}

	.order-view {
		margin: 20rpx;
		border-bottom: 1rpx solid rgb(242, 242, 242);
	}

	.order-top {
		display: flex;
		margin: 20rpx 10rpx;
	}

	.order-number {
		flex: 1;
	}

	.order-state {
		font-size: 24rpx;
	}

	.order-bottom {
		display: flex;
		margin-bottom: 20rpx;
	}

	.order-price {
		flex: 1;
	}

	.order-bottom button {
		font-size: 24rpx;
		color: #FFFFFF;
		border-radius: 99rpx;
	}
</style>
