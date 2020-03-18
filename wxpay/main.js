import Vue from 'vue'
import App from './App'
import api from './pages/utils/index.js'
import { toFix } from './pages/utils/utils.js';

Vue.config.productionTip = false
Vue.prototype.toFix = toFix
Vue.prototype.$api = api
App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
