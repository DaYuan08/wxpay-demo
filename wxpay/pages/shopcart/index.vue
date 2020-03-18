<template>
	<view class="home">
		<view class="home-shopcart" v-if="shopcarts.length > 0">
			<view class="shopcart-view">
				<view class="shopcart-book" v-for="shopcart in shopcarts" :key="shopcart.id">
					<view class="book-image">
						<image :src="shopcart.book.cover"></image>
					</view>
					<view class="book-info">
						<view class="book-description">
							<view class="book-title">{{shopcart.book.title}}</view>
							<view class="book-author">{{shopcart.book.author}}</view>
							<view class="book-rating">豆瓣评分 {{toFix(1, shopcart.book.rating)}}</view>
						</view>
						<view class="book-rate">
							<view class="book-price">
								<span>￥{{toFix(2, shopcart.book.price)}}</span>
							</view>
							<view class="book-control">
								<button @click="removeShopCart(shopcart.id)">移除购物车</button>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="shopcart-control">
				<button @click="settlement">结算</button>
			</view>
		</view>
		<view class="home-none" v-else>
			购物车是空的
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				shopcarts: []
			}
		},
		onShow() {
			this.query();
		},
		methods: {
			query() {
				this.$api.queryShopCart().then(res => {
					if (res.data.code === 2000) {
						this.shopcarts = res.data.data;
					} else {
						uni.showToast({
							title: '登陆过期，请重新登录',
							icon: 'none'
						})
					}
				})
			},
			removeShopCart(id) {
				this.$api.deleteShopCart(id).then(res => {
					if (res.data.code === 2000) {
						this.query();
					} else {
						uni.showToast({
							title: '操作失败，系统维护中...',
							icon: 'none'
						})
					}
				})
			},
			settlement() {
				let shopCartIds = this.shopcarts.map((shopCart) => {
					return shopCart.id
				});
				let prices = 0.00;
				this.shopcarts.forEach(shopCart => prices += shopCart.book.price);
				this.$api.settlementShopCart(shopCartIds, prices).then(res => {
					if (res.data.code === 5003) {
						uni.showToast({
							title: '登陆过期，请重新登录',
							icon: 'none'
						})
					} else if (res.data.code === 5004) {
						uni.showToast({
							title: res.data.desc,
							icon: 'none'
						})
					} else if (res.data.code === 5001) {
						uni.showToast({
							title: '操作失败，系统维护中...',
							icon: 'none'
						})
					} else {
						debugger
						// #ifdef H5
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
			}
		}
	}
</script>

<style>
	.home-shopcart {
		height: 100vh;
	}

	.shopcart-view {
		margin-bottom: 120rpx;
	}

	.shopcart-book {
		border-bottom: 1rpx solid rgb(242, 242, 242);
		display: flex;
		padding: 20rpx;
	}

	.book-image image {
		min-width: 80rpx;
		width: 160rpx;
		height: 246rpx;
	}

	.book-info {
		display: flex;
		flex-direction: column;
		margin-left: 24rpx;
		width: 100%;
	}

	.book-description {
		flex: 1;
	}

	.book-title {
		font-size: 32rpx;
	}

	.book-author {
		margin-top: 8rpx;
		font-size: 24rpx;
		color: rgb(170, 170, 170);
	}

	.book-rating {
		margin-top: 8rpx;
		font-size: 24rpx;
		color: rgb(214, 186, 140);
	}

	.book-rate {
		height: 58rpx;
		margin-top: 10rpx;
		display: flex;
	}

	.book-price {
		flex: 1;
		font-size: 34rpx;
	}

	.book-control button {
		font-size: 24rpx;
		border-radius: 99rpx;
		border: 1rpx solid #b0b0b0;
	}

	.shopcart-control {
		position: fixed;
		/* #ifdef MP-WEIXIN */
		bottom: 0;
		/* #endif */
		/* #ifdef H5 */
		bottom: 160rpx;
		/* #endif */
		right: 0;
		left: 0;
	}

	.shopcart-control button {
		background-color: #43B2FC;
		color: #FFFFFF;
	}
</style>
