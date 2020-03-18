<template>
	<view class="home">
		<view class="home-user" v-if="user">
			<view class="user-avatar">
				<image :src="user.avatar"></image>
			</view>
			<view class="user-name">
				{{user.name}}
			</view>
		</view>
		<view class="home-login" v-else>
			<!-- #ifdef MP-WEIXIN -->
			<button type="primary" @click="auth" open-type="getUserInfo">登录</button>
			<!-- #endif -->
			<!-- #ifdef H5 -->
			未登录
			<!-- #endif -->
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: undefined
			}
		},
		onShow() {
			// #ifdef MP-WEIXIN
			if (uni.getStorageSync('token')) this.login();
			// #endif
			// #ifdef H5
			if (localStorage.getItem('userInfo') != undefined) this.user = JSON.parse(localStorage.getItem('userInfo'));
			// #endif
		},
		methods: {
			// #ifdef MP-WEIXIN
			auth() {
				uni.login({
					provider: 'weixin',
					success: (res) => {
						uni.getUserInfo({
							provider: 'weixin',
							success: (info_res) => {
								this.$api.auth(res.code, info_res.encryptedData, info_res.iv).then(auth_res => {
									if (auth_res.data.code === 2000) {
										this.user = {
											'avatar': auth_res.data.data.avatar,
											'name': auth_res.data.data.name
										}
										uni.setStorageSync('token', auth_res.data.data.token);
									} else {
										uni.showToast({
											title: '登陆失败，系统维护中',
											icon: 'none'
										})
									}
								})
							}
						})
					}
				})
			},
			// #endif
			// #ifdef MP-WEIXIN
			login() {
				this.$api.getInfo().then(res => {
					if (res.data.code === 2000) {
						this.user = {
							'avatar': res.data.data.avatar,
							'name': res.data.data.name
						}
					} else {
						this.user = undefined;
						uni.showToast({
							title: '登陆过期，请重新登录',
							icon: 'none'
						})
					}
				})
			}
			// #endif
		}
	}
</script>

<style>
	.home-user {
		height: 100vh;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.user-avatar image {
		margin: 20rpx;
		width: 200rpx;
		height: 200rpx;
		border-radius: 99rpx;
	}

	.home-login {
		height: 100vh;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
</style>
