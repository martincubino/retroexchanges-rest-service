export default {
  async loadCategory(context, payload) {

    const categoryId = payload;

    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/category/${categoryId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }

    }).catch(e => {
      let error_message = 'Fallo de autenticación. Intentelo de nuevo mas tarde.';
      console.log(e.message);
      const error = new Error(error_message);
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
          error_message = 'Categoria no encontrada.';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    context.commit('setCategory', {
      categoryId: responseData.categoryId,
      name: responseData.name,
      createAt: responseData.createAt,
      description: responseData.description,
      image: responseData.image,
      updatedAt: responseData.updatedAt,
    });
  },
  async updateCategory(context, payload) {
    const token = context.rootGetters.token;
    const categoryId = payload.categoryId;

    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/category/${categoryId}`, {
      method: 'PUT',
      body: JSON.stringify(payload),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    }).catch(e => {
      let error_message = 'Fallo de autenticación. Intentelo de nuevo mas tarde.';
      console.log(e.message);
      const error = new Error(error_message);
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
          error_message = 'Categoría no encontrada. Registrese primero para autenticarse en el sistema.';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    context.commit('setCategory', {
      categoryId: responseData.categoryId,
      name: responseData.name,
      description: responseData.description,
      createAt: responseData.createAt,
      image: responseData.image,
      updatedAt: responseData.updatedAt,
    });
  },
  async createCategory(context, payload) {
    const token = context.rootGetters.token;
    
    let uri = process.env.VUE_APP_API_REST_BASE_URL+"/category";
    
    const response = await fetch(uri, {
      method: 'POST',
      body: JSON.stringify(payload),
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
        
      })
    }).catch(e => {
      let error_message = 'Fallo de autenticación. Intentelo de nuevo mas tarde.';
      console.log(e.message);
      const error = new Error(error_message);
      throw error;
    });
    const responseData = await response.json();

    if (!response.ok) {
      let error_message = '';
      switch (response.status) {
        case 401:
          error_message = 'Fallo de autenticación. Revise los datos introducidos.';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    context.commit('setCategory', {
      categoryId: responseData.categoryId,
      name: responseData.name,
      description: responseData.description,
      createAt: responseData.createAt,
      image: responseData.image,
      updatedAt: responseData.updatedAt,
    });
  },
  async loadCategories(context,payload) {

    console.log(payload);
    
    const response = await fetch(`${process.env.VUE_APP_API_REST_BASE_URL}/categories`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
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
          error_message = 'No se encontraron categorias. ';
          break;
        default:
          error_message = 'Fallo al intentar obtener los datos. Intentelo de nuevo mas tarde.';
          break;
      }
      const error = new Error(error_message);
      throw error;
    }

    const categories = [];

    for (const key in responseData) {
      const category = {
        id: key,
        categoryId: responseData[key].categoryId,
        name: responseData[key].name,
        description: responseData[key].description,
        createAt: responseData[key].createAt,
        image: responseData[key].image,
        updatedAt: responseData[key].updatedAt,
      };
      categories.push(category);
    }
    context.commit('setCategories', categories);
    context.commit('setFetchTimestamp');
  }
};