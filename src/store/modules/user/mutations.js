export default {
  setUser(state, payload) {
    state.user = payload;
  },
  setUsers(state, payload) {
    state.users = payload;
  },
  setUserLocation(state, payload) 
  {
    state.user.latitude = payload.latitude;
    state.user.longitude = payload.longitude;
  },
  setLocationAddress(state, payload) 
  {
    state.user.address = payload.address;
  },
  setFetchTimestamp(state) {
    state.lastFetch = new Date().getTime();
  }
};