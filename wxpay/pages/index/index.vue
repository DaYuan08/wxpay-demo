<template>
	<view class="home">
		<view class="home-books">
			<view class="books-view" v-for="book in books" :key="book.id">
				<view class="book-image">
					<image :src="book.cover"></image>
				</view>
				<view class="book-info">
					<view class="book-description">
						<view class="book-title">{{book.title}}</view>
						<view class="book-author">{{book.author}}</view>
						<view class="book-rating">豆瓣评分 {{toFix(1, book.rating)}}</view>
					</view>
					<view class="book-rate">
						<view class="book-price">
							<span>￥{{toFix(2, book.price)}}</span>
						</view>
						<view class="book-control">
							<button v-if="book.isAdd" type="default" @click="removeShopCart(book.id)">移除购物车</button>
							<button v-else type="primary" @click="addShopCart(book.id)">加入购物车</button>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				books: []
			}
		},
		// #ifdef H5
		created() {
			if (this.$route.query.code) {
				this.authMp(this.$route.query.code);
			}
		},
		// #endif
		onShow() {
			this.query();
		},
		methods: {
			// #ifdef H5
			authMp(code) {
				this.$api.authMp(code).then(res => {
					if (res.data.code === 2000) {
						localStorage.setItem('userInfo', JSON.stringify({
							'avatar': res.data.data.avatar,
							'name': res.data.data.name
						}))
						localStorage.setItem('token', res.data.data.token);
					} else {
						uni.showToast({
							title: '登陆失败，系统维护中',
							icon: 'none'
						})
					}
				})
			},
			// #endif
			query() {
				this.$api.queryBook().then(res => {
					if (res.data.code === 2000) {
						this.books = res.data.data;
					} else if (res.data.code === 5003) {
						uni.showToast({
							title: '登陆过期，请重新登录',
							icon: 'none'
						})
					} else {
						uni.showToast({
							title: '系统维护中...',
							icon: 'none'
						})
					}
				})
			},
			addShopCart(id) {
				this.$api.addShopCart(this.books.filter(book => book.id == id)[0]).then(res => {
					if (res.data.code === 2000) {
						this.query();
					} else if (res.data.code === 5003) {
						uni.showToast({
							title: '登陆过期，请重新登录',
							icon: 'none'
						})
					} else {
						uni.showToast({
							title: '操作失败，系统维护中...',
							icon: 'none'
						})
					}
				})
			},
			removeShopCart(id) {
				this.$api.removeShopCart(id).then(res => {
					if (res.data.code === 2000) {
						this.query();
					} else if (res.data.code === 5003) {
						uni.showToast({
							title: '登陆过期，请重新登录',
							icon: 'none'
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
	.home-books {
		height: 100vh;
	}

	.books-view {
		border-bottom: 1rpx solid rgb(242, 242, 242);
		display: flex;
		padding: 50rpx 30rpx;
	}

	.book-image image {
		min-width: 90rpx;
		width: 180rpx;
		height: 256rpx;
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
		border: 1rpx solid #eaeaea;
		border-radius: 99rpx;
		font-size: 24rpx;
	}
</style>
