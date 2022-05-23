import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import * as Vue2Leaflet from 'vue2-leaflet'; // VALID

// Import Bootstrap and BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'leaflet/dist/leaflet.css';

import './assets/reset.css';

import {  BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';


// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

Vue.use(LMap)
Vue.use(LTileLayer)
Vue.use(LMarker)

Vue.config.productionTip = false

require('@/plugins/fontawesome');

new Vue({
  store,
  router,
  Vue2Leaflet,
  render: h => h(App)
}).$mount('#app')
