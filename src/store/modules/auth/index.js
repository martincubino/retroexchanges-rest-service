import mutations from './mutations.js';
import actions from './actions.js';
import getters from './getters.js'; 

export default {
  state() {
    return {
      token: null,
      email: null,
      isAdmin: null,
      tokenExpirateAt: null,
      tokenCreateAt: null
    };
  },
  mutations,
  actions,
  getters
};