export default {
  setFavorite(state, payload) {
    state.favorite = payload;
  },
  setFavorites(state, payload) {
    state.favorites = payload;
  },
  setFetchTimestamp(state) {
    state.lastFetch = new Date().getTime();
  }
};