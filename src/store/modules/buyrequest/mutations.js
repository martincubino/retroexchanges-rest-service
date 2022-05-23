export default {
  setBuyRequest(state, payload) {
    state.buyrequest = payload;
  },
  setBuyRequests(state, payload) {
    state.buyrequests = payload;
  },
  setFetchTimestamp(state) {
    state.lastFetch = new Date().getTime();
  }
};