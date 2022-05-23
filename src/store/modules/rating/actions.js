
export default {
  async loadRating(context, payload) {
    const token = context.rootGetters.token;
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/rating/${payload}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).catch(e => {
      let error_message = 'Fallo de autenticación. Intentelo de nuevo mas tarde.';
      const error = new Error(error_message + '.(' + e.message + ')');
      throw error;
    });

    const responseData = await response.json();
    if (!response.ok) {
      let error_message = '';
      switch (response.status) {
        case 401:
          error_message = 'Fallo de autenticación. Revise los datos introducidos.';
          break;
        case 404:
          error_message = 'Reputacion de usuario no econtrado .';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    context.commit('setRating', {
      email: responseData.email,
      rating: responseData.rating
    });
  },
  async createRating(context, payload) {
    
    const token = context.rootGetters.token;
    
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/rating`, {
      method: 'POST',
      body: JSON.stringify(payload),
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }).catch(e => {
      let error_message = 'Fallo de autenticación. Intentelo de nuevo mas tarde.';
      const error = new Error(error_message + '.(' + e.message + ')');
      throw error;
    });

    const responseData = await response.json();

    if (!response.ok) {
      let error_message = '';
      switch (response.status) {
        case 401:
          error_message = 'Fallo de autenticación. Revise los datos introducidos.';
          break;
        case 404:
          error_message = 'Producto o usuario no envontrado.';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    context.commit('setBuyRequest', responseData);
  },
 
  async loadRatings(context, payload) {

    const token = context.rootGetters.token;
    let getUrl = `${process.env.VUE_APP_API_REST_BASE_URL}/requests/${payload.email}`

    const response = await fetch(getUrl, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })

    const responseData = await response.json();

    if (!response.ok) {
      let error_message = '';
      switch (response.status) {
        case 401:
          error_message = 'Fallo de autenticación. Revise los datos introducidos.';
          break;
        case 404:
          error_message = 'No se encontraron valoraciones.';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    const ratings = [];

    for (const key in responseData) {
      let rating = responseData[key];
      rating.id = key;
      ratings.push(rating);
    }
    context.commit('setRatings', ratings);
    context.commit('setFetchTimestamp');
  }
};