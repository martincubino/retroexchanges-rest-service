export default {
  setRating(state, payload) {
    state.rating = payload;
  },
  setRatings(state, payload) {
    state.ratings = payload;
  },
  setFetchTimestamp(state) {
    state.lastFetch = new Date().getTime();
  }
};