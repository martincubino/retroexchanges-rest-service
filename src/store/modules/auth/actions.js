
export default {
  async login(context, payload) {
    let login = {
      email: payload.email,
      password: payload.password
    };
    
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/login`, {
      method: 'POST',
      body: JSON.stringify(login),
      headers: {
        'Content-Type': 'application/json',
      }
    }).catch(e=>{
      let error_message = 'Fallo de autenticación. Intentelo de nuevo mas tarde.';
      console.log(e.message);
      const error = new Error(error_message);
      throw error;
    });

    let responseData = await response.json();
    if (!response.ok) {
      let error_message = '';
      switch(response.status){
      case 401:
        error_message = 'Fallo de autenticación. Revise los datos introducidos.';
        break;
      case 404:
        error_message = 'Usuario no registrado. Registrese primero para autenticarse en el sistema.';
      break;
      default:
        error_message = 'Fallo al intentar autenticar. Intentelo de nuevo mas tarde.';
        break;
      }

      const error = new Error(error_message);
      throw error;
    }

    context.commit('setAuth', {
      token: responseData.token,
      email: responseData.email,
      isAdmin: responseData.isAdmin,
      createAt: responseData.createAt,
      expirateAt: responseData.expirateAt
    });
  },

  async signup(context, payload) {
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/register`, {
      method: 'POST',
      body: JSON.stringify(payload),
      headers: {
        'Content-Type': 'application/json',
      }
    });

    const responseData = await response.json();

    if (!response.ok) {
      const error = new Error(responseData.message || 'Failed to authenticate. Check your login data.');
      throw error;
    }
    context.commit('setAuth', {
      token: responseData.token,
      email: responseData.email,
      isAdmin: responseData.isAdmin,
      createAt: responseData.createAt,
      expirateAt: responseData.expirateAt,
    });
  },
  logout(context) {
    context.commit('setAuth', {
      token: null,
      email:  null,
      isAdmin: null,
      createAt:  null,
      expirateAt:  null
    });
  }
};