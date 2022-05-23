
export default {
  async loadRequest(context, payload) {
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
  async createBuyRequest(context, payload) {
    
    const token = context.rootGetters.token;
    
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/request`, {
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
  async updateBuyRequest(context, payload) {
    
    const token = context.rootGetters.token;
    
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/request`, {
      method: 'PUT',
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
  async deleteBuyRequest(context, payload) {
    
    const token = context.rootGetters.token;
    
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/request`, {
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

    context.commit('setBuyRequest', responseData);
  },
  async loadBuyRequests(context, payload) {

    const token = context.rootGetters.token;
    let getUrl = `${process.env.VUE_APP_API_REST_BASE_URL}/requests/${payload.type}/${payload.value}`

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

    const buyrequests = [];

    for (const key in responseData) {
      let buyrequest = responseData[key];
      buyrequest.id = key;
      buyrequests.push(buyrequest);
    }
    context.commit('setBuyRequests', buyrequests);
    context.commit('setFetchTimestamp');
  }
};