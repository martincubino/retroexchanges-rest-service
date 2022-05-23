const url = require('url');

export default {
  async loadUser(context) {
    
    const token = context.rootGetters.token;
    const email = context.rootGetters.email;

    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/user/${email}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
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
          error_message = 'Usuario no registrado. Registrese primero para autenticarse en el sistema.';
          break;
        default:
          error_message = 'Fallo al intentar autenticar. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    context.commit('setUser', {
      name: responseData.name,
      surname: responseData.surname,
      email: responseData.email,
      nif: responseData.nif,
      address: responseData.address,
      isAdmin: responseData.isAdmin,
      status: responseData.status,
      password: responseData.password
    });
  },
  async updateUser(context, payload) {
    
    const token = context.rootGetters.token;
    const email = context.rootGetters.email;

    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/user/${email}`, {
      method: 'PUT',
      body: JSON.stringify(payload),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
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
          error_message = 'Usuario no registrado. Registrese primero para autenticarse en el sistema.';
          break;
        default:
          error_message = 'Fallo al intentar autenticar. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    context.commit('setUser', {
      name: responseData.name,
      surname: responseData.surname,
      email: responseData.email,
      nif: responseData.nif,
      address: responseData.address,
      isAdmin: responseData.isAdmin,
      status: responseData.status,
      password: responseData.password
    });
  },
  async getUserLocation(context, payload) {
    
    let u = url.parse(process.env.VUE_APP_API_NOMINATIM_BASE_URL + payload.address + '&format=jsonv2');

    const response = await fetch(u.href, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    })

    const responseData = await response.json();
    
    if (!response.ok) {
      let error_message = 'Fallo al intentar obterner ubicacion. Intentelo de nuevo mas tarde.';
      const error = new Error(error_message);
      throw error;
    }
    if (typeof responseData != "undefined") {
      if (responseData.length > 0) {
        context.commit('setUserLocation', {
          latitude: responseData[0].lat,
          longitude: responseData[0].lon
        });
      }
    }
  },
  async getLocationAddress(context, payload) {
    
    let u = url.parse(process.env.VUE_APP_API_NOMINATIM_REVERSE_BASE_URL + 'lat=' + payload.latitude + '&lon=' + payload.longitude + '&format=json&addressdetails=1&zoom=16');

    const response = await fetch(u.href, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    })

    const responseData = await response.json();

    if (!response.ok) {
      let error_message = 'Fallo al intentar obterner ubicacion. Intentelo de nuevo mas tarde.';
      const error = new Error(error_message);
      throw error;
    }
    if (typeof responseData != "undefined") {
      let nominatim_response = responseData.display_name.split(",");
      context.commit('setLocationAddress', {
        address: nominatim_response[0] + ',' + nominatim_response[3],
      });
    }
  },
  async loadUsers(context) {
    const token = context.rootGetters.token;

    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/users`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
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
          error_message = 'Usuario no registrado. Registrese primero para autenticarse en el sistema.';
          break;
        default:
          error_message = 'Fallo al intentar autenticar. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    const users = [];

    for (const key in responseData) {
      const user = {
        id: key,
        name: responseData[key].name,
        surname: responseData[key].surname,
        email: responseData[key].email,
        nif: responseData[key].nif,
        address: responseData[key].address,
        isAdmin: responseData[key].isAdmin,
        status: responseData[key].status
      };
      users.push(user);
    }
    context.commit('setUsers', users);
    context.commit('setFetchTimestamp');
  }

};