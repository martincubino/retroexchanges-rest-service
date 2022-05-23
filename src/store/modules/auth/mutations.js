export default {
  setAuth(state, payload) {
    state.token = payload.token;
    state.email = payload.email;
    state.isAdmin = payload.isAdmin;
    state.tokenCreateAt = payload.createAt;
    state.tokenExpirateAt = payload.expirateAt;
    localStorage.setItem('auth', JSON.stringify(state));
  }
};

