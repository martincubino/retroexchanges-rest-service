export default {
  setProduct(state, payload) {
    state.product = payload;
  },
  setProducts(state, payload) {
    state.products = payload;
  },
  setFetchTimestamp(state) {
    state.lastFetch = new Date().getTime();
  }
};