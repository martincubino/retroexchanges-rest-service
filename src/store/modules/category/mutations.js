export default {
  setCategory(state, payload) {
    state.category = payload;
  },
  setCategories(state, payload) {
    state.categories = payload;
  },
  setFetchTimestamp(state) {
    state.lastFetch = new Date().getTime();
  }
};