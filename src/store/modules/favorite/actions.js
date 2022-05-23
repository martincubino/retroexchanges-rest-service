//const uri2blob = require('datauritoblob');

export default {
  async loadFavorite(context, payload) {
    const token = context.rootGetters.token;
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/favorite/${payload.productId}`, {
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
          error_message = 'Producto o usuario no econtrado .';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    context.commit('setFavorite', {
      email: responseData.userProduct.email,
      productId: responseData.userProduct.productId,
      user: responseData.user,
      product: responseData.product
    });
  },
  async createFavorite(context, payload) {
    const token = context.rootGetters.token;

    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/favorite`, {
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

    context.commit('setFavorite', {
      email: responseData.userProduct.email,
      productId: responseData.userProduct.productId,
      user: responseData.user,
      product: responseData.product
    });
  },
  async deleteFavorite(context, payload) {
    const token = context.rootGetters.token;

    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/favorite`, {
      method: 'DELETE',
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

    context.commit('setFavorite', responseData.product);
  },

  async loadFavorites(context, payload) {

    const token = context.rootGetters.token;
    let getUrl = `${process.env.VUE_APP_API_REST_BASE_URL}/favorites/${payload.email}`

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
          error_message = 'No se encontraron favoritos.';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    const favorites = [];

    for (const key in responseData) {
      let favorite = responseData[key].product;
      favorite.id = key;
      favorites.push(favorite);
    }
    context.commit('setFavorites', favorites);
    context.commit('setFetchTimestamp');
  }
};