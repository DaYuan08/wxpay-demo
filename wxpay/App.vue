<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
	}
</script>

<style>
	/*每个页面公共css */
	.home {
		margin: 0 auto;
		/* #ifdef H5 */
		height: 120vh;
		/* #endif */
		/* #ifdef MP-WEIXIN */
		height: 100vh;
		/* #endif */
		max-width: 1200rpx;
	}
	
	.home-none {
		margin-top: 60rpx;
		font-size: 36rpx;
		text-align: center;
	}
</style>
