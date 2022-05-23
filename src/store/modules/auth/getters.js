export default {
    email(state) {
        try {
            state.email = JSON.parse(localStorage.getItem('auth')).email;
        } catch (e) {
            console.log(e.message);
        }

        return state.email;
    },
    isAdmin(state) {
        try {
            state.isAdmin = JSON.parse(localStorage.getItem('auth')).isAdmin;

        } catch (e) {
            console.log(e.message)
        }

        return state.isAdmin;
    },
    token(state) {
        try {
            state.token = JSON.parse(localStorage.getItem('auth')).token;
        } catch (e) {
            console.log(e.message);
        }
        return state.token;
    },
    isAuthenticated(state) {
        try {
            state.token = JSON.parse(localStorage.getItem('auth')).token;
        } catch (e) {
            console.log(e.message);
        }
        return !!state.token;
    },
    tokenExpirateAt(state) {
        try {
            state.tokenExpirateAt = JSON.parse(localStorage.getItem('auth')).tokenExpirateAt;
        } catch (e) {
            console.log(e.message);
        }
        return state.tokenExpirateAt;
    },
    tokenCreateAt(state) {
        try {
            state.tokenCreateAt = JSON.parse(localStorage.getItem('auth')).tokenCreateAt;
        } catch (e) {
            console.log(e.message);
        }
        return state.tokenCreateAt;
    }
};