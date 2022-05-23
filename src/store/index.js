import Vue from 'vue'
import Vuex from 'vuex'

import authModule from './modules/auth/index.js';
import userModule from './modules/user/index.js';
import categoryModule from './modules/category/index.js';
import productModule from './modules/product/index.js';
import favoriteModule from './modules/favorite/index.js';
import buyrequestModule from './modules/buyrequest/index.js';
import ratingModule from './modules/rating/index.js';

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    auth: authModule,
    user: userModule,
    category: categoryModule,
    product: productModule,
    favorite: favoriteModule,
    buyrequest: buyrequestModule,
    rating: ratingModule
  }
})
